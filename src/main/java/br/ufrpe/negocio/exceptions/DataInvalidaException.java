package br.ufrpe.negocio.exceptions;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String message) {
        super(message);
    }
}
