package br.ufrpe.GUI.telaCadastroCarros;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControladorCadastroCarros {

    private final Fachada fachada = Fachada.getInstance();
    private Carro carroParaEditar = null;

    @FXML
    private Label aviso;

    @FXML
    private Button botaoCadastrarCarro;

    @FXML
    private ComboBox<Categoria> comboCategoria;

    @FXML
    private TextField txtAno;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtPreco;

    @FXML
    public void initialize() {
        comboCategoria.getItems().setAll(Categoria.values());
        botaoCadastrarCarro.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        try {
                            voltarParaHomePage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void carregarDadosCarro(Carro carro) {
        this.carroParaEditar = carro;
        txtMarca.setText(carro.getMarca());
        txtModelo.setText(carro.getModelo());
        txtAno.setText(String.valueOf(carro.getAnoFabricacao()));
        txtPlaca.setText(carro.getPlaca());
        txtPlaca.setDisable(true);
        txtPreco.setText(String.valueOf(carro.getPreco()));
        txtDescricao.setText(carro.getDescricao());
        comboCategoria.setValue(carro.getCategoria());
        botaoCadastrarCarro.setText("Salvar Alterações");
    }

    @FXML
    void cadastrarCarro(ActionEvent event) {
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String anoStr = txtAno.getText().trim();
        String placa = txtPlaca.getText().trim();
        String precoStr = txtPreco.getText().trim();
        String descricao = txtDescricao.getText().trim();
        Categoria categoria = comboCategoria.getValue();

        if (marca.isEmpty() || modelo.isEmpty() || anoStr.isEmpty() || placa.isEmpty() || precoStr.isEmpty() || categoria == null) {
            exibirAviso(Color.RED, "Erro: Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int ano = Integer.parseInt(anoStr);
            double preco = Double.parseDouble(precoStr);

            if (carroParaEditar != null) {
                fachada.editarCarro(marca, modelo, ano, placa, categoria, carroParaEditar.isStatus(), preco, descricao);
                exibirAviso(Color.GREEN, "Carro atualizado com sucesso. Retornando...");
            } else {
                fachada.cadastrarCarro(marca, modelo, ano, placa, categoria, true, preco, descricao, "Cadastrado via interface gráfica");
                exibirAviso(Color.GREEN, "Carro cadastrado com sucesso. Retornando...");
            }

            botaoCadastrarCarro.setDisable(true);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                try {
                    voltarParaHomePage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            delay.play();

        } catch (NumberFormatException e) {
            exibirAviso(Color.RED, "Erro: Ano e Preço devem ser números válidos.");
        } catch (Exception e) {
            exibirAviso(Color.RED, "Erro: " + e.getMessage());
        }
    }

    private void voltarParaHomePage() throws IOException {
        Stage stage = (Stage) botaoCadastrarCarro.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/TelaHomePage2.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void exibirAviso(Color cor, String mensagem) {
        aviso.setTextFill(cor);
        aviso.setText(mensagem);
        aviso.setVisible(true);
    }
}