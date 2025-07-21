package br.ufrpe.GUI.telaExibirFuncionarios;

import br.ufrpe.GUI.telaCadastro.ControladorCadastro;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Atendente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ExibirFuncionariosController {

    private final Fachada fachada = Fachada.getInstance();

    @FXML
    private TabPane tabPaneFuncionarios;

    @FXML
    private TableView<Administrador> tabelaAdmins;

    @FXML
    private TableView<Atendente> tabelaAtendentes;

    @FXML
    private TableColumn<Administrador, String> colAdminNome;

    @FXML
    private TableColumn<Administrador, String> colAdminEmail;

    @FXML
    private TableColumn<Administrador, Double> colAdminSalario;

    @FXML
    private TableColumn<Atendente, String> colAtendenteNome;

    @FXML
    private TableColumn<Atendente, String> colAtendenteEmail;

    @FXML
    private TableColumn<Atendente, Double> colAtendenteVendas;

    @FXML
    private TableColumn<Atendente, Double> colAtendenteTaxa;

    @FXML
    private TextField txtBusca;

    @FXML
    private Button btnAdicionar;

    @FXML
    public void initialize() {
        configurarTabelas();
        carregarDadosEConfigurarFiltro();
        txtBusca.sceneProperty().addListener((obs, oldScene, newScene) -> {
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

    private void configurarTabelas() {
        colAdminNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAdminEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAdminSalario.setCellValueFactory(new PropertyValueFactory<>("salarioMensal"));
        colAtendenteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAtendenteEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAtendenteVendas.setCellValueFactory(new PropertyValueFactory<>("vendasBrutas"));
        colAtendenteTaxa.setCellValueFactory(new PropertyValueFactory<>("taxaComissao"));
    }

    private void carregarDadosEConfigurarFiltro() {
        ObservableList<Administrador> listaAdmins = FXCollections.observableArrayList(fachada.listarAdministradores());
        FilteredList<Administrador> dadosFiltradosAdmins = new FilteredList<>(listaAdmins, p -> true);
        ObservableList<Atendente> listaAtendentes = FXCollections.observableArrayList(fachada.listarAtendentes());
        FilteredList<Atendente> dadosFiltradosAtendentes = new FilteredList<>(listaAtendentes, p -> true);
        txtBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            String termoBusca = newValue.toLowerCase();
            dadosFiltradosAdmins.setPredicate(admin -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return admin.getNome().toLowerCase().contains(termoBusca) ||
                        admin.getEmail().toLowerCase().contains(termoBusca);
            });
            dadosFiltradosAtendentes.setPredicate(atendente -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return atendente.getNome().toLowerCase().contains(termoBusca) ||
                        atendente.getEmail().toLowerCase().contains(termoBusca);
            });
        });
        tabelaAdmins.setItems(dadosFiltradosAdmins);
        tabelaAtendentes.setItems(dadosFiltradosAtendentes);
    }

    @FXML
    void adicionarNovoFuncionario(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtBusca.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cadastro (1).fxml"));
        Parent root = loader.load();

        ControladorCadastro controller = loader.getController();
        controller.setTelaAnterior("/TelaExibirFuncionarios.fxml");

        Scene scene = new Scene(root);
        stage.setTitle("Adicionar Novo Funcionário");
        stage.setScene(scene);
        stage.show();
    }

    private void navegarPara(String fxmlPath, String title) throws IOException {
        Stage stage = (Stage) txtBusca.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}