package br.ufrpe.teste;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Fachada fachada = Fachada.getInstance();

        System.out.println("\nfuncionarios");
        try {
            fachada.cadastrarAdministrador("Admin", "admin@admin.com", "senha", 5000.0);
            System.out.println("Administrador cadastrado");
            fachada.cadastrarAtendente("Joana", "joana@gmail.com", "senha", 20000.0, 0.05);
            System.out.println("Atendente cadastrado");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            Funcionario f = fachada.fazerLogin("admin@admin.com", "senha");
            System.out.println("Login de '" + f.getNome() + "' realizado com sucesso.");
            fachada.fazerLogout();
            System.out.println("Logout realizado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            fachada.fazerLogin("admin@gmail.com", "123");
        } catch (Exception e) {
            System.out.println("Erro esperado de login: " + e.getMessage());
        }


        System.out.println("\nclientes");
        Cliente c1 = new Cliente("Carlos", "11122233344", "rua 1", "81999998888", "carlos@gmail.com", "1234567", null);
        Cliente c2 = new Cliente("Souza", "55566677788", "rua 2", "81988887777", "souza@email.com", "7654321", null);
        try {
            fachada.cadastrarCliente(c1);
            System.out.println("Cliente '" + c1.getNome() + "' cadastrado.");
            fachada.cadastrarCliente(c2);
            System.out.println("Cliente '" + c2.getNome() + "' cadastrado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            fachada.cadastrarCliente(c1);
        } catch (Exception e) {
            System.out.println("Erro esperado de cliente duplicado: " + e.getMessage());
        }


        System.out.println("\ncarros");
        fachada.cadastrarCarro("Fiat", "Mobi", 2023, "PDD1234", Categoria.ECONOMICO, true, 120.0, "Mobi novo", "Cadastro inicial");
        fachada.cadastrarCarro("Jeep", "Compass", 2024, "QWE5678", Categoria.SUV, true, 350.50, "Compass novo", "Cadastro inicial");
        fachada.cadastrarCarro("Hyundai", "HB20", 2022, "ASD9012", Categoria.INTERMEDIARIO, true, 180.0, "HB20 npvo", "Cadastro inicial");
        System.out.println("carros cadastrados.");


        System.out.println("\naluguel");
        try {
            Carro mobi = fachada.buscarCarroPorPlaca("PDD1234");
            fachada.adicionarAoCarrinho(mobi);
            System.out.println("Carro '" + mobi.getModelo() + "' adicionado ao carrinho.");
            System.out.println("status do carro agora é: " + mobi.isStatus());

            Carro[] carrosParaAlugar = fachada.getCarrinho().toArray(new Carro[0]);
            fachada.cadastrarAluguel(LocalDate.now(), LocalDate.now().plusDays(7), carrosParaAlugar, c1.getCpf());
            System.out.println("Aluguel para '" + c1.getNome() + "' cadastrado com sucesso.");
            System.out.println("carrinho foi limpo apos o aluguel: " + fachada.getCarrinho().isEmpty());

            System.out.println("status do carro '" + mobi.getModelo() + "' após aluguel é: " + mobi.isStatus());

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nbloqueio por atraso");
        try {
            Carro compass = fachada.buscarCarroPorPlaca("QWE5678");
            Carro[] carrosAtrasados = { compass };
            fachada.cadastrarAluguel(LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), carrosAtrasados, c2.getCpf());
            System.out.println("aluguel atrasado criado para '" + c2.getNome() + "' para teste.");

            boolean atrasado = fachada.clientePossuiAluguelAtrasado(c2.getCpf());
            System.out.println("verificacao de atraso para '" + c2.getNome() + "': " + atrasado);

            System.out.println("Tentando cadastrar novo aluguel para cliente com pendencia");
            Carro hb20 = fachada.buscarCarroPorPlaca("ASD9012");
            Carro[] novoCarrinho = { hb20 };
            fachada.cadastrarAluguel(LocalDate.now(), LocalDate.now().plusDays(3), novoCarrinho, c2.getCpf());

        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }

        System.out.println("\nfinalizacao de aluguel");
        try {
            System.out.println("Listando todos os alugueis:");
            for(Aluguel a : fachada.listarAlugueis()){
                System.out.println("  ID: " + a.getIdAluguel() + ", Cliente: " + a.getCpfCliente() + ", Ativo: " + a.isAtivo());
            }

            Funcionario admin = fachada.fazerLogin("admin@admin.com", "12345678");
            fachada.finalizarAluguel(1, admin);
            System.out.println("Aluguel id 1 (atrasado) foi finalizado.");

            boolean aindaAtrasado = fachada.clientePossuiAluguelAtrasado(c2.getCpf());
            System.out.println("Verificacao de atraso para '" + c2.getNome() + "' após quitação: " + aindaAtrasado);

            Aluguel aluguelFinalizado = fachada.buscarAluguelPorId(1);
            System.out.println("status do aluguel id 1 agora é ativo: " + aluguelFinalizado.isAtivo());
            Carro compass = fachada.buscarCarroPorPlaca("QWE5678");
            System.out.println("Status do carro Compass após devolucao é disponível: " + compass.isStatus());

        } catch (Exception e){
            System.out.println("Erro inesperado: " + e.getMessage());
        }

    }
}