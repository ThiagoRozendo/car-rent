package br.ufrpe.teste;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.exceptions.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Fachada fachada = Fachada.getInstance();

        // Cadastro de cliente
        Cliente cliente = new Cliente("Joao Silva", "12345678901", "Rua A", "000000000", "joao@email.com", "1234567", null);
        try {
            fachada.cadastrarCliente(cliente);
            fachada.cadastrarCliente(cliente);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
        Cliente cliente2 = new Cliente("Maria Oliveira", "11111111111", "Rua B", "999999999", "maria@email.com", "7654321", null);
        try {
            fachada.cadastrarCliente(cliente2);
            System.out.println("Cliente cadastrado: " + cliente2.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }

        // Listar clientes
        for (Cliente c : fachada.listarClientes()) {
            System.out.println("Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

        // Cadastro de carros
        fachada.cadastrarCarro("Fiat", "Uno", 2020, "ABC1234", Categoria.ECONOMICO, true, 150.0, "fiat uno", "Adicao de carro fiat uno");
        fachada.cadastrarCarro("VW", "Gol", 2019, "XYZ5678", Categoria.ECONOMICO, true, 180.0, "gol", "Adicao de carro vw gol");

        // Listar carros
        for (Carro c : fachada.listarCarros()) {
            System.out.println("Placa: " + c.getPlaca() + ", Modelo: " + c.getModelo() + ", Preço: " + c.getPreco());
        }

        // aluguel
        try {
            fachada.cadastrarAluguel(LocalDate.now(), LocalDate.now().plusDays(5), "ABC1234", "12345678901");
            System.out.println("Aluguel cadastrado com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluguel: " + e.getMessage());
        }

        try {
            fachada.cadastrarAluguel(LocalDate.now(), LocalDate.now().plusDays(5), "XYZ5678", "12345678901");
            System.out.println("Aluguel cadastrado com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluguel: " + e.getMessage());
        }

        try {
            fachada.cadastrarAluguel(LocalDate.now(), LocalDate.now().plusDays(5), "ABC1234", "12345678901");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluguel: " + e.getMessage());
        } // erro esperado

        try {
            fachada.finalizarAluguel(0, cliente);
        } catch (Exception e) {
            System.out.println("Erro ao finalizar aluguel: " + e.getMessage());
        } // erro esperado

        Administrador admin = new Administrador("Admin", "jonas@gmail.com", "admin123", 5000.0);

         try {
            fachada.finalizarAluguel(0, admin);
             System.out.println("Aluguel finalizado com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao finalizar aluguel: " + e.getMessage());
        }


        // Listar aluguéis
        System.out.println("Lista de alugueis:");
        for (Aluguel a : fachada.listarAlugueis()) {
            System.out.println("ID: " + a.getIdAluguel() + ", Cliente: " + a.getCpfCliente() + ", Placa: " + a.getPlacaCarro());
        }
//
        try {
            fachada.excluirCarro("ABC1234", "quebrou mano");
        } catch (Exception e) {
            System.out.println("Erro ao excluir carro: " + e.getMessage());
        }
         // Listar registros de carros
        System.out.println("Lista de registros:");
        for (RegistroCarros a : fachada.listarRegistros()) {
            System.out.println("Carro: " + a.getCarro().getMarca() + " " + a.getCarro().getModelo() + ", Descriçao: " + a.getDescricao() + ", Tipo de operaçao: " + a.getTipo() + ", Data e hora: " + a.getHorarioRegistro());
        }

//        // Editar aluguel
//        try {
//            fachada.editarAluguel(0, LocalDate.now(), LocalDate.now().plusDays(10), "ABC1234", "12345678901");
//            System.out.println("Aluguel editado com sucesso!");
//        } catch (Exception e) {
//            System.out.println("Erro ao editar aluguel: " + e.getMessage());
//        }

        // Buscar aluguel por ID
//        try {
//            Aluguel aluguel = fachada.buscarAluguelPorId(0);
//            System.out.println("Aluguel encontrado: ID " + aluguel.getIdAluguel());
//        } catch (AluguelNaoEncontradoException e) {
//            System.out.println("Erro: " + e.getMessage());
//        }
//
//        // Excluir aluguel
//        try {
//            fachada.excluirAluguel(0);
//            System.out.println("Aluguel excluído com sucesso");
//        } catch (AluguelNaoEncontradoException e) {
//            System.out.println("Erro ao excluir aluguel: " + e.getMessage());
//        }

////        // Tentar buscar aluguel excluído
//        try {
//            fachada.buscarAluguelPorId(0);
//        } catch (AluguelNaoEncontradoException e) {
//            System.out.println("Erro esperado: " + e.getMessage());
//        }
    }
}
