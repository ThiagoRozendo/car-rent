package br.ufrpe.negocio.beans.Funionarios;

public class Administrador extends Funcionario {
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
