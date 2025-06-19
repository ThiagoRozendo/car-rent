package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Itens;

public class RepositorioItens implements IRepositorioItens {

    private static final int MAX_ITENS = 100;
    private Itens[] itens;
    private int contador;
    private int nextId = 0;
    private static RepositorioItens instance;

    private RepositorioItens() {
        itens = new Itens[MAX_ITENS];
        contador = 0;
    }

    public static RepositorioItens getInstance() {
        if (instance == null) {
            instance = new RepositorioItens();
        }
        return instance;
    }

    @Override
    public void cadastrar(int quantidadeCarros, double valorParcial) {
        if (contador < MAX_ITENS) {
            Itens novo = new Itens(nextId++, quantidadeCarros, valorParcial);
            itens[contador++] = novo;
        } else {
            throw new IllegalStateException("Limite de itens atingido.");
        }
    }

    @Override
    public void editar(int idItens, int novaQuantidade, double novoValorParcial) {
        Itens item = buscarPorId(idItens);
        item.setQuantidadeCarros(novaQuantidade);
        item.setValorParcial(novoValorParcial);
    }

    @Override
    public void excluir(int idItens) {
        for (int i = 0; i < contador; i++) {
            if (itens[i] != null && itens[i].getIdItens() == idItens) {
                itens[i] = itens[contador - 1];
                itens[contador - 1] = null;
                contador--;
                return;
            }
        }
        throw new IllegalArgumentException("Item não encontrado.");
    }

    @Override
    public Itens buscarPorId(int idItens) {
        for (int i = 0; i < contador; i++) {
            if (itens[i] != null && itens[i].getIdItens() == idItens) {
                return itens[i];
            }
        }
        throw new IllegalArgumentException("Item não encontrado.");
    }

    @Override
    public Itens[] listar() {
        Itens[] lista = new Itens[contador];
        for (int i = 0; i < contador; i++) {
            lista[i] = itens[i];
        }
        return lista;
    }
}


