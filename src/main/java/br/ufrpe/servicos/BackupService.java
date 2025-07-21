package br.ufrpe.servicos;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Atendente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackupService {

    private final Fachada fachada;

    public BackupService(Fachada fachada) {
        this.fachada = fachada;
    }

    public void gerarBackup(File diretorio, String formato) throws IOException {
        if ("CSV".equals(formato)) {
            gerarBackupCSV(diretorio);
        } else if ("JSON".equals(formato)) {
            gerarBackupJSON(diretorio);
        }
    }

    private void gerarBackupCSV(File diretorio) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(diretorio, "clientes_backup.csv")))) {
            writer.println("cpf,nome,endereco,telefone,email,cnh");
            for (Cliente cliente : fachada.listarClientes()) {
                writer.println(String.join(",",
                        "\"" + cliente.getCpf() + "\"",
                        "\"" + cliente.getNome() + "\"",
                        "\"" + cliente.getEndereco() + "\"",
                        "\"" + cliente.getTelefone() + "\"",
                        "\"" + cliente.getEmail() + "\"",
                        "\"" + cliente.getCnh() + "\""
                ));
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(diretorio, "carros_backup.csv")))) {
            writer.println("placa,marca,modelo,ano,categoria,preco_diaria,status");
            for (Carro carro : fachada.listarCarros()) {
                writer.println(String.join(",",
                        "\"" + carro.getPlaca() + "\"",
                        "\"" + carro.getMarca() + "\"",
                        "\"" + carro.getModelo() + "\"",
                        String.valueOf(carro.getAnoFabricacao()),
                        "\"" + carro.getCategoria().name() + "\"",
                        String.valueOf(carro.getPreco()),
                        String.valueOf(carro.isStatus())
                ));
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(diretorio, "alugueis_backup.csv")))) {
            writer.println("id,cpf_cliente,data_inicio,data_fim,placas_carros");
            for (Aluguel aluguel : fachada.listarAlugueis()) {
                List<String> placas = new ArrayList<>();
                if (aluguel.getCarrinho() != null) {
                    for (Carro carro : aluguel.getCarrinho()) {
                        placas.add(carro.getPlaca());
                    }
                }
                writer.println(String.join(",",
                        String.valueOf(aluguel.getIdAluguel()),
                        "\"" + aluguel.getCpfCliente() + "\"",
                        "\"" + aluguel.getDataInicio().toString() + "\"",
                        "\"" + aluguel.getDataFim().toString() + "\"",
                        "\"" + String.join(";", placas) + "\""
                ));
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(diretorio, "funcionarios_backup.csv")))) {
            writer.println("tipo,email,nome,salario_mensal,vendas_brutas,taxa_comissao");
            for (Administrador admin : fachada.listarAdministradores()) {
                writer.println(String.join(",",
                        "\"ADMINISTRADOR\"",
                        "\"" + admin.getEmail() + "\"",
                        "\"" + admin.getNome() + "\"",
                        String.valueOf(admin.getSalarioMensal()),
                        "",
                        ""
                ));
            }
            for (Atendente atendente : fachada.listarAtendentes()) {
                writer.println(String.join(",",
                        "\"ATENDENTE\"",
                        "\"" + atendente.getEmail() + "\"",
                        "\"" + atendente.getNome() + "\"",
                        "",
                        String.valueOf(atendente.getVendasBrutas()),
                        String.valueOf(atendente.getTaxaComissao())
                ));
            }
        }
    }

    private void gerarBackupJSON(File diretorio) throws IOException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        BackupData dadosCompletos = new BackupData();
        dadosCompletos.clientes = Arrays.asList(fachada.listarClientes());
        dadosCompletos.carros = Arrays.asList(fachada.listarCarros());
        dadosCompletos.alugueis = fachada.listarAlugueis();
        dadosCompletos.administradores = fachada.listarAdministradores();
        dadosCompletos.atendentes = fachada.listarAtendentes();

        try (FileWriter writer = new FileWriter(new File(diretorio, "backup_completo.json"))) {
            gson.toJson(dadosCompletos, writer);
        }
    }

    private static class BackupData {
        @Expose List<Cliente> clientes;
        @Expose List<Carro> carros;
        @Expose List<Aluguel> alugueis;
        @Expose List<Administrador> administradores;
        @Expose List<Atendente> atendentes;
    }
}