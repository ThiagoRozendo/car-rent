package br.ufrpe.negocio.exceptions;

public class CpfNaoEncontradoException extends RuntimeException {
    public CpfNaoEncontradoException(String message) {
        super(message);
    }

    public static class ClienteNaoEncontradoException extends Exception {
        public ClienteNaoEncontradoException(String cpf) {
            super("Não foi encontrado um cliente com o CPF: " + cpf);
        }
    }
}
