package br.ufrpe.dados.funcionarios;

import br.ufrpe.negocio.beans.Funionarios.Administrador;
import java.util.ArrayList;

public interface IRepositorioAdministrador {
    void inserir(String nome, String email, String senha, double salarioMensal);
    void alterar(Administrador administrador, String nome, String email, String senha, double salarioMensal);
    void remover(Administrador administrador);
    Administrador buscarPorEmail(String email);
    ArrayList<Administrador> listar();
}
