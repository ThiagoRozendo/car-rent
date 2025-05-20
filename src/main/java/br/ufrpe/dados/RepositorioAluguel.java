package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;

import java.time.LocalDate;

public class RepositorioAluguel implements IRepositorioAluguel {
    private Aluguel[] alugueis;

    private static RepositorioAluguel instance;

    public RepositorioAluguel() {
        alugueis = new Aluguel[10000];
    }

    public static RepositorioAluguel getInstance() {
        if (instance == null){
            instance = new RepositorioAluguel();
        }
        return instance;
    }

    @Override
    public void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){

    }

    @Override
    public void editarAluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){

    }

    @Override
    public void excluirAluguel(int idAluguel){

    }

    @Override
    public void finalizarAluguel(int idAluguel){

    }

    @Override
    public void buscarAluguelPorId(int idAluguel){

    }

    @Override
    public Aluguel[] listarAlugueis(){
        return alugueis;
    }
}
