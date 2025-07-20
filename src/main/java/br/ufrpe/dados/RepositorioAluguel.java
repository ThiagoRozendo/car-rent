package br.ufrpe.dados;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.controllers.ControladorCarros;
import br.ufrpe.negocio.exceptions.AluguelNaoEncontradoException;
import br.ufrpe.negocio.exceptions.CarroInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;

public class RepositorioAluguel implements IRepositorioAluguel {

    private ControladorCarros controladorCarros = ControladorCarros.getInstance();
    private static final int MAX_ALUGUEIS = 100;
    private Aluguel[] alugueis;
    private int contador;
    private static RepositorioAluguel instance;
    private int nextId = 0;

    public RepositorioAluguel() {
        alugueis = new Aluguel[MAX_ALUGUEIS];
        contador = 0;
    }

    public static RepositorioAluguel getInstance() {
        if (instance == null){
            instance = new RepositorioAluguel();
        }
        return instance;
    }

    @Override
    public void cadastrar(LocalDate dataInicio, LocalDate dataFim, Carro[] carrinho, String cpfCliente) {
        if (contador < MAX_ALUGUEIS) {

            for(Carro carro : carrinho) {
                carro.setStatus(false);
            }
            Aluguel novoAluguel = new Aluguel(nextId++, dataInicio, dataFim, carrinho, cpfCliente);
            alugueis[contador++] = novoAluguel;
        }
    }


    @Override
    public void editar(int idAluguel, LocalDate dataInicio, LocalDate dataFim, Carro[] carrinho, String cpfCliente) {
        Aluguel aluguel = buscarPorId(idAluguel);
        if (aluguel == null){
            throw new IllegalArgumentException("Aluguel não encontrado.");
        }
        else {
            aluguel.setDataInicio(dataInicio);
            aluguel.setDataFim(dataFim);
            aluguel.setCarrinho(carrinho);
            aluguel.setCpfCliente(cpfCliente);
        }
    }

    @Override
    public void excluir(int idAluguel){
        for (int i = 0; i < contador; i++) {
            if (alugueis[i].getIdAluguel() == idAluguel) {
                alugueis[i] = alugueis[contador - 1];
                alugueis[contador - 1] = null;
                contador--;
                return;
            }
        }

        throw new IllegalArgumentException("Aluguel não encontrado.");
    }


    @Override
    public Aluguel buscarPorId(int idAluguel) {
        for (int i = 0; i < contador; i++) {
            if (alugueis[i].getIdAluguel() == idAluguel) {
                return alugueis[i];
            }
        }
        throw new IllegalArgumentException("Aluguel não encontrado.");
    }

    @Override
    public java.util.ArrayList<Aluguel> listar(){
        java.util.ArrayList<Aluguel> lista = new java.util.ArrayList<>();
        for (int i = 0; i < contador; i++) {
            lista.add(alugueis[i]);
        }
        return lista;
    }

    @Override
    public void finalizarAluguel(int idAluguel, Object funcionario) {
        if (funcionario instanceof Funcionario) {
            Aluguel aluguel = buscarPorId(idAluguel);
            if (aluguel == null) {
                throw new AluguelNaoEncontradoException("Aluguel não encontrado.");
            }
            aluguel.setAtivo(false);
            Carro carro = controladorCarros.buscarCarroPorPlaca(aluguel.getPlacaCarro());
            if (carro != null) {
                carro.setStatus(true); // Marca o carro como disponível novamente
            }
        } else {
            throw new IllegalArgumentException("Apenas funcionários podem finalizar o aluguel.");
        }
    }
}