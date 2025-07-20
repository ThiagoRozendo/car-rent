package br.ufrpe.GUI.telaHomePage;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import br.ufrpe.GUI.telaDetalhesCarros.ControladorDetalhesCarro;

import java.util.ArrayList;

import static br.ufrpe.negocio.beans.Categoria.ECONOMICO;

public class ControladorHomePage {

    private Fachada fachada = Fachada.getInstance();

    @FXML
    private HBox containerFiltros;

    @FXML
    private TilePane tilePaneCarros;

    @FXML
    private TextField txtBusca;


    @FXML
    public void initialize() {
        carregarCarros();
    }

    private void carregarCarros() {
        fachada.cadastrarCarro("Fiat", "Uno", 2020, "ABC1234", Categoria.ECONOMICO, true, 150.0, "carro fiat uno mt legal", "Carro adicionado com sucesso");
        fachada.cadastrarCarro("Fiat", "Uno", 2020, "asd", Categoria.ECONOMICO, true, 150.0, "carro fiat uno mais ou menos", "Carro adicionado com sucesso");
        fachada.cadastrarCarro("Fiat", "Uno", 2020, "asasdd", Categoria.ECONOMICO, true, 150.0, "carro fiat uno meio paia", "Carro adicionado com sucesso");
        fachada.cadastrarCarro("VW", "Gol", 2020, "d32d23d", Categoria.ECONOMICO, true, 300000.0, "gol bolinha novo", "Carro adicionado com sucesso");

        Carro[] carros = fachada.listarCarros();
        for (Carro carro : carros) {
            VBox card = criarCardCarro(carro);
            tilePaneCarros.getChildren().add(card);
        }
    }

    private VBox criarCardCarro(Carro carro) {
        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPrefWidth(200);
        box.setPrefHeight(150);

        Label lblMarca = new Label(carro.getMarca());
        Label lblModelo = new Label(carro.getModelo());
        Label lblPreco = new Label("R$ " + String.format("%.2f", carro.getPreco()));
        Label lbAno = new Label("Ano: " + carro.getAnoFabricacao());

        lblMarca.setStyle("-fx-font-weight: bold;");
        lblModelo.setStyle("-fx-font-size: 14px;");
        lblPreco.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        lbAno.setStyle("-fx-font-size: 14px;");

        box.getChildren().addAll(lblMarca, lblModelo, lbAno, lblPreco);

        // evento de clique
        box.setOnMouseClicked(event -> {
            System.out.println("Carro clicado: " + carro.getModelo());
            try {
                Stage stage = (Stage) txtBusca.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDetalhesCarro.fxml"));
                Parent root = loader.load();

                ControladorDetalhesCarro controlador = loader.getController();
                controlador.setCarro(carro);


                Scene novaCena = new Scene(root);
                stage.setScene(novaCena);
                stage.show();

            } catch (Exception e) {
                System.out.println("Erro ao carregar tela: " + e.getMessage());
            }
        });

        // efeito de hover
        box.setOnMouseEntered(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), box);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        box.setOnMouseExited(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), box);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });


        return box;
    }

}
