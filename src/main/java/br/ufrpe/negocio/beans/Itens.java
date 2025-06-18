package br.ufrpe.negocio.beans;

public class Itens {
    private int quantidadeCarros;
    private static final int MAX_CARROS = 100;
    private Carro[] carros = new Carro[MAX_CARROS];
    private double valorParcial;

    public Itens(int quantidadeCarrosAlugados, double valorParcial) {
        this.quantidadeCarros = quantidadeCarros;
        this.valorParcial = valorParcial;
    }

    public int getQuantidadeCarros() {
        return quantidadeCarros;
    }

    public void setQuantidadeCarros(int quantidadeCarros) {
        this.quantidadeCarros = quantidadeCarros;
    }

    public Carro[] getCarros() {
        return carros;
    }

    public void adicionarCarro(Carro carro) {
        for (int i = 0; i < MAX_CARROS; i++) {
            if (carros[i] == null) {
                carros[i] = carro;
                quantidadeCarros++;
                valorParcial += carro.getPreco();
                break;
            }
        }
    }

    public double getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(double valorParcial) {
        this.valorParcial = valorParcial;
    }
}