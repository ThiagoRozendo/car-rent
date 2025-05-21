package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;

import java.time.LocalDate;

public class RepositorioCliente implements IRepositorioCliente{
    private Cliente[] clientes;
    private static RepositorioCliente instance;
    private int proximaPosicao;

    public RepositorioCliente() {
        clientes = new Cliente[10000];
    }

    public static RepositorioCliente getInstance() {
        if (instance == null){
            instance = new RepositorioCliente();
        }
        return instance;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public void adicionarCliente(Cliente cliente) {
        if (cliente != null && proximaPosicao < clientes.length) {
            clientes[proximaPosicao] = cliente;
            proximaPosicao++;
        }
    }

    @Override
    public boolean removerPorCpf(String cpf) {
        for (int i = 0; i < proximaPosicao; i++) {
            Cliente cliente = clientes[i];
            if (cliente != null && cliente.getCpf().equals(cpf)) {
                clientes[i] = clientes[proximaPosicao - 1];
                clientes[proximaPosicao - 1] = null;
                proximaPosicao--;
                return true;
            }
        }
        return false;
    }

}
