package br.ufrpe.negocio.beans.Funionarios;

import com.google.gson.annotations.Expose;

public class Administrador extends Funcionario {
    @Expose
    private double salarioMensal;
    public Administrador(String nome, String email, String senha, double salarioMensal) {
        super(nome, email, senha);
        this.setSalarioMensal(salarioMensal);
    }

    public double calcularSalario(){
        return  getSalarioMensal();
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(double salario) {
        this.salarioMensal = salario < 0.0 ? 0.0 : salario;
    }
}
