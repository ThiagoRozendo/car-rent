package br.ufrpe.negocio.exceptions;

public class CarroInvalidoException extends RuntimeException {
    public CarroInvalidoException(String message) {
        super(message);
    }
}
