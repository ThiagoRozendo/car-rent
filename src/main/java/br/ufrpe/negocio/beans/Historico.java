package br.ufrpe.negocio.beans;

import java.time.LocalDateTime;

public class Historico {

    private Carro carro;
    private Cliente cliente;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;
    private String descricao;

    public Historico(Carro carro, Cliente cliente, LocalDateTime horarioInicial, LocalDateTime horarioFinal, String descricao) {
        this.carro = carro;
        this.cliente = cliente;
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
        this.descricao = descricao;
    }

    public Carro getCarro() {
        return carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getHorarioInicial() {
        return horarioInicial;
    }
    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }
    public String getDescricao() {
        return descricao;
    }
}
