package br.ufrpe.negocio.beans;

import java.time.LocalDate;

public class Aluguel {

    private int idAluguel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String placaCarro;
    private String cpfCliente; //deve ter o cpf do cliente
    private Itens itens;


    private boolean ativo;
    private boolean atrasado;

    //ID tem que estar no costrutor também
    //cpf do cliente também
    public Aluguel(int idAluguel, LocalDate dataInicio, LocalDate dataFim, String placaCarro, String cpfCliente) {
        this.idAluguel = idAluguel;
        this.placaCarro = placaCarro;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = true;
        this.atrasado = false;
    }

    public int getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(int idAluguel) {
        this.idAluguel = idAluguel;
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


