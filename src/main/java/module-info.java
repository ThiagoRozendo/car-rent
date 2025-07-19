module car.rent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens br.ufrpe.GUI to javafx.fxml;
    exports br.ufrpe.GUI;
}