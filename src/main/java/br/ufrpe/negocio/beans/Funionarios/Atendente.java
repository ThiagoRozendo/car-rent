package br.ufrpe.negocio.beans.Funionarios;

import com.google.gson.annotations.Expose;

public class Atendente extends Funcionario {

    @Expose
    private double vendasBrutas;
    @Expose
    private double taxaComissao;

    public Atendente(String nome, String email, String senha, double vendas, double taxa) {
        super(nome, email, senha);
        setVendasBrutas(vendas);
        setTaxaComissao(taxa);
    }

    public double calcularSalario() {
        return getVendasBrutas() * getTaxaComissao();
    }
    public double getVendasBrutas() {
        return vendasBrutas;
    }

    public void setVendasBrutas(double vendasBrutas) {
        this.vendasBrutas = vendasBrutas;
    }

    public double getTaxaComissao() {
        return taxaComissao;
    }

    public void setTaxaComissao(double taxaComissao) {
        this.taxaComissao = taxaComissao;
    }
}
