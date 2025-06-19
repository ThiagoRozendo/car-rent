package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioCarro;
import br.ufrpe.negocio.beans.Carro;

public class ControladorCarros {

    private RepositorioCarro repositorioCarro;

    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status){
        repositorioCarro.cadastrar(marca, modelo, anoFabricacao, placa, categoria, status);
    }

    public void editar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status){
        repositorioCarro.editar(marca, modelo, anoFabricacao, placa, categoria, status);
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
