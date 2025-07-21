package br.ufrpe.negocio.beans.Funionarios;

import com.google.gson.annotations.Expose;

public abstract class Funcionario {
    @Expose // expose pro gson ignorar a senha
    private String nome;
    @Expose
    private String email;
    private String senha;

    public Funcionario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public abstract double calcularSalario();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
