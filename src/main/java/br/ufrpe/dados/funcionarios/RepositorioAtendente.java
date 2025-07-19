package br.ufrpe.dados.funcionarios;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Atendente;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.controllers.ControladorAdministrador;
import br.ufrpe.negocio.controllers.ControladorCarros;
import br.ufrpe.negocio.exceptions.AtendenteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;

import java.util.ArrayList;

public class RepositorioAtendente implements IRepositorioAtendente {

    private ControladorAdministrador controladorAdministrador = ControladorAdministrador.getInstance();
    private ArrayList<Atendente> atendentes = new ArrayList<>();
    private static RepositorioAtendente instance;

    public static RepositorioAtendente getInstance(){
        if (instance == null){
            instance = new RepositorioAtendente();
        }
        return instance;
    }

    @Override
    public void inserir(String nome, String email, String senha, double vendas, double taxa){
        Atendente atendente = new Atendente(nome, email, senha, vendas, taxa);
        atendentes.add(atendente);
    }

    @Override
    public void alterar(Atendente atendente, String nome, String email, String senha, double vendas, double taxa){
        atendente.setNome(nome);
        atendente.setEmail(email);
        atendente.setSenha(senha);
        atendente.setVendasBrutas(vendas);
        atendente.setTaxaComissao(taxa);
    }

    @Override
    public void remover(Atendente atendente){
            atendentes.remove(atendente);
    }

    @Override
    public Atendente buscarPorEmail(String email) {
        for (Atendente atendente : atendentes) {
            if (atendente.getEmail().equals(email)) {
                return atendente;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Atendente> listar(){
        return atendentes;
    }

}

