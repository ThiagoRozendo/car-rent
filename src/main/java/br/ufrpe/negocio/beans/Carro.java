package br.ufrpe.negocio.beans;

import java.util.List;
import java.util.Locale;

public class Carro {
    private String marca;
    private String modelo;
    private int anoFabricacao;
    private String placa; //PK
    private Categoria categoria; // Corrigido para usar o enum Categoria
    private boolean status;
    private double preco;
    private List<Historico> historicos;
    private String descricao;



    public Carro(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco, String descricao) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.categoria = categoria;
        this.status = status;
        this.preco = preco;
        this.descricao = descricao;
    }


    @Override
    public String toString() {

        return String.format(Locale.forLanguageTag("pt-BR"),
                "  - %s %s (%d) | Placa: %s | Preço: R$ %.2f",
                this.marca, this.modelo, this.anoFabricacao, this.placa, this.preco);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Categoria getCategoria() { // Mantido o método que retorna o enum
        return categoria;
    }

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public void setCategoria(Categoria categoria) { // Corrigido para receber o enum
        this.categoria = categoria;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void adicionarHistorico(Historico historico) {
        this.historicos.add(historico);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}