package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.CarroInvalidoException;
import br.ufrpe.negocio.exceptions.ClienteJaCadastradoException;
import br.ufrpe.negocio.exceptions.CpfNaoEncontradoException;

public class RepositorioCliente implements IRepositorioCliente {

    private Cliente[] clientes;
    private int proximaPosicao;
    private static RepositorioCliente instance;
    private static final int CAPACIDADE_INICIAL = 10;

    private RepositorioCliente() {
        this.clientes = new Cliente[CAPACIDADE_INICIAL];
        this.proximaPosicao = 0;
    }

    public static RepositorioCliente getInstance() {
        if (instance == null) {
            instance = new RepositorioCliente();
        }
        return instance;
    }

    private void verificarCapacidade() {
        if (this.proximaPosicao == this.clientes.length) {
            Cliente[] novoArray = new Cliente[this.clientes.length * 2];
            System.arraycopy(this.clientes, 0, novoArray, 0, this.clientes.length);
            this.clientes = novoArray;
        }
    }

    private boolean existe(String cpf) {
        for (int i = 0; i < this.proximaPosicao; i++) {
            if (this.clientes[i] != null && this.clientes[i].getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void adicionarCliente(Cliente cliente) throws ClienteJaCadastradoException {
        if (cliente == null || cliente.getCpf() == null) {
            return;
        }

        if (this.existe(cliente.getCpf())) {
            throw new ClienteJaCadastradoException("Cliente já cadastrado.");
        }

        this.verificarCapacidade();
        this.clientes[this.proximaPosicao] = cliente;
        this.proximaPosicao++;
    }

    @Override
    public Cliente buscar(String cpf) throws ClienteNaoEncontradoException {
        for (int i = 0; i < this.proximaPosicao; i++) {
            if (this.clientes[i] != null && this.clientes[i].getCpf().equals(cpf)) {
                return this.clientes[i];
            }
        }
        throw new ClienteNaoEncontradoException("Cliente com CPF " + cpf + " não encontrado.");
    }

    @Override
    public void removerPorCpf(String cpf) throws ClienteNaoEncontradoException {
        int indiceRemover = -1;

        for (int i = 0; i < this.proximaPosicao; i++) {
            if (this.clientes[i] != null && this.clientes[i].getCpf().equals(cpf)) {
                indiceRemover = i;
                break;
            }
        }

        if (indiceRemover != -1) {
            for (int i = indiceRemover; i < this.proximaPosicao - 1; i++) {
                this.clientes[i] = this.clientes[i + 1];
            }
            this.proximaPosicao--;
            this.clientes[this.proximaPosicao] = null;
        } else {
            throw new ClienteNaoEncontradoException(cpf);
        }
    }

    @Override
    public Cliente[] listar() {
        Cliente[] clientesAtivos = new Cliente[this.proximaPosicao];
        System.arraycopy(this.clientes, 0, clientesAtivos, 0, this.proximaPosicao);
        return clientesAtivos;
    }
}