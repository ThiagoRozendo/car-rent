package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;

import java.time.LocalDate;

public interface IRepositorioCliente {
    Cliente buscarPorCpf(String cpf);
    void adicionarCliente(Cliente cliente);
    boolean removerPorCpf(String cpf);
}
