// src/main/java/br/ufrpe/negocio/controllers/ControladorCarros.java
package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioCarro;
import br.ufrpe.negocio.beans.Carro;

public class ControladorCarros {

    private RepositorioCarro repositorioCarro = RepositorioCarro.getInstance();

    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status, double preco){
        repositorioCarro.cadastrar(marca, modelo, anoFabricacao, placa, categoria, status, preco);
    }

    public void editar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status, double preco){
        repositorioCarro.editar(marca, modelo, anoFabricacao, placa, categoria, status, preco);
    }

    public void excluir(String placa){
        repositorioCarro.excluir(placa);
    }

    public Carro buscarCarroPorPlaca(String placa){
        return repositorioCarro.buscarCarroPorPlaca(placa);
    }

    public Carro[] listarCarros(){
        return repositorioCarro.listarCarros();
    }
}