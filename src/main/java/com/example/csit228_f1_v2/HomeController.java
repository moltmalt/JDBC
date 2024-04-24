package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setStage(primaryStage);
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setTitle("Cat Collection System");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
