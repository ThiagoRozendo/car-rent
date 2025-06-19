package br.ufrpe.negocio.excecoes;

import br.ufrpe.negocio.beans.Cliente;

public class ClienteJaCadastradoException extends Exception {
    public ClienteJaCadastradoException(Cliente cliente) {
        super("O cliente com o CPF " + cliente.getCpf() + " já está cadastrado.");
    }
}
