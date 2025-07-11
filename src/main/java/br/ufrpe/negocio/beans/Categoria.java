package br.ufrpe.negocio.beans;

public enum Categoria {
    ECONOMICO("Econômico"),
    INTERMEDIARIO("Intermediário"),
    LUXO("Luxo"),
    SUV("SUV");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}