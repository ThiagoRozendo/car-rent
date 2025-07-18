package br.ufrpe.negocio.beans;

import java.time.LocalDateTime;

public class RegistroCarros {
    private Carro carro;
    private String descricao;
    private LocalDateTime horarioRegistro;
    private TipoOperacao tipo; // novo campo

    public RegistroCarros(Carro carro, String descricao, LocalDateTime horarioRegistro, TipoOperacao tipo) {
        this.carro = carro;
        this.descricao = descricao;
        this.horarioRegistro = horarioRegistro;
        this.tipo = tipo;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getHorarioRegistro() {
        return horarioRegistro;
    }

    public void setHorarioRegistro(LocalDateTime horarioRegistro) {
        this.horarioRegistro = horarioRegistro;
    }

    public TipoOperacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoOperacao tipo) {
        this.tipo = tipo;
    }
}
