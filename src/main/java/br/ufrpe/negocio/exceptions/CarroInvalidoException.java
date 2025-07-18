package br.ufrpe.negocio.exceptions;

import br.ufrpe.negocio.beans.Cliente;

public class CarroInvalidoException extends RuntimeException {
    public CarroInvalidoException(String message) {
        super(message);
    }

    public static class ClienteJaCadastradoException extends Exception {
        public ClienteJaCadastradoException(Cliente cliente) {
            super("O cliente com o CPF " + cliente.getCpf() + " já está cadastrado.");
        }
    }
}
