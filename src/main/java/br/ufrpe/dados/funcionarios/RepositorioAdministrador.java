package br.ufrpe.dados.funcionarios;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import java.util.ArrayList;

public class RepositorioAdministrador implements IRepositorioAdministrador{

    private ArrayList<Administrador> administradores;
    private static RepositorioAdministrador instance;

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

