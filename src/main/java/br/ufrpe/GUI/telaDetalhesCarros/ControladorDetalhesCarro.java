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
    private ImageView imgCarro;

    @FXML
    private Label lblCategoria;

    @FXML
    private Label lblNomeCarro;

    @FXML
    private Label lblPreco;

    @FXML
    private Button btnRemoverCarrinho;

    @FXML
    private TextArea txtDescricao;
    private Fachada fachada = Fachada.getInstance();
    private Carro carroSelecionado;

    boolean adicionado = false;

    @FXML
    public void initialize() {
        btnAdcionarCarrinho.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ESCAPE:
                            try {
                                voltarTelaAnterior();
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


    @FXML
    void adicionarAoCarrinho(ActionEvent event) {
        try {
            fachada.adicionarAoCarrinho(carroSelecionado);
            btnRemoverCarrinho.setVisible(true);
            System.out.println("Carro adicionado ao carrinho: " + carroSelecionado.getModelo());
        } catch (IllegalStateException e) {
            System.out.println("Carrinho cheio! Não é possível adicionar mais carros.");
        }
    }

    @FXML
    void removerDoCarrinho(ActionEvent event) {
        try{
            fachada.removerDoCarrinho(carroSelecionado);
            System.out.println("Carro removido do carrinho: " + carroSelecionado.getModelo());
        } catch (IllegalStateException e) {
            System.out.println("Erro ao remover carro do carrinho: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
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

        if(fachada.getCarrinho().contains(carroSelecionado)) {
            btnRemoverCarrinho.setVisible(true);
        }

    }

    private void voltarTelaAnterior() throws IOException {

        btnAdcionarCarrinho.getScene().getWindow().hide();

        try {
            Stage stage = (Stage) txtDescricao.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaHomePage2.fxml"));
            Parent root = loader.load();
            Scene novaCena = new Scene(root);
            stage.setScene(novaCena);
            stage.show();

        } catch (Exception e) {
            System.out.println("Erro ao carregar tela: " + e.getMessage());
        }
    }


}
