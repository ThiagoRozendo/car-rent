package br.ufrpe.GUI.telaClientes;

import br.ufrpe.GUI.telaCadastroCliente.CadastroClienteController;
import br.ufrpe.GUI.telaDetalhesCliente.ControladorDetalhesCliente;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;
import br.ufrpe.negocio.beans.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelaClientesController {

    private final Fachada fachada = Fachada.getInstance();
    private ObservableList<Cliente> listaClientes;

    @FXML
    private
    TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colCpf;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colAlugueis;

    @FXML
    private TextField txtBusca;

    @FXML
    private Button btnAdicionar;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarClientes();

        tabelaClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Cliente rowData = row.getItem();
                    try {
                        abrirDetalhesCliente(rowData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        tabelaClientes.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        try {
                            navegarPara("/TelaHomePage2.fxml", "Car Rent - Home");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void abrirDetalhesCliente(Cliente cliente) throws IOException {
        Stage stage = (Stage) tabelaClientes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDetalhesCliente.fxml"));
        Parent root = loader.load();

        ControladorDetalhesCliente controller = loader.getController();
        controller.setCliente(cliente);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colAlugueis.setCellValueFactory(cellData -> {
            Cliente clienteDaLinha = cellData.getValue();
            List<String> descricoesDeAlugueis = new ArrayList<>();
            ArrayList<Aluguel> todosOsAlugueis = fachada.listarAlugueis();

            for (Aluguel aluguel : todosOsAlugueis) {
                if (aluguel.getCpfCliente() != null && aluguel.getCpfCliente().equals(clienteDaLinha.getCpf())) {
                    descricoesDeAlugueis.add("ID " + aluguel.getIdAluguel());
                }
            }
            return new SimpleStringProperty(String.join(", ", descricoesDeAlugueis));
        });
    }

    private void carregarClientes() {
        this.listaClientes = FXCollections.observableArrayList(Arrays.asList(fachada.listarClientes()));
        FilteredList<Cliente> dadosFiltrados = new FilteredList<>(listaClientes, p -> true);

        txtBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            dadosFiltrados.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String termoBusca = newValue.toLowerCase();
                if (cliente.getNome().toLowerCase().contains(termoBusca)) return true;
                else if (cliente.getCpf().toLowerCase().contains(termoBusca)) return true;
                else if (cliente.getEmail().toLowerCase().contains(termoBusca)) return true;
                return false;
            });
        });

        tabelaClientes.setItems(dadosFiltrados);
    }

    private void adicionarDadosDeTeste() { // metodo que foi usado pra testar
        try {
            if (fachada.listarClientes().length == 0) {
                fachada.cadastrarCliente(new Cliente("Ana Silva", "11111111111", "Rua A, 1", "(81) 91111-1111", "ana@email.com", "11111", null));
                fachada.cadastrarCliente(new Cliente("Bruno Costa", "22222222222", "Rua B, 2", "(81) 92222-2222", "bruno@email.com", "22222", null));
            }
            if (fachada.listarCarros().length == 0) {
                fachada.cadastrarCarro("Fiat", "Uno", 2020, "ABC1111", Categoria.ECONOMICO, true, 150.0, "Uno com escada", "");
                fachada.cadastrarCarro("VW", "Gol", 2022, "DEF2222", Categoria.INTERMEDIARIO, true, 200.0, "Gol bolinha", "");
            }
            if (fachada.listarAlugueis().isEmpty()) {
                Carro[] carrosAluguel1 = { fachada.buscarCarroPorPlaca("ABC1111") };
                fachada.cadastrarAluguel(LocalDate.now().minusDays(5), LocalDate.now().plusDays(10), carrosAluguel1, "11111111111");
                Carro[] carrosAluguel2 = { fachada.buscarCarroPorPlaca("DEF2222") };
                fachada.cadastrarAluguel(LocalDate.now().minusDays(20), LocalDate.now().minusDays(10), carrosAluguel2, "11111111111");
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar dados de teste: " + e.getMessage());
        }
    }

    @FXML
    void adicionarNovoCliente(ActionEvent event) throws IOException {
        Stage stage = (Stage) tabelaClientes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CadastroCliente.fxml"));
        Parent root = loader.load();

        CadastroClienteController controller = loader.getController();
        controller.setTelaAnterior("/TelaClientes.fxml");

        Scene scene = new Scene(root);
        stage.setTitle("Cadastro de Cliente");
        stage.setScene(scene);
        stage.show();
    }

    private void navegarPara(String fxmlPath, String title) throws IOException {
        Stage stage = (Stage) tabelaClientes.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}