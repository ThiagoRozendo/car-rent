package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;

import java.util.ArrayList;

import java.time.LocalDate;

public interface IRepositorioAluguel {

    void cadastrar(LocalDate dataInicio, LocalDate dataFim, ArrayList<Carro> carrinho, String cpfCliente);
    void editar(int idAluguel, LocalDate dataInicio, LocalDate dataFim, ArrayList<Carro> carrinho, String cpfCliente);
    void excluir(int idAluguel);
    void finalizarAluguel(int idAluguel, Object funcionario);
    Aluguel buscarPorId(int idAluguel);
    ArrayList<Aluguel> listar();
}
