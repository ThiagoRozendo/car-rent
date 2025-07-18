// src/main/java/br/ufrpe/negocio/controllers/ControladorCarros.java
package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioCarro;
import br.ufrpe.dados.funcionarios.RepositorioAtendente;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;
import br.ufrpe.negocio.beans.RegistroCarros;

import java.util.List;

public class ControladorCarros {

    private static ControladorCarros instance;
    private RepositorioCarro repositorioCarro = RepositorioCarro.getInstance();

    private ControladorCarros() {
        this.repositorioCarro = repositorioCarro.getInstance();
    }

    public static ControladorCarros getInstance() {
        if (instance == null) {
            instance = new ControladorCarros();
        }
        return instance;
    }

    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco, String descricao) {
        repositorioCarro.cadastrar(marca, modelo, anoFabricacao, placa, categoria, status, preco, descricao);
    }

    public void editar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco){
        repositorioCarro.editar(marca, modelo, anoFabricacao, placa, categoria, status, preco);
    }

    public void excluir(String placa, String descricao) {
        repositorioCarro.excluir(placa, descricao);
    }

    public Carro buscarCarroPorPlaca(String placa){
        return repositorioCarro.buscarCarroPorPlaca(placa);
    }

    public Carro[] listarCarros(){
        return repositorioCarro.listarCarros();
    }

    public List<RegistroCarros> listarRegistros() {
        return repositorioCarro.listarRegistros();
    }
}