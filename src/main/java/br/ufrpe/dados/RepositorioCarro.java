package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Carro;

import java.util.ArrayList;

public class RepositorioCarro implements IRepositorioCarro{

    private Carro[] carros;
    private static RepositorioCarro instance;
    private static final int TAMANHO_INICIAL = 10;
    private int contadorCarros;

    public RepositorioCarro() {
        this.carros = new Carro[TAMANHO_INICIAL];
        this.contadorCarros = 0;
    }

    public static RepositorioCarro getInstance() {
        if (instance == null) {
            instance = new RepositorioCarro();
        }
        return instance;
    }

    private void redimensionarArray() {
        if (contadorCarros == carros.length) {
            int novoTamanho = carros.length * 2;
            Carro[] novoArray = new Carro[novoTamanho];
            System.arraycopy(carros, 0, novoArray, 0, carros.length);
            this.carros = novoArray;
        }
    }

    private int encontrarPosicaoPorPlaca(String placa) {
        for (int i = 0; i < contadorCarros; i++) {
            if (carros[i] != null && carros[i].getPlaca().equalsIgnoreCase(placa)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status) {
        if (encontrarPosicaoPorPlaca(placa) == -1) {
            redimensionarArray();
            Carro novoCarro = new Carro(marca, modelo, anoFabricacao, placa, categoria, status);
            carros[contadorCarros] = novoCarro;
            contadorCarros++;
        }
    }

    @Override
    public void editar(String marca, String modelo, int anoFabricacao, String placa, String categoria, boolean status) {
        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            Carro carroExistente = carros[index];
            carroExistente.setMarca(marca);
            carroExistente.setModelo(modelo);
            carroExistente.setAnoFabricacao(anoFabricacao);
            carroExistente.setCategoria(categoria);
            carroExistente.setStatus(status);
        }
    }

    @Override
    public void excluir(String placa) {
        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            carros[index] = carros[contadorCarros - 1];
            carros[contadorCarros - 1] = null;
            contadorCarros--;
        }
    }

    @Override
    public Carro buscarCarroPorPlaca(String placa) {
        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            return carros[index];
        }
        throw new IllegalArgumentException("Carro não encontrado");
    }

    @Override
    public Carro[] listarCarros() {
        Carro[] todos = new Carro[contadorCarros];
        System.arraycopy(carros, 0, todos, 0, contadorCarros);
        return todos;
    }
}