package br.ufrpe.negocio.beans;

import java.time.DayOfWeek;
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
    private Carro[] carrinho;
    private double valorParcial;

    public Aluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, Carro[] carrinho, String cpfCliente) {
        this.idAluguel = idAluguel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cpfCliente = cpfCliente;
        this.ativo = true;
        this.atrasado = false;
        if (carrinho.length <= 2 && carrinho.length > 0) {
            this.carrinho = carrinho;
        } else {
            throw new IllegalArgumentException("O carrinho deve conter no máximo 2 carros e pelo menos 1 carro.");
        }

        this.valorParcial = getValorParcial();
    }

    public int getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(int idAluguel) {
        this.idAluguel = idAluguel;
    }

    public Carro[] getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carro[] carrinho) {
        if (carrinho.length == 2) {
            this.carrinho = carrinho;
            this.valorParcial = getValorParcial();
        } else {
            throw new IllegalArgumentException("O carrinho deve conter exatamente 2 carros.");
        }
    }

    public double getValorParcial() {
        double precoPorDia = 0;
        for (Carro carro : carrinho) {
            if (carro != null) {
                precoPorDia += carro.getPreco();
            }
        }

        long diasUsados = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, LocalDate.now());
        if (diasUsados < 1) diasUsados = 1; // mínimo de 1 dia para evitar total 0

        double total = precoPorDia * diasUsados;

        if (LocalDate.now().isAfter(dataFim)) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataFim, LocalDate.now());
            double taxa = 0.03 * diasAtraso;
            total += total * taxa;
        }

        return total;
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
}


