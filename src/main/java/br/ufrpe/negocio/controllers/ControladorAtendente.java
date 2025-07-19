package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.funcionarios.RepositorioAtendente;
import br.ufrpe.negocio.beans.Funionarios.Atendente;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.exceptions.*;

import java.util.ArrayList;

public class ControladorAtendente {

    private final RepositorioAtendente repositorioAtendente;
    private static ControladorAtendente instance;

    private ControladorAtendente() {
        this.repositorioAtendente = RepositorioAtendente.getInstance();
    }

    public static ControladorAtendente getInstance() {
        if (instance == null) {
            instance = new ControladorAtendente();
        }
        return instance;
    }

    public void cadastrar(String nome, String email, String senha, double vendasBrutas, double taxaComissao)
            throws DadosInvalidosException, EmailInvalidoException {
        validarDados(nome, email, senha, vendasBrutas, taxaComissao);
        repositorioAtendente.inserir(nome, email, senha, vendasBrutas, taxaComissao);
    }

    public void editar(Atendente atendente, String nome, String emailNovo, String senha, double vendasBrutas, double taxaComissao)
            throws AtendenteNaoEncontradoException, DadosInvalidosException, EmailInvalidoException {
        if (atendente == null) {
            throw new AtendenteNaoEncontradoException("Atendente não encontrado.");
        }
        validarDados(nome, emailNovo, senha, vendasBrutas, taxaComissao);
        repositorioAtendente.alterar(atendente, nome, emailNovo, senha, vendasBrutas, taxaComissao);
    }

    public void excluir(Atendente atendente) throws AtendenteNaoEncontradoException {
        if (atendente == null) {
            throw new AtendenteNaoEncontradoException("Não foi possível excluir: atendente não encontrado.");
        }
        repositorioAtendente.remover(atendente);
    }

    public Atendente buscarPorEmail(String email) throws AtendenteNaoEncontradoException {
        Atendente atendente = repositorioAtendente.buscarPorEmail(email);
        if (atendente == null) {
            throw new AtendenteNaoEncontradoException("Atendente com e-mail " + email + " não foi encontrado.");
        }
        return atendente;
    }

    public ArrayList<Atendente> listar() {
        return repositorioAtendente.listar();
    }

    private void validarDados(String nome, String email, String senha, double vendasBrutas, double taxaComissao)
            throws DadosInvalidosException, EmailInvalidoException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("Nome não pode ser vazio.");
        }

        if (email == null || !email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new EmailInvalidoException("E-mail inválido.");
        }

        if (senha == null || senha.length() < 8) {
            throw new DadosInvalidosException("A senha deve conter pelo menos 8 caracteres.");
        }

        if (vendasBrutas < 0) {
            throw new DadosInvalidosException("Vendas brutas não podem ser negativas.");
        }

        if (taxaComissao < 0 ) {
            throw new DadosInvalidosException("A taxa dde comissão deve ser maior que 0.0.");
        }
    }
}
