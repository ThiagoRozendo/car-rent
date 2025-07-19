package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Historico;

import java.time.LocalDateTime;
import java.util.List;

public interface IRepositorioHistorico {
    public void adicionar(Historico historico);
    public List<Historico> listarTodos();
    public List<Historico> buscarPorCpf(String cpf);
    public List<Historico> buscarPorPlaca(String placa);
    public List<Historico> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}