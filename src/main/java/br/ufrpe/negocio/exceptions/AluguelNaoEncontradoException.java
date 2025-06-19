package br.ufrpe.negocio.exceptions;

public class AluguelNaoEncontradoException extends RuntimeException {
    public AluguelNaoEncontradoException(String message) {
        super(message);
    }
}
