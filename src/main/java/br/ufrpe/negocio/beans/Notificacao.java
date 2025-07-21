package br.ufrpe.negocio.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacao {
    private String mensagem;
    private LocalDateTime data;
    private boolean lida;

    public Notificacao(String mensagem) {
        this.mensagem = mensagem;
        this.data = LocalDateTime.now();
        this.lida = false;
    }

    public String getMensagem() { return mensagem; }
    public boolean isLida() { return lida; }
    public void marcarComoLida() { this.lida = true; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s", data.format(formatter), mensagem);
    }
}