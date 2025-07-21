package br.ufrpe.GUI.telaDetalhesCarros;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorDetalhesCarro {

    @FXML
    private Button btnAdcionarCarrinho;

    @FXML
    private Button btnRemoverCarrinho;

    @FXML
    private Button btnCadastrarAluguel;

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnHome;

    @FXML
    private ImageView imgCarro;

    @FXML
    private Label lblCategoria;

    @FXML
    private Label lblNomeCarro;

    @FXML
    private Label lblPreco;

    @FXML
    private TextArea txtDescricao;

    private Fachada fachada = Fachada.getInstance();
    private Carro carroSelecionado;

    @FXML
    public void initialize() {
        btnAdcionarCarrinho.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs2, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        newScene.setOnKeyPressed(event -> {
                            switch (event.getCode()) {
                                case ESCAPE:
                                    try {
                                        irParaTelaHomePage();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        });
                    }
                });
            }
        });
        atualizarVisibilidadeBotoes();
    }

    @FXML
    void adicionarAoCarrinho(ActionEvent event) {
        try {
            fachada.adicionarAoCarrinho(carroSelecionado);
            atualizarVisibilidadeBotoes();
        } catch (IllegalStateException e) {
            System.out.println("Carrinho cheio! Não é possível adicionar mais carros.");
        }
    }

    @FXML
    void removerDoCarrinho(ActionEvent event) {
        try {
            fachada.removerDoCarrinho(carroSelecionado);
            System.out.println("Carro removido do carrinho: " + carroSelecionado.getModelo());
            atualizarVisibilidadeBotoes();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao remover carro do carrinho: " + e.getMessage());
        }
    }

    public void setCarro(Carro carro) {
        this.carroSelecionado = carro;
        atualizarTela();
    }

    private void atualizarTela() {
        lblNomeCarro.setText(carroSelecionado.getMarca() + " " + carroSelecionado.getModelo());
        lblCategoria.setText(carroSelecionado.getCategoria().toString());
        lblPreco.setText("R$ " + carroSelecionado.getPreco());
        txtDescricao.setText(carroSelecionado.getDescricao());
        atualizarVisibilidadeBotoes();
    }

    private void atualizarVisibilidadeBotoes() {
        boolean carroEstaNoCarrinho = fachada.getCarrinho().contains(carroSelecionado);
        boolean carrinhoNaoVazio = !fachada.getCarrinho().isEmpty();

        btnRemoverCarrinho.setVisible(carroEstaNoCarrinho);
        btnAdcionarCarrinho.setVisible(!carroEstaNoCarrinho);
        btnCadastrarAluguel.setVisible(carrinhoNaoVazio);
    }

    @FXML
    void cadastrarAluguel(ActionEvent event) throws IOException {
        navegarPara("/TelaCadastroAlugueis.fxml");
    }

    @FXML
    void irParaTelaCliente(ActionEvent event) throws IOException {
        navegarPara("/TelaClientes.fxml");
    }

    @FXML
    void irParaTelaHome() throws IOException {
        navegarPara("/TelaInicial.fxml");
    }
    @FXML
    void irParaTelaHomePage() throws IOException {
        navegarPara("/TelaHomePage2.fxml");
    }

    private void navegarPara(String fxmlPath) throws IOException {
        Stage stage = (Stage) txtDescricao.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene novaCena = new Scene(root);
        stage.setScene(novaCena);
        stage.show();
    }
}