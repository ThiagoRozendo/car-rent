package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;

import java.time.LocalDate;

import java.util.ArrayList;

public class RepositorioAluguel implements IRepositorioAluguel {

    private ArrayList<Aluguel>  alugueis;
    private static RepositorioAluguel instance;
    private int nextId = 0;

    public RepositorioAluguel() {
        alugueis = new ArrayList<>();
    }

    public static RepositorioAluguel getInstance() {
        if (instance == null){
            instance = new RepositorioAluguel();
        }
        return instance;
    }

    @Override
    public void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){
        //usar o nextId evita duplicacoes de id, pois quando um aluguel for excluido, o alugueis.sizew() tambem diminui, é mais simples também
        Aluguel novoAluguel = new Aluguel(nextId++, dataInicio, dataFim, placaCarro, cpfCliente);
        this.alugueis.add(novoAluguel);
    }

    @Override
    public void editarAluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente){
        Aluguel aluguel = buscarAluguelPorId(idAluguel);
        if (aluguel != null){
            aluguel.setDataInicio(dataInicio);
            aluguel.setDataFim(dataFim);
            aluguel.setPlacaCarro(placaCarro);
            aluguel.setCpfCliente(cpfCliente);
        }
    }

    @Override
    public boolean excluirAluguel(int idAluguel){ //o retorno foi trocado para boolean para saber se a exclusao foi feita ou nao
        Aluguel aluguel = buscarAluguelPorId(idAluguel);
        if (aluguel != null){
            this.alugueis.remove(aluguel);
            return true;
        }
        return false;
    }

    /*/@Override //metodo sera implementado apos a finalização do crud
    public void finalizarAluguel(int idAluguel){

    }/*/

    @Override
    public Aluguel buscarAluguelPorId(int idAluguel) {
        for (Aluguel aluguel : alugueis){
            if(aluguel.getIdAluguel() == idAluguel){
                return aluguel;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Aluguel> listarAlugueis(){
        return alugueis;
    }
}
