/*/package br.ufrpe.teste;

import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.controllers.*;
import br.ufrpe.negocio.exceptions.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Teste de Cliente
        ControladorCliente controladorCliente = ControladorCliente.getInstance();
        Cliente cliente = new Cliente("Joao Silva", "12345678901", "Rua A", "000000000", "joao@email.com", "1234567", null);
        try {
            controladorCliente.cadastrarCliente(cliente);
            System.out.println("Cliente cadastrado: " + cliente.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }

        Cliente cliente2 = new Cliente("Joao Silva2", "11111111111", "Rua A", "000000000", "joao@email.com", "1234527", null);
        try {
            controladorCliente.cadastrarCliente(cliente2);
            System.out.println("Cliente cadastrado: " + cliente.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }

        for (Cliente c : controladorCliente.listarClientes()) {
            System.out.println("Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

        ControladorCarros controladorCarros = new ControladorCarros();
        controladorCarros.cadastrar("Fiat", "Uno", 2020, "ABC1234", "Hatch", true, 150.0);
        controladorCarros.cadastrar("VW", "Gol", 2019, "XYZ5678", "Sedan", true, 180.0);
        for (Carro c : controladorCarros.listarCarros()) {
            System.out.println("Placa: " + c.getPlaca() + ", Modelo: " + c.getModelo() + ", Preço: " + c.getPreco());
        };

        Itens itens = new Itens(1, 0, 0.0);
        try {
            Carro carro = controladorCarros.buscarCarroPorPlaca("ABC1234");
            itens.adicionarCarro(carro.getPlaca());
            System.out.println("Itens adicionados: " + itens.getQuantidadeCarros() + ", Valor parcial: " + itens.getValorParcial());
        } catch (Exception e) {
            System.out.println("Erro ao adicionar carro aos itens: " + e.getMessage());
        }

        ControladorAlugueis controladorAlugueis = new ControladorAlugueis();
        try {
            controladorAlugueis.cadastrar(LocalDate.now(), LocalDate.now().plusDays(5), "ABC1234", "12345678901", itens);
            System.out.println("Aluguel cadastrado com sucesso");
        } catch (Exception e) {
            System.out.println("erro ao cadastrar aluguel: " + e.getMessage());
        }

        System.out.println("Lista de alugueis:");
        for (Aluguel a : controladorAlugueis.listar()) {
            System.out.println("ID: " + a.getIdAluguel() + ", Cliente: " + a.getCpfCliente() + ", Placa: " + a.getPlacaCarro());
        }

        try {
            controladorAlugueis.editar(0, LocalDate.now(), LocalDate.now().plusDays(10), "ABC1234", "12345678901", itens);
            System.out.println("Aluguel editado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao editar aluguel: " + e.getMessage());
        }

        try {
            Aluguel aluguel = controladorAlugueis.buscarPorId(0);
            System.out.println("Aluguel encontrado: ID " + aluguel.getIdAluguel());
        } catch (AluguelNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            controladorAlugueis.excluir(0);
            System.out.println("Aluguel excluído com sucesso");
        } catch (AluguelNaoEncontradoException e) {
            System.out.println("Erro ao excluir aluguel: " + e.getMessage());
        }

        try {
            controladorAlugueis.buscarPorId(0);
        } catch (AluguelNaoEncontradoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}/*/