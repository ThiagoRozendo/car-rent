package br.ufrpe.GUI.telaCadastro;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Funionarios.Administrador;
import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class ControladorCadastro {

    private Fachada fachada = Fachada.getInstance();
    private String telaAnteriorFxml;

    @FXML
    private Label aviso;

    @FXML
    private Label aviso1;

    @FXML
    private Button botaoCadastrarFuncionario;

    @FXML
    private Button botaoCadastrarFuncionario1;

    @FXML
    private ToggleGroup cargoFuncionarioGroup;

    @FXML
    private RadioButton rbAdmin;

    @FXML
    private RadioButton rbAtendente;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmail1;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNome1;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtSalario1;

    @FXML
    private TextField txtSalario11;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private PasswordField txtSenha1;

    @FXML
    private VBox vboxAdmin;

    @FXML
    private VBox vboxAtendente;

    public void setTelaAnterior(String fxmlPath) {
        this.telaAnteriorFxml = fxmlPath;
    }

    @FXML
    public void initialize() {
        Funcionario usuarioLogado = fachada.getUsuarioLogado();
        if (usuarioLogado != null && !(usuarioLogado instanceof Administrador)) {
            vboxAdmin.setDisable(true);
            vboxAtendente.setDisable(true);
            rbAdmin.setDisable(true);
            rbAtendente.setDisable(true);
            aviso.setText("Acesso negado. Apenas administradores podem cadastrar funcionários.");
            aviso.setTextFill(RED);
            aviso.setVisible(true);
        }

        botaoCadastrarFuncionario.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        try {
                            voltarParaTelaAnterior();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void voltarParaTelaAnterior() throws IOException {
        String destino = "/TelaHomePage2.fxml";
        if ("/TelaInicial.fxml".equals(telaAnteriorFxml) || "/TelaExibirFuncionarios.fxml".equals(telaAnteriorFxml)) {
            destino = telaAnteriorFxml;
        }
        navegarPara(destino);
    }

    @FXML
    void RadioAtendenteSelecionado(ActionEvent event) {
        vboxAtendente.setVisible(true);
        vboxAdmin.setVisible(false);
        aviso.setVisible(false);
    }

    @FXML
    void radioAdminSelecionado(ActionEvent event) {
        vboxAtendente.setVisible(false);
        vboxAdmin.setVisible(true);
        aviso1.setVisible(false);
    }

    @FXML
    void cadastrarFuncionario(ActionEvent event) {
        if (rbAdmin.isSelected()) {
            try {
                fachada.cadastrarAdministrador(txtNome.getText(), txtEmail.getText(), txtSenha.getText(), Double.parseDouble(txtSalario.getText()));
                aviso.setVisible(true);
                aviso.setTextFill(GREEN);
                aviso.setText("Administrador cadastrado com sucesso!");
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(evento -> {
                    try {
                        voltarParaTelaAnterior();
                    } catch (Exception e) {
                        aviso.setText("Erro ao carregar tela: " + e.getMessage());
                    }
                });
                pause.play();
            } catch (NumberFormatException e) {
                aviso.setVisible(true);
                aviso.setText("Salário inválido. Apenas números são permitidos.");
            } catch (Exception e) {
                aviso.setVisible(true);
                aviso.setText("Erro ao cadastrar administrador: " + e.getMessage());
            }
        } else if (rbAtendente.isSelected()) {
            try {
                fachada.cadastrarAtendente(txtNome1.getText(), txtEmail1.getText(), txtSenha1.getText(), Double.parseDouble(txtSalario1.getText()), Double.parseDouble(txtSalario11.getText()));
                aviso1.setVisible(true);
                aviso1.setTextFill(GREEN);
                aviso1.setText("Atendente cadastrado com sucesso!");
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(evento -> {
                    try {
                        voltarParaTelaAnterior();
                    } catch (Exception e) {
                        aviso1.setText("Erro ao carregar tela: " + e.getMessage());
                    }
                });
                pause.play();
            } catch (NumberFormatException e) {
                aviso1.setVisible(true);
                aviso1.setText("Vendas ou taxa inválidos. Apenas números são permitidos.");
            } catch (Exception e) {
                aviso1.setVisible(true);
                aviso1.setText("Erro ao cadastrar atendente: " + e.getMessage());
            }
        }
    }

    private void navegarPara(String fxmlPath) throws IOException {
        Stage stage = (Stage) botaoCadastrarFuncionario.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}