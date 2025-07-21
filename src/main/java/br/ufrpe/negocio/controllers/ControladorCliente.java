package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.IRepositorioCliente;
import br.ufrpe.dados.RepositorioCliente;
import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.ClienteJaCadastradoException;
import br.ufrpe.negocio.exceptions.CpfNaoEncontradoException;

public class ControladorCliente {

    private IRepositorioCliente repositorioCliente;
    private static ControladorCliente instance;

    private ControladorCliente() {
        this.repositorioCliente = RepositorioCliente.getInstance();
    }

    public static ControladorCliente getInstance() {
        if (instance == null) {
            instance = new ControladorCliente();
        }
        return instance;
    }

    public void cadastrarCliente(Cliente cliente)
            throws ClienteJaCadastradoException, IllegalArgumentException, ClienteNaoEncontradoException {

        if (cliente == null) {
            throw new IllegalArgumentException("O objeto cliente não pode ser nulo.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório.");
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new IllegalArgumentException("O email do cliente é obrigatório.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new IllegalArgumentException("O telefone do cliente é obrigatório.");
        }

        if (cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
            throw new IllegalArgumentException("O endereço do cliente é obrigatório.");
        }

        if (cliente.getCnh() == null || cliente.getCnh().isBlank()) {
            throw new IllegalArgumentException("A CNH do cliente é obrigatória.");
        }

        this.repositorioCliente.adicionarCliente(cliente);
    }


    public void editarCliente(Cliente cliente, String nome, String endereco, String telefone, String email, String cnh) {
        if (cliente != null) {
            cliente.setNome(nome);
            cliente.setEndereco(endereco);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setCnh(cnh);
            this.repositorioCliente.alterar(cliente);
        }
    }

    public Cliente buscar(String cpf) throws ClienteNaoEncontradoException {
        return this.repositorioCliente.buscar(cpf);
    }

    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        this.repositorioCliente.removerPorCpf(cpf);
    }

    public Cliente[] listarClientes() {
        return this.repositorioCliente.listar();
    }
}