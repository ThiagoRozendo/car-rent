package br.ufrpe.GUI.telaDetalhesCliente;

import br.ufrpe.GUI.telaCadastroCliente.CadastroClienteController;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Carro;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorDetalhesCliente {

    private Fachada fachada = Fachada.getInstance();
    private Cliente clienteSelecionado;
    private ObservableList<Aluguel> alugueisDoCliente = FXCollections.observableArrayList();

    @FXML
    private Label lblNomeCliente;

    @FXML
    private Label lblCpfCliente;

    @FXML
    private ToggleGroup filtroAlugueis;

    @FXML
    private ToggleButton btnFiltroAtivos;

    @FXML
    private ToggleButton btnFiltroFinalizados;

    @FXML
    private ToggleButton btnFiltroTodos;

    @FXML
    private TableView<Aluguel> tabelaAlugueisCliente;

    @FXML
    private TableColumn<Aluguel, Integer> colIdAluguel;

    @FXML
    private TableColumn<Aluguel, LocalDate> colDataInicio;

    @FXML
    private TableColumn<Aluguel, LocalDate> colDataFim;

    @FXML
    private TableColumn<Aluguel, String> colCarros;

    @FXML
    private TableColumn<Aluguel, String> colStatus;

    @FXML
    private TableColumn<Aluguel, Double> colValor;

    @FXML
    private Button btnRemoverCliente;

    @FXML
    private Button btnEditarCliente;

    @FXML
    public void initialize() {
        configurarTabela();
        filtroAlugueis.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> aplicarFiltro());
        tabelaAlugueisCliente.setRowFactory(tv -> {
            TableRow<Aluguel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Aluguel aluguel = row.getItem();
                    abrirOpcoesAluguel(aluguel);
                }
            });
            return row;
        });
    }

    public void setCliente(Cliente cliente) {
        this.clienteSelecionado = cliente;
        lblNomeCliente.setText(cliente.getNome());
        lblCpfCliente.setText("CPF: " + cliente.getCpf());
        carregarAlugueis();
    }

    @FXML
    void removerCliente(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Remover " + clienteSelecionado.getNome() + "?");
        alert.setContentText("O cliente será permanentemente removido do sistema. Deseja continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                fachada.removerCliente(clienteSelecionado.getCpf());
                voltar(event);
            } catch (Exception e) {
                Alert erro = new Alert(Alert.AlertType.ERROR, "Erro ao remover cliente: " + e.getMessage());
                erro.showAndWait();
            }
        }
    }

    @FXML
    void editarCliente(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnEditarCliente.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CadastroCliente.fxml"));
        Parent root = loader.load();

        CadastroClienteController controller = loader.getController();
        controller.carregarDadosCliente(this.clienteSelecionado);
        controller.setTelaAnterior("/TelaClientes.fxml");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void configurarTabela() {
        colIdAluguel.setCellValueFactory(new PropertyValueFactory<>("idAluguel"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorParcial"));

        colValor.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Aluguel aluguel = getTableRow().getItem();
                    Locale brLocale = new Locale("pt", "BR");
                    if (aluguel != null && aluguel.isAtivo() && LocalDate.now().isAfter(aluguel.getDataFim())) {
                        long diasAtraso = ChronoUnit.DAYS.between(aluguel.getDataFim(), LocalDate.now());
                        setText(String.format(brLocale, "R$ %.2f (vencido há %d dias)", item, diasAtraso));
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setText(String.format(brLocale, "R$ %.2f", item));
                        setStyle("-fx-text-fill: black; -fx-font-weight: normal;");
                    }
                }
            }
        });

        colCarros.setCellValueFactory(cellData -> {
            List<String> descricoes = new ArrayList<>();
            for (Carro carro : cellData.getValue().getCarrinho()) {
                descricoes.add(carro.getModelo() + " (" + carro.getPlaca() + ")");
            }
            return new SimpleStringProperty(String.join(", ", descricoes));
        });

        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAtivo() ? "Ativo" : "Finalizado"));
    }

    private void carregarAlugueis() {
        List<Aluguel> todosAlugueis = fachada.listarAlugueis().stream()
                .filter(a -> a.getCpfCliente().equals(clienteSelecionado.getCpf()))
                .collect(Collectors.toList());
        alugueisDoCliente.setAll(todosAlugueis);
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        FilteredList<Aluguel> dadosFiltrados = new FilteredList<>(alugueisDoCliente);
        ToggleButton selected = (ToggleButton) filtroAlugueis.getSelectedToggle();

        if (selected == btnFiltroAtivos) {
            dadosFiltrados.setPredicate(Aluguel::isAtivo);
        } else if (selected == btnFiltroFinalizados) {
            dadosFiltrados.setPredicate(aluguel -> !aluguel.isAtivo());
        } else {
            dadosFiltrados.setPredicate(aluguel -> true);
        }
        tabelaAlugueisCliente.setItems(dadosFiltrados);
    }

    private void abrirOpcoesAluguel(Aluguel aluguel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opções do Aluguel");
        alert.setHeaderText("Aluguel ID: " + aluguel.getIdAluguel());
        alert.setContentText("O que você deseja fazer?");

        ButtonType btnFinalizar = new ButtonType("Finalizar Aluguel");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnFinalizar, btnCancelar);

        if (!aluguel.isAtivo()) {
            alert.getDialogPane().lookupButton(btnFinalizar).setDisable(true);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnFinalizar) {
            finalizarAluguel(aluguel);
        }
    }

    private void finalizarAluguel(Aluguel aluguel) {
        try {
            fachada.finalizarAluguel(aluguel.getIdAluguel(), fachada.getUsuarioLogado());
            carregarAlugueis();
        } catch (Exception e) {
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Não foi possível finalizar o aluguel.");
            erro.setContentText(e.getMessage());
            erro.showAndWait();
        }
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        Stage stage = (Stage) lblNomeCliente.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/TelaClientes.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}