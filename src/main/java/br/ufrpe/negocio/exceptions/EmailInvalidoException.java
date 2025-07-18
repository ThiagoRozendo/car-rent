package br.ufrpe.negocio.exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException(String msg) {
        super(msg);
    }
}
