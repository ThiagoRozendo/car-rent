package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.funcionarios.IRepositorioAdministrador;
import br.ufrpe.dados.funcionarios.RepositorioAdministrador;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.exceptions.EmailInvalidoException;
import br.ufrpe.negocio.exceptions.AdministradorNaoEncontradoException;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;

import java.util.ArrayList;

public class ControladorAdministrador {

    private RepositorioAdministrador repositorioAdministrador;
    private static ControladorAdministrador instance;

    private ControladorAdministrador() {
        this.repositorioAdministrador = RepositorioAdministrador.getInstance();
    }

    public static ControladorAdministrador getInstance() {
        if(instance == null) {
            instance = new ControladorAdministrador();
        }
        return instance;
    }

    public void cadastrar(String nome, String email, String senha, double salarioMensal)
            throws DadosInvalidosException, EmailInvalidoException {
        validarDados(nome, email, senha, salarioMensal);
        repositorioAdministrador.inserir(nome, email, senha, salarioMensal);
    }

    public void editar(Administrador admin, String nome, String emailNovo, String senha, double salarioMensal)
            throws AdministradorNaoEncontradoException, DadosInvalidosException, EmailInvalidoException {
        if (admin == null) {
            throw new AdministradorNaoEncontradoException("Administrador não encontrado.");
        }
        validarDados(nome, emailNovo, senha, salarioMensal);
        repositorioAdministrador.alterar(admin, nome, emailNovo, senha, salarioMensal);
    }

    public void remover(Administrador admin) throws AdministradorNaoEncontradoException {
        if (admin == null) {
            throw new AdministradorNaoEncontradoException("Administrador não encontrado.");
        }
        repositorioAdministrador.remover(admin);
    }

    public Administrador buscarPorEmail(String email) {
        return repositorioAdministrador.buscarPorEmail(email);
    }

    public ArrayList<Administrador> listar() {
        return repositorioAdministrador.listar();
    }

    private void validarDados(String nome, String email, String senha, double salarioMensal)
            throws DadosInvalidosException, EmailInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("Nome não pode ser vazio.");
        }

        if (salarioMensal <= 0) {
            throw new DadosInvalidosException("Salário mensal deve ser maior que zero.");
        }

        if (senha == null || senha.length() < 8) {
            throw new DadosInvalidosException("A senha deve conter pelo menos 8 caracteres.");
        }

        if (email == null || !email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new EmailInvalidoException("E-mail inválido.");
        }
    }
}
