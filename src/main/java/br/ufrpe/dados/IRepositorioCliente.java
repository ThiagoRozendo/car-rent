package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.excecoes.ClienteJaCadastradoException;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;

public interface IRepositorioCliente {

    void adicionarCliente(Cliente cliente) throws ClienteJaCadastradoException;

    Cliente buscar(String cpf) throws ClienteNaoEncontradoException; // Renomeado

    void removerPorCpf(String cpf) throws ClienteNaoEncontradoException;

    Cliente[] listar();

}