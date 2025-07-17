package br.ufrpe.negocio.exceptions;

public class ClienteJaCadastradoException extends RuntimeException {
  public ClienteJaCadastradoException(String message) {
    super(message);
  }
}
