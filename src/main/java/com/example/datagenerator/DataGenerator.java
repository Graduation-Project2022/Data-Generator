package com.example.datagenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
public class DataGenerator extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DataGenerator.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 550);
        stage.setTitle("Data Generator");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}