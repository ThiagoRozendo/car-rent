package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.IRepositorioCarro;
import br.ufrpe.dados.RepositorioCarro;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;

public class ControladorCarros {

    private IRepositorioCarro repositorioCarro;

    public ControladorCarros() {
        this.repositorioCarro = RepositorioCarro.getInstance();
    }

    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status, double preco) {
        try {
            Categoria categoriaEnum = Categoria.valueOf(categoria.toUpperCase());
            repositorioCarro.cadastrar(marca, modelo, anoFabricacao, placa, categoriaEnum, status, preco);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de Cadastro: Categoria '" + categoria + "' é inválida.");
        }
    }

    public void editar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status, double preco) {
        try {
            Categoria categoriaEnum = Categoria.valueOf(categoria.toUpperCase());
            repositorioCarro.editar(marca, modelo, anoFabricacao, placa, categoriaEnum, status, preco);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de Edição: Categoria '" + categoria + "' é inválida.");
        }
    }

    public void excluir(String placa) {
        repositorioCarro.excluir(placa);
    }

    public Carro buscarCarroPorPlaca(String placa) {
        return repositorioCarro.buscarCarroPorPlaca(placa);
    }

    public Carro[] listarCarros() {
        return repositorioCarro.listarCarros();
    }
}