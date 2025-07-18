package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Carro;

public interface IRepositorioRegCarros {

    public void inserirCarro(Carro carro, String descricao);
    public void remocaoCarro(Carro carro, String descricao);
}
