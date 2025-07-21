package br.ufrpe.GUI.telaHomePage;

import br.ufrpe.GUI.telaCadastroCarros.ControladorCadastroCarros;
import br.ufrpe.GUI.telaDetalhesCarros.ControladorDetalhesCarro;
import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Cliente;
import br.ufrpe.negocio.beans.Notificacao;
import br.ufrpe.servicos.BackupService;
import br.ufrpe.servicos.RelatorioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorHomePage {

    private final Fachada fachada = Fachada.getInstance();
    private final BackupService backupService = new BackupService(fachada);
    private final RelatorioService relatorioService = new RelatorioService(fachada);
    private ObservableList<Carro> masterData = FXCollections.observableArrayList();
    private FilteredList<Carro> dadosFiltrados;

    @FXML
    private TilePane tilePaneCarros;

    @FXML
    private TextField txtBusca;

    @FXML
    private ComboBox<String> comboBusca;

    @FXML
    private CheckBox checkMostrarAlugados;

    @FXML
    private Button btnCadastrarCarro;

    @FXML
    private Button btnCadastrarAluguel;

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnFazerBackup;

    @FXML
    private Button btnNotificacoes;

    @FXML
    private Button btnRelatorios;

    @FXML
    public void initialize() {
        configurarBusca();
        refreshTela();
    }

    private void configurarBusca() {
        comboBusca.getItems().addAll("Modelo", "Marca", "Placa");
        comboBusca.setValue("Modelo");
        dadosFiltrados = new FilteredList<>(masterData, p -> true);
        txtBusca.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());
        comboBusca.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());
        checkMostrarAlugados.selectedProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());
    }

    private void aplicarFiltro() {
        String criterio = comboBusca.getValue();
        String texto = txtBusca.getText();
        boolean mostrarAlugados = checkMostrarAlugados.isSelected();

        dadosFiltrados.setPredicate(carro -> {
            boolean statusOk = mostrarAlugados || carro.isStatus();
            boolean buscaOk = true;

            if (texto != null && !texto.isEmpty()) {
                String termoBusca = texto.toLowerCase();
                switch (criterio) {
                    case "Marca": buscaOk = carro.getMarca().toLowerCase().contains(termoBusca);
                    break;

                    case "Placa": buscaOk = carro.getPlaca().toLowerCase().contains(termoBusca);
                    break;

                    case "Modelo": default: buscaOk = carro.getModelo().toLowerCase().contains(termoBusca);
                    break;
                }
            }
            return statusOk && buscaOk;
        });
        atualizarTilePane();
    }

    private void refreshTela() {
        masterData.setAll(Arrays.asList(fachada.listarCarros()));
        aplicarFiltro();
        atualizarVisibilidadeBotoes();
    }

    private void atualizarTilePane() {
        tilePaneCarros.getChildren().clear();
        for (Carro carro : dadosFiltrados) {
            tilePaneCarros.getChildren().add(criarCardCarro(carro));
        }
    }

    private BorderPane criarCardCarro(Carro carro) {
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(10));
        infoBox.setPrefWidth(200);

        Label lblMarca = new Label(carro.getMarca());
        Label lblModelo = new Label(carro.getModelo());
        Label lblPreco = new Label("R$ " + String.format("%.2f", carro.getPreco()) + "/dia");
        Label lbAno = new Label("Ano: " + carro.getAnoFabricacao());

        lblMarca.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        lblModelo.setStyle("-fx-font-size: 14px;");
        lblPreco.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
        lbAno.setStyle("-fx-font-size: 14px;");
        infoBox.getChildren().addAll(lblMarca, lblModelo, lbAno, lblPreco);

        ImageView removeIcon = new ImageView(new Image(getClass().getResourceAsStream("/remove.png")));
        removeIcon.setFitHeight(20);
        removeIcon.setFitWidth(20);
        removeIcon.setStyle("-fx-cursor: hand;");
        removeIcon.setOnMouseClicked(event -> {
            removerCarro(carro);
            event.consume();
        });

        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/editar.png")));
        editIcon.setFitHeight(20);
        editIcon.setFitWidth(20);
        editIcon.setStyle("-fx-cursor: hand;");
        editIcon.setOnMouseClicked(event -> {
            try {
                abrirEdicaoCarro(carro);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });

        HBox iconBox = new HBox(10, editIcon, removeIcon);
        iconBox.setAlignment(Pos.CENTER_RIGHT);
        iconBox.setPadding(new Insets(5, 8, 0, 0));
        iconBox.setVisible(false);

        BorderPane cardPane = new BorderPane();
        cardPane.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        cardPane.setTop(iconBox);
        cardPane.setCenter(infoBox);

        if (!carro.isStatus()) {
            infoBox.setOpacity(0.6);
            Label lblAlugado = new Label("ALUGADO");
            lblAlugado.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 5; -fx-font-weight: bold;");

            StackPane centerPane = new StackPane(infoBox, lblAlugado);
            cardPane.setCenter(centerPane);
        }

        cardPane.setOnMouseClicked(event -> {
            if (carro.isStatus()) {
                abrirDetalhesCarro(carro);
            } else {
                solicitarReserva(carro);
            }
        });

        cardPane.setOnMouseEntered(event -> {
            cardPane.setStyle("-fx-background-color: white; -fx-border-color: #007bff; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
            if(fachada.getUsuarioLogado() != null) {
                iconBox.setVisible(true);
            }
        });

        cardPane.setOnMouseExited(event -> {
            cardPane.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");
            iconBox.setVisible(false);
        });

        return cardPane;
    }

    private void removerCarro(Carro carro) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Remover " + carro.getMarca() + " " + carro.getModelo() + "?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            fachada.excluirCarro(carro.getPlaca(), "Removido pelo funcionário.");
            refreshTela();
        }
    }

    private void abrirEdicaoCarro(Carro carro) throws IOException {
        Stage stage = (Stage) txtBusca.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaCadastroCarros.fxml"));
        Parent root = loader.load();

        ControladorCadastroCarros controller = loader.getController();
        controller.carregarDadosCarro(carro);

        Scene novaCena = new Scene(root);
        stage.setScene(novaCena);
        stage.show();
    }

    private void solicitarReserva(Carro carro) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Carro Indisponível");
        alert.setHeaderText("Deseja registrar seu interesse neste carro?");
        alert.setContentText("Você será notificado quando o " + carro.getMarca() + " " + carro.getModelo() + " estiver disponível.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (fachada.listarClientes().length > 0) {
                Cliente clienteInteressado = fachada.listarClientes()[0];
                fachada.solicitarReserva(carro, clienteInteressado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Interesse Registrado", "Seu interesse foi registrado! Verifique suas notificações.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Cliente", "Nenhum cliente cadastrado para registrar o interesse.");
            }
        }
    }

    private void abrirDetalhesCarro(Carro carro) {
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
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    private void atualizarVisibilidadeBotoes() {
        boolean isFuncionarioLogado = fachada.getUsuarioLogado() != null;
        btnCadastrarCarro.setVisible(isFuncionarioLogado);
        btnFazerBackup.setVisible(isFuncionarioLogado);
        btnRelatorios.setVisible(isFuncionarioLogado);
        btnCadastrarAluguel.setVisible(!fachada.getCarrinho().isEmpty());
    }

    @FXML
    void verNotificacoes(ActionEvent event) {
        List<Notificacao> naoLidas = fachada.getNotificacoesNaoLidas();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificações");

        if (naoLidas.isEmpty()) {
            alert.setHeaderText("Nenhuma nova notificação.");
            alert.setContentText("");
        } else {
            alert.setHeaderText("Você tem " + naoLidas.size() + " novas notificações:");

            String conteudo = naoLidas.stream()
                    .map(Notificacao::toString)
                    .collect(Collectors.joining("\n"));
            alert.setContentText(conteudo);
        }
        alert.showAndWait();
    }

    @FXML
    void fazerBackup(ActionEvent event) {
        List<String> formatos = new ArrayList<>();
        formatos.add("CSV");
        formatos.add("JSON");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("CSV", formatos);
        dialog.setTitle("Backup de Dados");
        dialog.setHeaderText("Escolha o formato do backup");
        dialog.setContentText("Formato:");
        dialog.showAndWait().ifPresent(formato -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Selecione a Pasta para Salvar o Backup");
            Stage stage = (Stage) btnFazerBackup.getScene().getWindow();
            File diretorio = directoryChooser.showDialog(stage);

            if (diretorio != null) {
                try {
                    backupService.gerarBackup(diretorio, formato);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Backup realizado com sucesso na pasta:\n" + diretorio.getAbsolutePath());
                } catch (IOException e) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao gerar o backup:\n" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void abrirMenuRelatorios(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("Carros Disponíveis");
        choices.add("Aluguéis Ativos");
        choices.add("Histórico de Aluguéis de um Cliente");
        choices.add("Clientes com Aluguéis Atrasados");
        choices.add("Carros por Categoria");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);

        dialog.setTitle("Gerador de Relatórios");
        dialog.setHeaderText("Escolha o tipo de relatório que deseja gerar.");
        dialog.setContentText("Tipo de Relatório:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String valor = result.get();
            gerarRelatorioSelecionado(valor);
        }

    }

    private void gerarRelatorioSelecionado(String tipoRelatorio) {
        Stage stage = (Stage) btnRelatorios.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        try {
            File arquivo;
            boolean sucesso = false;
            switch (tipoRelatorio) {
                case "Carros Disponíveis":
                    fileChooser.setInitialFileName("relatorio_carros_disponiveis.pdf");
                    arquivo = fileChooser.showSaveDialog(stage);
                    if (arquivo != null) {
                        relatorioService.gerarRelatorioCarrosDisponiveis(arquivo);
                        sucesso = true;
                    }
                    break;

                case "Aluguéis Ativos":
                    fileChooser.setInitialFileName("relatorio_alugueis_ativos.pdf");
                    arquivo = fileChooser.showSaveDialog(stage);
                    if (arquivo != null) {
                        relatorioService.gerarRelatorioAlugueisAtivos(arquivo);
                        sucesso = true;
                    }
                    break;

                case "Histórico de Aluguéis de um Cliente":
                    Cliente clienteSelecionado = selecionarCliente();
                    if (clienteSelecionado != null) {
                        fileChooser.setInitialFileName("historico_" + clienteSelecionado.getNome().replace(" ", "_") + ".pdf");
                        arquivo = fileChooser.showSaveDialog(stage);
                        if (arquivo != null) {
                            relatorioService.gerarRelatorioHistoricoCliente(arquivo, clienteSelecionado);
                            sucesso = true;
                        }
                    }
                    break;

                case "Clientes com Aluguéis Atrasados":
                    fileChooser.setInitialFileName("relatorio_clientes_atrasados.pdf");
                    arquivo = fileChooser.showSaveDialog(stage);
                    if (arquivo != null) {
                        relatorioService.gerarRelatorioClientesAtrasados(arquivo);
                        sucesso = true;
                    }
                    break;

                case "Carros por Categoria":
                    fileChooser.setInitialFileName("relatorio_carros_por_categoria.pdf");
                    arquivo = fileChooser.showSaveDialog(stage);
                    if (arquivo != null) {
                        relatorioService.gerarRelatorioCarrosPorCategoria(arquivo);
                        sucesso = true;
                    }
                    break;

            }
            if (sucesso) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório gerado com sucesso!");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Cliente selecionarCliente() {
        List<Cliente> clientes = Arrays.asList(fachada.listarClientes());
        if (clientes.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum cliente cadastrado para gerar o relatório.");
            return null;
        }
        ChoiceDialog<Cliente> dialog = new ChoiceDialog<>(clientes.get(0), clientes);
        dialog.setTitle("Seleção de Cliente");
        dialog.setHeaderText("Selecione um cliente para gerar o histórico.");
        dialog.setContentText("Cliente:");
        Optional<Cliente> result = dialog.showAndWait();
        return result.orElse(null);
    }

    @FXML
    void abrirCadastroCarro(ActionEvent event) throws IOException {
        navegarPara("/TelaCadastroCarros.fxml");
    }

    @FXML
    void cadastrarAluguel(ActionEvent event) throws IOException {
        navegarPara("/TelaCadastroAlugueis.fxml");
    }

    @FXML
    void irParaTelaCliente(ActionEvent event) throws IOException {
        navegarPara("/TelaClientes.fxml");
    }

    @FXML
    void irParaAreaFuncionario(ActionEvent event) throws IOException {
        navegarPara("/TelaExibirFuncionarios.fxml");
    }

    @FXML
    void irParaTelaHome(ActionEvent event) throws IOException {
        navegarPara("/TelaInicial.fxml");
    }

    private void navegarPara(String fxmlPath) throws IOException {
        Stage stage = (Stage) txtBusca.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene novaCena = new Scene(root);
        stage.setScene(novaCena);
        stage.show();
    }

    private void adicionarDadosDeTeste() {
    }
}