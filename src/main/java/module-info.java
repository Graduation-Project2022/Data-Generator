module com.example.datagenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires java.logging;


    opens com.example.datagenerator to javafx.fxml;
    exports com.example.datagenerator;
}