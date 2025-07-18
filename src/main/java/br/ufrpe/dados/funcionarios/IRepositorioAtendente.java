package br.ufrpe.dados.funcionarios;

import br.ufrpe.negocio.beans.Funionarios.Atendente;

import java.util.ArrayList;

public interface IRepositorioAtendente {

    public void inserir(String nome, String email, String senha, double vendas, double taxa);
    public void alterar(Atendente atendente, String nome, String email, String senha, double vendas, double taxa);
    public void remover(Atendente atendente);
    public Atendente buscarPorEmail(String email);
    public ArrayList<Atendente> listar();
}
