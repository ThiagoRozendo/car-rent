package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Historico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioHistorico implements IRepositorioHistorico {

    private List<Historico> historicos;
    private static RepositorioHistorico instance;

    public static RepositorioHistorico getInstance() {
        if (instance == null) {
            instance = new RepositorioHistorico();
        }
        return instance;
    }
    private RepositorioHistorico() {
        historicos = new ArrayList<>();
    }
    @Override
    public void adicionar(Historico historico) {
        this.historicos.add(historico);
    }

    @Override
    public List<Historico> listarTodos() {
        return new ArrayList<>(this.historicos);
    }

    @Override
    public List<Historico> buscarPorCpf(String cpf) {
        return historicos.stream()
                .filter(historico -> historico.getCliente() != null && historico.getCliente().getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    @Override
    public List<Historico> buscarPorPlaca(String placa) {
        return historicos.stream()
                .filter(historico -> historico.getCarro() != null && historico.getCarro().getPlaca().equalsIgnoreCase(placa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Historico> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return historicos.stream()
                .filter(historico -> historico.getHorarioInicial().isAfter(inicio) && historico.getHorarioFinal().isBefore(fim))
                .collect(Collectors.toList());
    }
}