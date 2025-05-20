package br.ufrpe.negocio.beans;

import java.time.LocalDate;

public class Aluguel {

    private int idAluguel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String placaCarro;
    private boolean ativo;
    private boolean atrasado;

    public Aluguel(String placaCarro, LocalDate dataInicio, LocalDate dataFim) {
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


