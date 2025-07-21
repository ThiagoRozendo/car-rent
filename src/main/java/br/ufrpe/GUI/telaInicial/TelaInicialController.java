package br.ufrpe.GUI.telaInicial;

import br.ufrpe.GUI.telaCadastro.ControladorCadastro;
import br.ufrpe.GUI.telaLogin.ControladorLogin;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialController {

    private final Fachada fachada = Fachada.getInstance();

    @FXML
    private HBox loginBox;

    @FXML
    private Button btnLogout;

    @FXML
    public void initialize() {
        if (fachada.getUsuarioLogado() != null) {
            loginBox.setVisible(false);
            loginBox.setManaged(false);
            btnLogout.setVisible(true);
            btnLogout.setManaged(true);
        } else {
            loginBox.setVisible(true);
            loginBox.setManaged(true);
            btnLogout.setVisible(false);
            btnLogout.setManaged(false);
        }

        btnLogout.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        if (fachada.getUsuarioLogado() != null) {
                            try {
                                navegarPara("/TelaHomePage2.fxml", "Car Rent - Home");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    @FXML
    void fazerLogout(ActionEvent event) throws IOException {
        fachada.fazerLogout();
        navegarPara("/TelaInicial.fxml", "Car Rent - Bem-vindo");
    }

    @FXML
    void abrirCadastroCliente(ActionEvent event) throws IOException {
        navegarPara("/CadastroCliente.fxml", "Cadastro de Cliente");
    }

    @FXML
    void abrirCadastroFuncionario(ActionEvent event) throws IOException {
        Funcionario usuarioLogado = fachada.getUsuarioLogado();

        if (usuarioLogado instanceof Administrador) {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cadastro (1).fxml"));
            Parent root = loader.load();

            ControladorCadastro controller = loader.getController();
            controller.setTelaAnterior("/TelaInicial.fxml");

            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Funcionário");
            stage.setScene(scene);
            stage.show();

        } else {
            Stage stage = (Stage) loginBox.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaLogin2.fxml"));
            Parent root = loader.load();
            ControladorLogin loginController = loader.getController();
            loginController.setAviso("Acesso restrito a administradores.");
            Scene scene = new Scene(root);
            stage.setTitle("Login de Funcionário");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException {
        navegarPara("/TelaLogin2.fxml", "Login de Funcionário");
    }

    private void navegarPara(String fxmlPath, String title) throws IOException {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}