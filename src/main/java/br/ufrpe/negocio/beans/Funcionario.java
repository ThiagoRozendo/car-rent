package br.ufrpe.negocio.beans;
import java.time.LocalDate;

public class Funcionario {
    private String nome;
    private Cargo cargo;
    private String login;
    private String senha;

    public Funcionario(String nome, Cargo cargo, String login, String senha) {
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void cadastrarCliente(String nome, String cpf, String endereco, String telefone, String  email, String cnh) {

    }
    public void editarCliente(String nome, String cpf, String endereco, String telefone, String  email, String cnh){

    }

    public Cliente procurarCliente(String cpf){

    }

    public void cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, int idCliente){

    }
    public void finalizarAluguel(String idAluguel){

    }

    public void editarAluguel(LocalDate dataInicio, LocalDate dataFim, String placaCarro, int idCliente){

    }

    public void cadastrarCarro(String marca, String modelo, String placa, int anoFabricacao, int anoModelo, String categoria, int quantidadeDisponivel){

    }

    public void excluirCarro(String placaCarro){

    }


}
