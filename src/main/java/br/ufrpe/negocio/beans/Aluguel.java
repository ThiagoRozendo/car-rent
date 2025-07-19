package br.ufrpe.negocio.beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Aluguel {

    private int idAluguel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String placaCarro;
    private String cpfCliente;

    private boolean ativo;
    private boolean atrasado;
    private ArrayList<Carro> carrinho;
    private double valorParcial;

    public Aluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, ArrayList<Carro> carrinho, String cpfCliente) {
        this.idAluguel = idAluguel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cpfCliente = cpfCliente;
        this.ativo = true;
        this.atrasado = false;
        this.carrinho = new ArrayList<>();
        this.valorParcial = getValorParcial();
    }

    public int getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(int idAluguel) {
        this.idAluguel = idAluguel;
    }

    public void setCarrinho(ArrayList<Carro> carrinho) {
        this.carrinho = carrinho;
    }

    public double getValorParcial() {
        for(Carro carro : carrinho) {
            valorParcial += carro.getPreco();
        }
        return valorParcial;
    }

    public void setValorParcial(double valorParcial) {
        this.valorParcial = valorParcial;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtrasado() {
        return atrasado;
    }

    public void setAtrasado(boolean atrasado) {
        this.atrasado = atrasado;
    }

    // carrinho
    public void adicionarCarro(Carro carro) {
        if (carro != null && !carrinho.contains(carro)) {
            carrinho.add(carro);
        }
    }

    public void removerCarro(Carro carro) {
        carrinho.remove(carro);
    }

    public ArrayList<Carro> getCarrinho() {
        return carrinho;
    }


}


