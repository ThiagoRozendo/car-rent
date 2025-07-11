package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.exceptions.CarroInvalidoException;
import br.ufrpe.negocio.exceptions.CpfNaoEncontradoException;

public interface IRepositorioCliente {

    void adicionarCliente(Cliente cliente) throws CarroInvalidoException.ClienteJaCadastradoException;

    Cliente buscar(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException; // Renomeado

    void removerPorCpf(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException;

    Cliente[] listar();

}