package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;
import java.time.LocalDate;

public class RepositorioAluguel implements IRepositorioAluguel {

    private static final int MAX_ALUGUEIS = 100;
    private Aluguel[] alugueis;
    private int contador;
    private static RepositorioAluguel instance;
    private int nextId = 0;

    public RepositorioAluguel() {
        alugueis = new Aluguel[MAX_ALUGUEIS];
        contador = 0;
    }

    public static RepositorioAluguel getInstance() {
        if (instance == null){
            instance = new RepositorioAluguel();
        }
        return instance;
    }

    @Override
    public void cadastrar(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){
        if (contador < MAX_ALUGUEIS) {
            Aluguel novoAluguel = new Aluguel(nextId++, dataInicio, dataFim, placaCarro, cpfCliente);
            alugueis[contador++] = novoAluguel;
        }
    }

    @Override
    public void editar(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){
        Aluguel aluguel = buscarPorId(idAluguel);
        if (aluguel == null){
            throw new IllegalArgumentException("Aluguel não encontrado.");
        }
        else {
            aluguel.setDataInicio(dataInicio);
            aluguel.setDataFim(dataFim);
            aluguel.setPlacaCarro(placaCarro);
            aluguel.setCpfCliente(cpfCliente);
        }
    }

    @Override
    public void excluir(int idAluguel){
        for (int i = 0; i < contador; i++) {
            if (alugueis[i].getIdAluguel() == idAluguel) {
                alugueis[i] = alugueis[contador - 1];
                alugueis[contador - 1] = null;
                contador--;
                return;
            }
        }

        throw new IllegalArgumentException("Aluguel não encontrado.");
    }


    @Override
    public Aluguel buscarPorId(int idAluguel) {
        for (int i = 0; i < contador; i++) {
            if (alugueis[i].getIdAluguel() == idAluguel) {
                return alugueis[i];
            }
        }
        throw new IllegalArgumentException("Aluguel não encontrado.");
    }

    @Override
    public java.util.ArrayList<Aluguel> listar(){
        java.util.ArrayList<Aluguel> lista = new java.util.ArrayList<>();
        for (int i = 0; i < contador; i++) {
            lista.add(alugueis[i]);
        }
        return lista;
    }
}