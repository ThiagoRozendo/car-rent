package br.ufrpe.negocio;

import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.beans.Funionarios.*;
import br.ufrpe.negocio.controllers.*;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.*;
import br.ufrpe.negocio.beans.Notificacao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fachada {

    private static Fachada instance;
    private final ControladorAdministrador controladorAdministrador;
    private final ControladorAlugueis controladorAlugueis;
    private final ControladorAtendente controladorAtendente;
    private final ControladorCarros controladorCarros;
    private final ControladorCliente controladorCliente;
    private Funcionario usuarioLogado = null;
    private List<Carro> carrinho = new ArrayList<>();
    private final List<Notificacao> notificacoes = new ArrayList<>();
    private final List<String> reservas = new ArrayList<>();




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

    public Funcionario getUsuarioLogado() {
        return usuarioLogado;
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
            throws ClienteJaCadastradoException, IllegalArgumentException, ClienteNaoEncontradoException {
        controladorCliente.cadastrarCliente(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) throws ClienteNaoEncontradoException {
        return controladorCliente.buscar(cpf);
    }

    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        controladorCliente.removerCliente(cpf);
    }

    public void editarCliente(Cliente cliente, String nome, String endereco, String telefone, String email, String cnh) {
        controladorCliente.editarCliente(cliente, nome, endereco, telefone, email, cnh);
    }

    public Cliente[] listarClientes() {
        return controladorCliente.listarClientes();
    }


    public boolean clientePossuiAluguelAtrasado(String cpfCliente) {
        ArrayList<Aluguel> todosAlugueis = this.listarAlugueis();
        LocalDate hoje = LocalDate.now();

        for (Aluguel aluguel : todosAlugueis) {
            if (aluguel.getCpfCliente().equals(cpfCliente) &&
                    aluguel.isAtivo() &&
                    aluguel.getDataFim().isBefore(hoje)) {
                return true;
            }
        }
        return false;
    }

    // Aluguel
    public void cadastrarAluguel(LocalDate inicio, LocalDate fim, Carro[] carrinho, String cpf)
            throws DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        controladorAlugueis.cadastrar(inicio, fim, carrinho, cpf);
        limparCarrinho();
    }

    public void editarAluguel(int id, LocalDate inicio, LocalDate fim, Carro[] carrinho, String cpf)
            throws AluguelNaoEncontradoException, DataInvalidaException, CarroInvalidoException, CpfNaoEncontradoException {
        controladorAlugueis.editar(id, inicio, fim, carrinho, cpf);
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

    public void finalizarAluguel(int idAluguel, Object funcionario) throws AluguelNaoEncontradoException, IllegalArgumentException {
        controladorAlugueis.finalizarAluguel(idAluguel, funcionario);

        // notificaccao
        Aluguel aluguel = this.buscarAluguelPorId(idAluguel);
        if (aluguel != null) {
            for (Carro carro : aluguel.getCarrinho()) {
                List<String> reservasParaEsteCarro = reservas.stream()
                        .filter(r -> r.startsWith(carro.getPlaca() + ";"))
                        .collect(Collectors.toList());

                for (String reserva : reservasParaEsteCarro) {
                    String mensagem = "O carro " + carro.getModelo() + " (" + carro.getPlaca() + ") que você reservou está disponível!";
                    notificacoes.add(new Notificacao(mensagem));
                }
                reservas.removeAll(reservasParaEsteCarro);
            }
        }
    }

    // Carro
    public void cadastrarCarro(String marca, String modelo, int ano, String placa, Categoria categoria, boolean status, double preco, String descricao, String descricaoCadastro) {
        controladorCarros.cadastrar(marca, modelo, ano, placa, categoria, status, preco, descricao, descricaoCadastro);
    }

    public void editarCarro(String marca, String modelo, int ano, String placa, Categoria categoria, boolean status, double preco, String descricao) {
        controladorCarros.editar(marca, modelo, ano, placa, categoria, status, preco, descricao);
    }

    public void excluirCarro(String placa, String descricao) {
        controladorCarros.excluir(placa, descricao);
    }

    public Carro buscarCarroPorPlaca(String placa) {
        return controladorCarros.buscarCarroPorPlaca(placa);
    }

    public Carro[] listarCarros() {
        return controladorCarros.listarCarros();
    }

    public List<RegistroCarros> listarRegistros(){
        return controladorCarros.listarRegistros();
    }

    // Login
    public Funcionario fazerLogin(String email, String senha) throws AdministradorNaoEncontradoException, DadosInvalidosException {
        if(email.equals("admin@admin.com") && senha.equals("12345678")){
            controladorAdministrador.cadastrar("Administrador", "admin@admin.com", "12345678", 3450.00);
            this.usuarioLogado = controladorAdministrador.buscarPorEmail("admin@admin.com");
            return usuarioLogado;
        }

        for (Funcionario administrador : controladorAdministrador.listar()) {
            if (administrador.getEmail().equals(email) && administrador.getSenha().equals(senha)) {
                this.usuarioLogado = administrador;
                return administrador;
            }
        }

        for (Funcionario atendente : controladorAtendente.listar()) {
            if (atendente.getEmail().equals(email) && atendente.getSenha().equals(senha)) {
                this.usuarioLogado = atendente;
                return atendente;
            }
        }
        throw new AdministradorNaoEncontradoException("Login inválido. Verifique seu email e senha.");
    }

    // Logout
    public void fazerLogout() {
        this.usuarioLogado = null;
    }

    public void adicionarAoCarrinho(Carro carro) {
        if (carrinho.size() >= 2) {
            throw new IllegalStateException("Carrinho cheio!");
        }
        carrinho.add(carro);
    }

    public void removerDoCarrinho(Carro carro) {
        if(carrinho.size() == 0){
            throw new IllegalStateException("Carrinho vazio!");
        }
        carrinho.remove(carro);
    }


    public List<Carro> getCarrinho() {
        return carrinho;
    }

    public void limparCarrinho() {
        carrinho.clear();
    }

    public void solicitarReserva(Carro carro, Cliente cliente) {
        if (carro != null && cliente != null) {
            String reserva = carro.getPlaca() + ";" + cliente.getCpf();
            if (!reservas.contains(reserva)) {
                reservas.add(reserva);
            }
        }
    }

    public List<Notificacao> getNotificacoesNaoLidas() {
        List<Notificacao> naoLidas = notificacoes.stream()
                .filter(n -> !n.isLida())
                .collect(Collectors.toList());
        naoLidas.forEach(Notificacao::marcarComoLida);
        return naoLidas;
    }
}
