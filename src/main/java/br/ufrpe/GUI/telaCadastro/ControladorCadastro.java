package br.ufrpe.GUI.telaCadastro;

import br.ufrpe.negocio.Fachada;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.paint.Color.GREEN;

public class ControladorCadastro {

    private Fachada fachada = Fachada.getInstance();
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
    private ToggleGroup cargoFuncionarioGroup2;

    @FXML
    private ToggleGroup cargoFuncionarioGroup3;

    @FXML
    private RadioButton rbAdmin;

    @FXML
    private RadioButton rbAdmin2;

    @FXML
    private RadioButton rbAtendente;

    @FXML
    private RadioButton rbAtendente2;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmail1;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNome1;

    @FXML
    private PasswordField txtSalario;

    @FXML
    private PasswordField txtSalario1;

    @FXML
    private PasswordField txtSalario11;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private PasswordField txtSenha1;

    @FXML
    private VBox vboxAdmin;

    @FXML
    private VBox vboxAtendente;

    @FXML
    void RadioAtendenteSelecionado(ActionEvent event) {

        vboxAtendente.setVisible(true);
        vboxAdmin.setVisible(false);
        rbAdmin2.setSelected(false);
        rbAdmin.setSelected(false);
        aviso1.setVisible(false);
    }

    @FXML
    void cadastrarFuncionario(ActionEvent event) {
        if(rbAdmin.isSelected() || rbAdmin2.isSelected()) {
            try {
                fachada.cadastrarAdministrador(txtNome.getText(), txtEmail.getText(), txtSenha.getText(), Double.parseDouble(txtSalario.getText()));
                aviso.setVisible(true);
                aviso.setTextFill(GREEN);
                aviso.setVisible(true);
                aviso.setText("Administrador cadastrado com sucesso!");

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(evento -> {
                    try {
                        Stage stage = (Stage) txtNome.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaLogin2.fxml"));
                        Parent root = loader.load();
                        Scene novaCena = new Scene(root);
                        stage.setScene(novaCena);
                        stage.show();
                    } catch (Exception e) {
                        aviso1.setText("Erro ao carregar tela: " + e.getMessage());
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
        }

        if(rbAtendente.isSelected() || rbAtendente2.isSelected()) {
            try {
                fachada.cadastrarAtendente(txtNome1.getText(), txtEmail1.getText(), txtSenha1.getText(), Double.parseDouble(txtSalario1.getText()), Double.parseDouble(txtSalario11.getText()));
                aviso1.setVisible(true);
                aviso1.setTextFill(GREEN);
                aviso1.setVisible(true);
                aviso1.setText("Atendente cadastrado com sucesso!");

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(evento -> {
                    try {
                        Stage stage = (Stage) txtNome.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaLogin2.fxml"));
                        Parent root = loader.load();
                        Scene novaCena = new Scene(root);
                        stage.setScene(novaCena);
                        stage.show();
                    } catch (Exception e) {
                        aviso1.setText("Erro ao carregar tela: " + e.getMessage());
                    }
                });
                pause.play();

            } catch (NumberFormatException e) {
                aviso1.setVisible(true);
                aviso1.setText("Salário ou taxa inválidos. Apenas números são permitidos.");

            } catch (Exception e) {
                aviso1.setVisible(true);
                aviso1.setText("Erro ao cadastrar atendente: " + e.getMessage());
            }
        }
    }

    @FXML
    void radioAdminSelecionado(ActionEvent event) {

        vboxAtendente.setVisible(false);
        vboxAdmin.setVisible(true);
        rbAdmin2.setSelected(true);
        rbAdmin.setSelected(true);
        aviso1.setVisible(false);
    }

}
