package br.ufrpe.negocio.excecoes;

public class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException(String cpf) {
        super("Não foi encontrado um cliente com o CPF: " + cpf);
    }
}