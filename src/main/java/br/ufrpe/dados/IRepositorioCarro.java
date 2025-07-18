// src/main/java/br/ufrpe/dados/IRepositorioCarro.java
package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;

public interface IRepositorioCarro {
    void cadastrar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco, String descricao);
    void editar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco);
    void excluir(String placa, String descricao);
    Carro buscarCarroPorPlaca(String placa);
    Carro[] listarCarros();
}