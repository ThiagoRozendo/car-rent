package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioAluguel;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorAlugueis {

    private final RepositorioAluguel repositorioAluguel;

    public ControladorAlugueis() {
        this.repositorioAluguel = RepositorioAluguel.getInstance();
    }

    public void cadastrar(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException, ItensInvalidosException {
        validarDados(dataInicio, dataFim, placaCarro, cpfCliente);
        repositorioAluguel.cadastrar(dataInicio, dataFim, placaCarro, cpfCliente);
    }

    public void editar(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente)
            throws AluguelNaoEncontradoException, DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException, ItensInvalidosException {
        if (!aluguelExiste(idAluguel)) {
            throw new AluguelNaoEncontradoException("Aluguel com ID " + idAluguel + " não encontrado.");
        }
        validarDados(dataInicio, dataFim, placaCarro, cpfCliente);
        repositorioAluguel.editar(idAluguel, dataInicio, dataFim, placaCarro, cpfCliente);
    }

    public void excluir(int idAluguel) throws AluguelNaoEncontradoException {
        if (!aluguelExiste(idAluguel)) {
            throw new AluguelNaoEncontradoException("Não foi possível excluir: aluguel com ID " + idAluguel + " não existe.");
        }
        repositorioAluguel.excluir(idAluguel);
    }

    public Aluguel buscarPorId(int idAluguel) throws AluguelNaoEncontradoException {
        try {
            return repositorioAluguel.buscarPorId(idAluguel);
        } catch (IllegalArgumentException e) {
            throw new AluguelNaoEncontradoException("Aluguel com ID " + idAluguel + " não foi encontrado.");
        }
    }

    public ArrayList<Aluguel> listar() {
        return repositorioAluguel.listar();
    }

    // validacao de todos os dados
    private void validarDados(LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException, ItensInvalidosException {

        if (dataInicio == null || dataFim == null) {
            throw new DataInvalidaException("Datas de início e fim são obrigatórias.");
        }

        if (!dataFim.isAfter(dataInicio)) {
            throw new DataInvalidaException("A data de fim deve ser posterior à data de início.");
        }

        if (placaCarro == null || placaCarro.trim().isEmpty()) {
            throw new CarroInvalidoException("A placa do carro não pode ser vazia.");
        }

        if (cpfCliente == null || !cpfCliente.matches("\\d{11}")) {
            throw new CpfNaoEncontradoException("CPF inválido. Deve conter exatamente 11 dígitos.");
        }
    }

    private boolean aluguelExiste(int id) {
        try {
            repositorioAluguel.buscarPorId(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
