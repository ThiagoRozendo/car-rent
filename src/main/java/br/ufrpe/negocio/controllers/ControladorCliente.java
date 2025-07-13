package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.IRepositorioCliente;
import br.ufrpe.dados.RepositorioCliente;
import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.exceptions.CarroInvalidoException;
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
            throws CarroInvalidoException.ClienteJaCadastradoException, IllegalArgumentException {

        if (cliente == null) {
            throw new IllegalArgumentException("O objeto cliente não pode ser nulo.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório.");
        }

        this.repositorioCliente.adicionarCliente(cliente);
    }

    public Cliente buscar(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException {
        return this.repositorioCliente.buscar(cpf);
    }

    public void removerCliente(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException {
        this.repositorioCliente.removerPorCpf(cpf);
    }

    public Cliente[] listarClientes() {
        return this.repositorioCliente.listar();
    }
}