package br.ufrpe.negocio.exceptions;

public class FuncionarioNaoExisteException extends RuntimeException {
    public FuncionarioNaoExisteException(String message) {
        super(message);
    }
}
