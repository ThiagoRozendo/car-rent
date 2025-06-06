package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioAluguel;
import br.ufrpe.negocio.beans.Aluguel;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorAlugueis {


    private RepositorioAluguel repositorioAluguel;

    public ControladorAlugueis(){
        this.repositorioAluguel = RepositorioAluguel.getInstance();
    }

    public void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente) {
    // Regras de negocio a serem atribuidas
        repositorioAluguel.cadastrarAluguel(dataInicio, dataFim, placaCarro, cpfCliente);
    }

    public void editarAluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente) {
    // Regras de negocio a serem atribuidas
        repositorioAluguel.editarAluguel(idAluguel, dataInicio, dataFim, placaCarro, cpfCliente);
    }

    public void excluirAluguel(int idAluguel) {
    // Regras de negocio a serem atribuidas
        repositorioAluguel.excluirAluguel(idAluguel);
    }

    /*/public void finalizarAluguel(int idAluguel) {
    // Regras de negocio a serem atribuidas
        repositorioAluguel.finalizarAluguel(idAluguel);
    }/*/ //metodo sera implementado apos a finalizacao do crud

    public Aluguel buscarAluguelPorId(int idAluguel) {
    // Regras de negocio a serem atribuidas
        return repositorioAluguel.buscarAluguelPorId(idAluguel);
    }

    public ArrayList<Aluguel> listarAlugueis() {
    // Regras de negocio a serem atribuidas
        return repositorioAluguel.listarAlugueis();
    }
}
