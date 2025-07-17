package br.ufrpe.negocio;

import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.beans.Funionarios.*;
import br.ufrpe.negocio.controllers.*;
import br.ufrpe.negocio.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Fachada {

    private static Fachada instance;
    private final ControladorAdministrador controladorAdministrador;
    private final ControladorAlugueis controladorAlugueis;
    private final ControladorAtendente controladorAtendente;
    private final ControladorCarros controladorCarros;
    private final ControladorCliente controladorCliente;

    private Fachada() {
        this.controladorAdministrador = ControladorAdministrador.getInstance();
        this.controladorAlugueis = ControladorAlugueis.getInstance();
        this.controladorAtendente = ControladorAtendente.getInstance();
        this.controladorCarros = ControladorCarros.getInstance();
        this.controladorCliente = ControladorCliente.getInstance();
    }

    public static Fachada getInstance() {
        if(instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    // Administrador
    public void cadastrarAdministrador(String nome, String email, String senha, double salarioMensal)
            throws DadosInvalidosException, EmailInvalidoException {
        controladorAdministrador.cadastrar(nome, email, senha, salarioMensal);
    }

    public void editarAdministrador(Administrador admin, String nome, String email, String senha, double salarioMensal)
            throws AdministradorNaoEncontradoException, DadosInvalidosException, EmailInvalidoException {
        controladorAdministrador.editar(admin, nome, email, senha, salarioMensal);
    }

    public void removerAdministrador(Administrador admin) throws AdministradorNaoEncontradoException {
        controladorAdministrador.remover(admin);
    }

    public Administrador buscarAdministradorPorEmail(String email) {
        return controladorAdministrador.buscarPorEmail(email);
    }

    public ArrayList<Administrador> listarAdministradores() {
        return controladorAdministrador.listar();
    }

    // Atendente
    public void cadastrarAtendente(String nome, String email, String senha, double vendasBrutas, double taxaComissao)
            throws DadosInvalidosException, EmailInvalidoException {
        controladorAtendente.cadastrar(nome, email, senha, vendasBrutas, taxaComissao);
    }

    public void editarAtendente(Atendente atendente, String nome, String email, String senha, double vendasBrutas, double taxaComissao)
            throws AtendenteNaoEncontradoException, DadosInvalidosException, EmailInvalidoException {
        controladorAtendente.editar(atendente, nome, email, senha, vendasBrutas, taxaComissao);
    }

    public void excluirAtendente(Atendente atendente) throws AtendenteNaoEncontradoException {
        controladorAtendente.excluir(atendente);
    }

    public Atendente buscarAtendentePorEmail(String email) throws AtendenteNaoEncontradoException {
        return controladorAtendente.buscarPorEmail(email);
    }

    public ArrayList<Atendente> listarAtendentes() {
        return controladorAtendente.listar();
    }

    // Cliente
    public void cadastrarCliente(Cliente cliente)
            throws ClienteJaCadastradoException, IllegalArgumentException {
        controladorCliente.cadastrarCliente(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException {
        return controladorCliente.buscar(cpf);
    }

    public void removerCliente(String cpf) throws CpfNaoEncontradoException.ClienteNaoEncontradoException {
        controladorCliente.removerCliente(cpf);
    }

    public Cliente[] listarClientes() {
        return controladorCliente.listarClientes();
    }

    // Aluguel
    public void cadastrarAluguel(LocalDate inicio, LocalDate fim, String placa, String cpf)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        controladorAlugueis.cadastrar(inicio, fim, placa, cpf);
    }

    public void editarAluguel(int id, LocalDate inicio, LocalDate fim, String placa, String cpf)
            throws AluguelNaoEncontradoException, DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        controladorAlugueis.editar(id, inicio, fim, placa, cpf);
    }

    public void excluirAluguel(int id) throws AluguelNaoEncontradoException {
        controladorAlugueis.excluir(id);
    }

    public Aluguel buscarAluguelPorId(int id) throws AluguelNaoEncontradoException {
        return controladorAlugueis.buscarPorId(id);
    }

    public ArrayList<Aluguel> listarAlugueis() {
        return controladorAlugueis.listar();
    }

    // Carro
    public void cadastrarCarro(String marca, String modelo, int ano, String placa, String categoria, boolean status, double preco) {
        controladorCarros.cadastrar(marca, modelo, ano, placa, categoria, status, preco);
    }

    public void editarCarro(String marca, String modelo, int ano, String placa, String categoria, boolean status, double preco) {
        controladorCarros.editar(marca, modelo, ano, placa, categoria, status, preco);
    }

    public void excluirCarro(String placa) {
        controladorCarros.excluir(placa);
    }

    public Carro buscarCarroPorPlaca(String placa) {
        return controladorCarros.buscarCarroPorPlaca(placa);
    }

    public Carro[] listarCarros() {
        return controladorCarros.listarCarros();
    }
}
