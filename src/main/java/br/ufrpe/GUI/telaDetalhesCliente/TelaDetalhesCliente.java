package br.ufrpe.GUI.telaDetalhesCliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaDetalhesCliente extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TelaDetalhesCliente.fxml"));
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);

        primaryStage.setTitle("Tela de Detalhes de Clientes");
        primaryStage.setScene(tela);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
