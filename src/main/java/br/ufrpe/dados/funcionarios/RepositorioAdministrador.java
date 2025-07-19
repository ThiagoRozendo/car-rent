package br.ufrpe.dados.funcionarios;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.controllers.ControladorAdministrador;
import br.ufrpe.negocio.exceptions.AdministradorNaoEncontradoException;
import br.ufrpe.negocio.exceptions.AtendenteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;

import java.util.ArrayList;

public class RepositorioAdministrador implements IRepositorioAdministrador{

    private static RepositorioAdministrador instance;
    private ArrayList<Administrador> administradores;


    private RepositorioAdministrador() {
        this.administradores = new ArrayList<>();
    }

    public static RepositorioAdministrador getInstance() {
        if (instance == null) {
            instance = new RepositorioAdministrador();
        }
        return instance;
    }

    @Override
    public void inserir(String nome, String email, String senha, double salarioMensal) {
        Administrador administrador = new Administrador(nome, email, senha, salarioMensal);
        administradores.add(administrador);
    }

    @Override
    public void alterar(Administrador administrador, String nome, String email, String senha, double salarioMensal) {
        administrador.setNome(nome);
        administrador.setEmail(email);
        administrador.setSenha(senha);
        administrador.setSalarioMensal(salarioMensal);
    }

    @Override
    public void remover(Administrador administrador) {
        administradores.remove(administrador);
    }

    @Override
    public Administrador buscarPorEmail(String email) {
        for (Administrador admin : administradores) {
            if (admin.getEmail().equals(email)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Administrador> listar() {
        return administradores;
    }
}

