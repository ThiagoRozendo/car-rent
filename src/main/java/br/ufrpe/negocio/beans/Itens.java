package br.ufrpe.negocio.beans;

public class Itens {
    private int quantidadeCarros;
    int idItens;
    private static final int MAX_CARROS = 100;
    private Carro[] carros = new Carro[MAX_CARROS];
    private double valorParcial;

    public Itens(int idItens, int quantidadeCarrosAlugados, double valorParcial) {
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
}
