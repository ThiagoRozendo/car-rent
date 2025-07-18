package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.CarroInvalidoException;
import br.ufrpe.negocio.exceptions.ClienteJaCadastradoException;
import br.ufrpe.negocio.exceptions.CpfNaoEncontradoException;

public interface IRepositorioCliente {

    void adicionarCliente(Cliente cliente) throws ClienteJaCadastradoException;

    Cliente buscar(String cpf) throws ClienteNaoEncontradoException; // Renomeado

    void removerPorCpf(String cpf) throws ClienteNaoEncontradoException;

    Cliente[] listar();

}