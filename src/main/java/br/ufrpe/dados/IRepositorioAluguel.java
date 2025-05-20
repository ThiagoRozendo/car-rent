package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;

import java.time.LocalDate;

public interface IRepositorioAluguel {

    void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente);
    void editarAluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente);
    void excluirAluguel(int idAluguel);
    void finalizarAluguel(int idAluguel);
    void buscarAluguelPorId(int idAluguel);
    Aluguel[] listarAlugueis(); //implementacao inicial com array
}
