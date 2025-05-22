package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;
import java.util.ArrayList;

import java.time.LocalDate;

public interface IRepositorioAluguel {

    void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente);
    void editarAluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente);
    boolean excluirAluguel(int idAluguel); //o retorno foi trocado para boolean para saber se a exclusao foi feita ou nao
    //void finalizarAluguel(int idAluguel); //metodo sera implementado apos a finalizacao do crud
    Aluguel buscarAluguelPorId(int idAluguel); //Deve ser do tipo Aluguel
    ArrayList<Aluguel> listarAlugueis(); //implementacao com arraylist
}
