package br.ufrpe.GUI;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Funionarios.Atendente;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static javafx.scene.paint.Color.GREEN;

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
    void fazerLogin(ActionEvent event) throws DadosInvalidosException {

        fachada.cadastrarAtendente("Jonas", "jonas@gmail.com", "123456789", 123.423, 25);
        fachada.cadastrarAdministrador("João", "joao@gmail.com", "123456789", 3450.00);


        try{
            fachada.fazerLogin(txtEmail.getText(), txtSenha.getText());
            aviso.setTextFill(GREEN);
            aviso.setVisible(true);
            aviso.setText("Login realizado com sucesso!");

        } catch (Exception e){
            aviso.setText(e.getMessage());
            aviso.setVisible(true);
            return;
        }
    }

}
