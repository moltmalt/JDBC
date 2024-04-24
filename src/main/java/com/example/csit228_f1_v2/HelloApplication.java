package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CRUD crud = new CRUD();
        System.out.println("Hello");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setStage(primaryStage); // Set the stage to the controller
            primaryStage.setTitle("Your Title");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setTitle("Cat Collection System");
            primaryStage.show();
            crud.createTable1();
            crud.createTable2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

