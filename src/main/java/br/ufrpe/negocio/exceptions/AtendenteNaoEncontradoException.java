package br.ufrpe.negocio.exceptions;

public class AtendenteNaoEncontradoException extends RuntimeException {
    public AtendenteNaoEncontradoException(String msg) {
        super(msg);
    }
}
