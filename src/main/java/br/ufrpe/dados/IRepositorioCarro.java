package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Categoria;
import br.ufrpe.negocio.beans.Carro;

public interface IRepositorioCarro {
    void cadastrar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco);
    void editar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco);
    void excluir(String placa);
    Carro buscarCarroPorPlaca(String placa);
    Carro[] listarCarros();
}