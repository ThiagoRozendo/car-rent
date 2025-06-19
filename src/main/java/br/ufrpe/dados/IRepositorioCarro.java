package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Carro;

import java.util.ArrayList;

public interface IRepositorioCarro {
    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status);
    public void editar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status);
    public void excluir(String placa);
    public Carro buscarCarroPorPlaca(String placa);
    public Carro[] listarCarros();
}
