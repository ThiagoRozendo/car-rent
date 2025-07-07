package br.ufrpe.dados;

public interface IRepositorioItens {

    void cadastrar(int quantidadeCarros, double valorParcial);
    void editar(int idItens, int novaQuantidade, double novoValorParcial);
    void excluir(int idItens);
    Itens buscarPorId(int idItens);
    Itens[] listar();
}
