module car.rent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires com.google.gson;
    requires kernel;
    requires layout;

    opens br.ufrpe.GUI to javafx.fxml;
    exports br.ufrpe.GUI;
    exports br.ufrpe.GUI.telaLogin;
    opens br.ufrpe.GUI.telaLogin to javafx.fxml;
    exports br.ufrpe.GUI.telaCadastro;
    opens br.ufrpe.GUI.telaCadastro to javafx.fxml;
    exports br.ufrpe.GUI.telaHomePage;
    opens br.ufrpe.GUI.telaHomePage to javafx.fxml;
    opens br.ufrpe.GUI.telaDetalhesCarros;
    exports br.ufrpe.GUI.telaDetalhesCarros to javafx.graphics;
    opens br.ufrpe.GUI.telaInicial to javafx.fxml;
    exports br.ufrpe.GUI.telaInicial;
    opens br.ufrpe.GUI.telaCadastroCliente to javafx.fxml;
    exports br.ufrpe.GUI.telaCadastroCliente;
    opens br.ufrpe.GUI.telaClientes to javafx.fxml;
    exports br.ufrpe.GUI.telaClientes;
    opens br.ufrpe.negocio.beans to javafx.base, javafx.fxml;
    exports br.ufrpe.negocio.beans;
    opens br.ufrpe.GUI.telaExibirFuncionarios to javafx.base, javafx.fxml;
    exports br.ufrpe.GUI.telaExibirFuncionarios;
    opens br.ufrpe.negocio.beans.Funionarios to javafx.base, javafx.fxml;
    opens br.ufrpe.GUI.telaCadastroCarros to javafx.fxml;
    exports br.ufrpe.GUI.telaCadastroCarros;
    opens br.ufrpe.GUI.telaDetalhesCliente to javafx.fxml;
    exports br.ufrpe.GUI.telaDetalhesCliente;
    opens br.ufrpe.GUI.telaCadastroAluguel to javafx.fxml;
    exports br.ufrpe.GUI.telaCadastroAluguel;

}