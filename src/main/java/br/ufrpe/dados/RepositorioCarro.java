package br.ufrpe.dados;

import br.ufrpe.negocio.beans.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCarro implements IRepositorioCarro {

    private Carro[] carros;
    private static RepositorioCarro instance;
    private static final int TAMANHO_INICIAL = 10;
    private int contadorCarros;
    private final List<RegistroCarros> registros = new ArrayList<>();


    public RepositorioCarro() {
        this.carros = new Carro[TAMANHO_INICIAL];
        this.contadorCarros = 0;
    }

    public static RepositorioCarro getInstance() {
        if (instance == null) {
            instance = new RepositorioCarro();
        }
        return instance;
    }

    private void redimensionarArray() {
        if (contadorCarros == carros.length) {
            int novoTamanho = carros.length * 2;
            Carro[] novoArray = new Carro[novoTamanho];
            System.arraycopy(carros, 0, novoArray, 0, carros.length);
            this.carros = novoArray;
        }
    }

    private int encontrarPosicaoPorPlaca(String placa) {
        for (int i = 0; i < contadorCarros; i++) {
            if (carros[i] != null && carros[i].getPlaca().equalsIgnoreCase(placa)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void cadastrar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco, String descricao) {
        if (encontrarPosicaoPorPlaca(placa) == -1) {
            redimensionarArray();
            Carro novoCarro = new Carro(marca, modelo, anoFabricacao, placa, categoria, status, preco);
            carros[contadorCarros] = novoCarro;
            contadorCarros++;

            registros.add(new RegistroCarros(novoCarro, descricao, LocalDateTime.now(), TipoOperacao.ADICAO));
        }
    }

    @Override
    public void editar(String marca, String modelo, int anoFabricacao, String placa, Categoria categoria, boolean status, double preco) {
        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            Carro carroExistente = carros[index];
            carroExistente.setMarca(marca);
            carroExistente.setModelo(modelo);
            carroExistente.setAnoFabricacao(anoFabricacao);
            carroExistente.setCategoria(categoria);
            carroExistente.setStatus(status);
            carroExistente.setPreco(preco);
        }
    }

    @Override
    public void excluir(String placa, String descricao) {
        Carro carroRemovido = buscarCarroPorPlaca(placa);
        registros.add(new RegistroCarros(carroRemovido, descricao, LocalDateTime.now(), TipoOperacao.REMOCAO));

        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            carros[index] = carros[contadorCarros - 1];
            carros[contadorCarros - 1] = null;
            contadorCarros--;


        }
    }

    @Override
    public Carro buscarCarroPorPlaca(String placa) {
        int index = encontrarPosicaoPorPlaca(placa);
        if (index != -1) {
            return carros[index];
        }
        throw new IllegalArgumentException("Carro não encontrado");
    }

    @Override
    public Carro[] listarCarros() {
        Carro[] todos = new Carro[contadorCarros];
        System.arraycopy(carros, 0, todos, 0, contadorCarros);
        return todos;
    }

    public void adicionarHistorico(String placa, Historico historico) {
        Carro carro = buscarCarroPorPlaca(placa);
        if (carro != null) {
            carro.adicionarHistorico(historico);
        }
        else {
            throw new IllegalArgumentException("Carro com placa " + placa + "não encontrado");
        }
    }

    public List<Historico> listarHistorico(String placa) {
        Carro carro = buscarCarroPorPlaca(placa);
        if (carro != null) {
            return carro.getHistoricos();
        }
        else {
            throw new IllegalArgumentException("Carro com placa" + placa + "não encontrado");
        }
    }

    public List<RegistroCarros> listarRegistros() {
        return new ArrayList<>(registros);
    }

    public List<Carro> filtrarPorStatus(boolean status) {
        List<Carro> carrosFiltrados = new ArrayList<>();
        for (int i = 0; i < contadorCarros; i++) {
            if (carros[i] != null && carros[i].isStatus() == status) {
                carrosFiltrados.add(carros[i]);
            }
        }
        return carrosFiltrados;
    }

    public List<Carro> filtrarPorCategoria(Categoria categoria) {
        List<Carro> carrosFiltrados = new ArrayList<>();
        for (int i = 0; i < contadorCarros; i++) {
            if (carros[i] != null && carros[i].getCategoria().equals(categoria)) {
                carrosFiltrados.add(carros[i]);
            }
        }
        return carrosFiltrados;
    }

    public List<Carro> filtrarPorPlaca(String placa) {
        List<Carro> carrosFiltrados = new ArrayList<>();
        int i = encontrarPosicaoPorPlaca(placa);
        if (i != -1) {
            carrosFiltrados.add(carros[i]);
        }
        return carrosFiltrados;
    }

}