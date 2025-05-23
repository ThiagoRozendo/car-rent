package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Cliente;
import java.util.ArrayList;

public class RepositorioCliente implements IRepositorioCliente {
    private ArrayList<Cliente> clientes;
    private static RepositorioCliente instance;

    public RepositorioCliente() {
        clientes = new ArrayList<>();
    }

    public static RepositorioCliente getInstance() {
        if (instance == null) {
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
        if (cliente != null) {
            clientes.add(cliente);
        }
    }

    @Override
    public boolean removerPorCpf(String cpf) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente != null && cliente.getCpf().equals(cpf)) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }
}
