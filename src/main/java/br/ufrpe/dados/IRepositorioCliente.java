package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.excecoes.ClienteJaCadastradoException;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;

public interface IRepositorioCliente {

    void adicionarCliente(Cliente cliente) throws ClienteJaCadastradoException;

    Cliente buscarPorCpf(String cpf) throws ClienteNaoEncontradoException;

    void removerPorCpf(String cpf) throws ClienteNaoEncontradoException;

}