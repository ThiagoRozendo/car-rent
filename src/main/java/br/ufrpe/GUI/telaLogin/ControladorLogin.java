package br.ufrpe.GUI.telaLogin;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class ControladorLogin {

    Fachada fachada = Fachada.getInstance();
    @FXML
    private Label aviso;

    @FXML
    private Button botaoEntrar;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    void fazerLogin(ActionEvent event) {

        try {
            fachada.fazerLogin(txtEmail.getText(), txtSenha.getText());
            aviso.setTextFill(GREEN);
            aviso.setVisible(true);
            aviso.setText("Login realizado com sucesso! Redirecionando...");

            botaoEntrar.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                try {
                    navegarParaHomePage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    aviso.setTextFill(RED);
                    aviso.setText("Erro ao carregar a página principal.");
                }
            });
            delay.play();

        } catch (Exception e) {
            aviso.setTextFill(RED);
            aviso.setText(e.getMessage());
            aviso.setVisible(true);
        }
    }

    private void navegarParaHomePage() throws IOException {
        Stage stage = (Stage) botaoEntrar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/TelaHomePage2.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AlugaCar - Home");
        stage.show();
    }

    public void setAviso(String mensagem) {
        aviso.setTextFill(RED);
        aviso.setText(mensagem);
        aviso.setVisible(true);
    }
}