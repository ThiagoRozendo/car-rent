package br.ufrpe.negocio.controllers;

import br.ufrpe.dados.RepositorioAluguel;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorAlugueis {

    private static ControladorAlugueis instance;
    private final RepositorioAluguel repositorioAluguel;

    private ControladorAlugueis() {
        this.repositorioAluguel = RepositorioAluguel.getInstance();
    }

    public static ControladorAlugueis getInstance() {
        if (instance == null) {
            instance = new ControladorAlugueis();
        }
        return instance;
    }

    public void cadastrar(LocalDate dataInicio, LocalDate dataFim, Carro[] carrinho, String cpfCliente)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        validarDados(dataInicio, dataFim, cpfCliente);
        repositorioAluguel.cadastrar(dataInicio, dataFim, carrinho, cpfCliente);
    }

    public void editar(int idAluguel, LocalDate dataInicio, LocalDate dataFim, Carro[] carrinho, String cpfCliente)
            throws AluguelNaoEncontradoException, DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        if (!aluguelExiste(idAluguel)) {
            throw new AluguelNaoEncontradoException("Aluguel com ID " + idAluguel + " não encontrado.");
        }
        validarDados(dataInicio, dataFim, cpfCliente);
        repositorioAluguel.editar(idAluguel, dataInicio, dataFim, carrinho, cpfCliente);
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

    public void finalizarAluguel(int idAluguel, Object funcionario) throws AluguelNaoEncontradoException, IllegalArgumentException {
        if (!aluguelExiste(idAluguel)) {
            throw new AluguelNaoEncontradoException("Aluguel com ID " + idAluguel + " não encontrado.");
        }
        repositorioAluguel.finalizarAluguel(idAluguel, funcionario);
    }

    // validacao de todos os dados
    private void validarDados(LocalDate dataInicio, LocalDate dataFim, String cpfCliente)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {

        if (dataInicio == null || dataFim == null) {
            throw new DataInvalidaException("Datas de início e fim são obrigatórias.");
        }

        if (!dataFim.isAfter(dataInicio)) {
            throw new DataInvalidaException("A data de fim deve ser posterior à data de início.");
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
