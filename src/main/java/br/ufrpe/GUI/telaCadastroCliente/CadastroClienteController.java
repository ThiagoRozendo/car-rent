package br.ufrpe.GUI.telaCadastroCliente;

import br.ufrpe.GUI.telaDetalhesCliente.ControladorDetalhesCliente;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Aluguel;
import br.ufrpe.negocio.beans.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastroClienteController {

    private final Fachada fachada = Fachada.getInstance();
    private Cliente clienteParaEditar = null;
    private String telaAnteriorFxml;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCNH;

    @FXML
    private Label lblAviso;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    public void setTelaAnterior(String fxmlPath) {
        this.telaAnteriorFxml = fxmlPath;
    }

    public void carregarDadosCliente(Cliente cliente) {
        this.clienteParaEditar = cliente;
        txtNome.setText(cliente.getNome());
        txtCPF.setText(cliente.getCpf());
        txtCPF.setDisable(true);
        txtEndereco.setText(cliente.getEndereco());
        txtTelefone.setText(cliente.getTelefone());
        txtEmail.setText(cliente.getEmail());
        txtCNH.setText(cliente.getCnh());
        btnSalvar.setText("Salvar Alterações");
    }

    @FXML
    void salvarCliente(ActionEvent event) {
        String nome = txtNome.getText().trim();
        String cpf = txtCPF.getText().trim();
        String endereco = txtEndereco.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        String cnh = txtCNH.getText().trim();

        try {
            if (clienteParaEditar != null) {
                fachada.editarCliente(clienteParaEditar, nome, endereco, telefone, email, cnh);
                exibirAviso(Color.GREEN, "Cliente atualizado com sucesso!");
            } else {
                Aluguel[] alugueis = new Aluguel[0];
                Cliente novoCliente = new Cliente(nome, cpf, endereco, telefone, email, cnh, alugueis);
                fachada.cadastrarCliente(novoCliente);
                exibirAviso(Color.GREEN, "Cliente cadastrado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            exibirAviso(Color.RED, e.getMessage());
        }
    }

    @FXML
    void voltarParaTelaAnterior(ActionEvent event) throws IOException {
        String destino = (telaAnteriorFxml != null && !telaAnteriorFxml.isEmpty()) ? telaAnteriorFxml : "/TelaInicial.fxml";
        navegarPara(destino);
    }

    private void exibirAviso(Color cor, String mensagem) {
        lblAviso.setVisible(true);
        lblAviso.setTextFill(cor);
        lblAviso.setText(mensagem);
    }

    private void limparCampos() {
        txtNome.clear();
        txtCPF.clear();
        txtEndereco.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtCNH.clear();
    }

    private void navegarPara(String fxmlPath) throws IOException {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}