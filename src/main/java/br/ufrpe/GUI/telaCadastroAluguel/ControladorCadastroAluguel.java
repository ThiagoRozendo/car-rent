package br.ufrpe.GUI.telaCadastroAluguel;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Cliente;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ControladorCadastroAluguel {

    private Fachada fachada = Fachada.getInstance();

    @FXML
    private Label aviso;

    @FXML
    private Button botaoFinalizarAluguel;

    @FXML
    private ComboBox<Cliente> comboClientes;

    @FXML
    private DatePicker dateFim;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private VBox containerCarros;

    @FXML
    private Label lblTotalDias;

    @FXML
    private Label lblValorTotal;

    @FXML
    public void initialize() {
        carregarDadosIniciais();
        configurarListeners();
        botaoFinalizarAluguel.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        try {
                            navegarPara("/TelaHomePage2.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void carregarDadosIniciais() {
        comboClientes.getItems().setAll(Arrays.asList(fachada.listarClientes()));
        containerCarros.getChildren().clear();
        List<Carro> carrosNoCarrinho = fachada.getCarrinho();
        if (carrosNoCarrinho.isEmpty()) {
            containerCarros.getChildren().add(new Label("Nenhum carro no carrinho."));
        } else {
            for (Carro carro : carrosNoCarrinho) {
                containerCarros.getChildren().add(criarCardCarro(carro));
            }
        }
    }

    private Node criarCardCarro(Carro carro) {
        VBox card = new VBox(5.0);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-background-radius: 5;");
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(250.0);
        imageView.setPreserveRatio(true);
        Label nomeLabel = new Label(carro.getMarca() + " " + carro.getModelo());
        nomeLabel.setFont(new Font("System Bold", 18.0));
        Label precoLabel = new Label(String.format(new Locale("pt", "BR"), "Diária: R$ %.2f", carro.getPreco()));
        precoLabel.setFont(new Font("System Bold", 14.0));
        precoLabel.setTextFill(Color.DARKGREEN);
        card.getChildren().addAll(imageView, nomeLabel, new Separator(), precoLabel);
        return card;
    }

    private void configurarListeners() {
        comboClientes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (CPF: " + item.getCpf() + ")");
                }
            }
        });
        comboClientes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        dateInicio.valueProperty().addListener((obs, oldVal, newVal) -> calcularValorTotal());
        dateFim.valueProperty().addListener((obs, oldVal, newVal) -> calcularValorTotal());
    }

    private void calcularValorTotal() {
        LocalDate inicio = dateInicio.getValue();
        LocalDate fim = dateFim.getValue();
        List<Carro> carrosNoCarrinho = fachada.getCarrinho();

        if (inicio == null || fim == null || fim.isBefore(inicio) || carrosNoCarrinho.isEmpty()) {
            lblValorTotal.setText("R$ 0,00");
            lblTotalDias.setText("(Selecione as datas para calcular)");
            return;
        }

        long dias = ChronoUnit.DAYS.between(inicio, fim);
        if (dias == 0) dias = 1;

        double precoTotalDiarias = carrosNoCarrinho.stream().mapToDouble(Carro::getPreco).sum();
        double valorTotal = precoTotalDiarias * dias;

        lblValorTotal.setText(String.format(new Locale("pt", "BR"), "R$ %.2f", valorTotal));
        lblTotalDias.setText(String.format("Total para %d dias", dias));
    }

    @FXML
    void finalizarAluguel(ActionEvent event) {
        Cliente cliente = comboClientes.getValue();
        LocalDate inicio = dateInicio.getValue();
        LocalDate fim = dateFim.getValue();
        List<Carro> carrinho = fachada.getCarrinho();

        if (cliente == null || inicio == null || fim == null) {
            exibirAviso(Color.RED, "Erro: Cliente e datas devem ser selecionados.");
            return;
        }
        if (fim.isBefore(inicio)) {
            exibirAviso(Color.RED, "Erro: A data de fim deve ser posterior à data de início.");
            return;
        }
        if (carrinho.isEmpty()) {
            exibirAviso(Color.RED, "Erro: O carrinho de compras está vazio.");
            return;
        }

        if (fachada.clientePossuiAluguelAtrasado(cliente.getCpf())) {
            exibirAviso(Color.RED, "Ação bloqueada: O cliente possui aluguéis com devolução atrasada.");
            return;
        }

        try {
            fachada.cadastrarAluguel(inicio, fim, carrinho.toArray(new Carro[0]), cliente.getCpf());

            exibirAviso(Color.GREEN, "Aluguel cadastrado com sucesso! Retornando à tela principal...");
            botaoFinalizarAluguel.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                try {
                    navegarPara("/TelaHomePage2.fxml");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            delay.play();

        } catch (Exception e) {
            exibirAviso(Color.RED, "Erro ao cadastrar aluguel: " + e.getMessage());
        }
    }

    private void exibirAviso(Color cor, String mensagem) {
        aviso.setTextFill(cor);
        aviso.setText(mensagem);
        aviso.setVisible(true);
    }

    private void navegarPara(String fxmlPath) throws IOException {
        Stage stage = (Stage) botaoFinalizarAluguel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}