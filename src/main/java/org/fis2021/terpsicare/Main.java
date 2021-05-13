package org.fis2021.terpsicare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fis2021.terpsicare.services.FileSystemService;
import org.fis2021.terpsicare.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.fis2021.terpsicare.services.UserService.initDatabase;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
        primaryStage.setTitle("TerpsiCare");
        primaryStage.setScene(new Scene(root, 500, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
