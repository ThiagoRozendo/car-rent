package br.ufrpe.negocio.beans;

import br.ufrpe.dados.RepositorioCarro;
import br.ufrpe.negocio.controllers.ControladorCarros;

public class Itens {
    private int quantidadeCarros;
    int idItens;
    private static final int MAX_CARROS = 100;
    private Carro[] carros = new Carro[MAX_CARROS];
        private double valorParcial;

    public Itens(int idItens, int quantidadeCarros, double valorParcial) {
        this.idItens = idItens;
        this.quantidadeCarros = quantidadeCarros;
        this.valorParcial = valorParcial;
    }

    public int getQuantidadeCarros() {
        return quantidadeCarros;
    }

    public void setQuantidadeCarros(int quantidadeCarros) {
        this.quantidadeCarros = quantidadeCarros;
    }

    public int getIdItens() {
        return idItens;
    }

    public void setIdItens(int idItens) {
        this.idItens = idItens;
    }

    public Carro[] getCarros() {
        return carros;
    }

    public void setCarros(Carro[] carros) {
        this.carros = carros;
    }

    public double getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(double valorParcial) {
        this.valorParcial = valorParcial;
    }


    public void adicionarCarro(String placa) {
        ControladorCarros controladorCarros = new ControladorCarros();
        Carro carro = controladorCarros.buscarCarroPorPlaca(placa);
        if (carro == null) {
            throw new IllegalArgumentException("Carro não encontrado.");
        }
        for (int i = 0; i < MAX_CARROS; i++) {
            if (carros[i] == null) {
                carros[i] = carro;
                quantidadeCarros++;
                valorParcial += carro.getPreco();
                break;
            }
        }
    }

    public void removerCarro(String placa) {
        ControladorCarros controladorCarros = new ControladorCarros();
        Carro carro = controladorCarros.buscarCarroPorPlaca(placa);
        if (carro == null) {
            throw new IllegalArgumentException("Carro não encontrado.");
        }
        for (int i = 0; i < MAX_CARROS; i++) {
            if (carros[i] != null && carros[i].getPlaca().equals(placa)) {
                valorParcial -= carros[i].getPreco();
                carros[i] = null;
                quantidadeCarros--;

                for (int j = i; j < MAX_CARROS - 1; j++) {
                    carros[j] = carros[j + 1];
                }
                carros[MAX_CARROS - 1] = null;
                break;
            }
        }
    }
}
