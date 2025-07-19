package br.ufrpe.GUI.telaDetalhesCarros;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ControladorDetalhesCarro {

    @FXML
    private Button botaoConfirmarAluguel;

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

    @FXML
    void confirmarAluguel(ActionEvent event) {

    }

    private Fachada fachada = Fachada.getInstance();
    private Carro carroSelecionado;

    public void setCarro(Carro carro) {
        this.carroSelecionado = carro;
        atualizarTela();
    }

    private void atualizarTela() {
        lblNomeCarro.setText(carroSelecionado.getMarca() + " " + carroSelecionado.getModelo());
        lblCategoria.setText(carroSelecionado.getCategoria().toString());
        lblPreco.setText("R$ " + carroSelecionado.getPreco());
    }

}
