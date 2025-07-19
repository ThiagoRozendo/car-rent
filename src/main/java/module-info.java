module car.rent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens br.ufrpe.GUI to javafx.fxml;
    exports br.ufrpe.GUI;
    exports br.ufrpe.GUI.telaLogin;
    opens br.ufrpe.GUI.telaLogin to javafx.fxml;
    exports br.ufrpe.GUI.telaCadastro;
    opens br.ufrpe.GUI.telaCadastro to javafx.fxml;
    exports br.ufrpe.GUI.telaHomePage;
    opens br.ufrpe.GUI.telaHomePage to javafx.fxml;
}