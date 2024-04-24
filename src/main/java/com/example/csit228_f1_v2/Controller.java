package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    TextField tfOldPassword;

    @FXML
    TextField tfNewPassword;
    @FXML
    TextField pfPassword;
    @FXML
    Label lblPasswordInfo;

    @FXML
    TextField tfUsername;

    @FXML
    Label lblInfo;

    public static String sessionUsername;
    
    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void login(ActionEvent e) {
        CRUD crud = new CRUD();
        if(crud.readData(tfUsername.getText(), pfPassword.getText())){
            System.out.println("Hello");
            sessionUsername = tfUsername.getText();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                Parent p = loader.load();
                Controller controller = loader.getController();
                controller.setStage(stage);
                Scene s = new Scene(p);
                stage.setScene(s);
                stage.show();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            lblInfo.setText("Unsuccessful Log-in!");
        }
    }

    public void logout(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Parent p = loader.load();
                Controller controller = loader.getController();
                controller.setStage(stage);
                Scene s = new Scene(p);
                stage.setScene(s);
                stage.show();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
    }

    public void register(ActionEvent e) {
        CRUD crud = new CRUD();
        System.out.println("Registering " + tfUsername.getText());

        if(crud.readData(tfUsername.getText(), pfPassword.getText())){
            lblInfo.setText("Already registered! Log-in please!");
        }else if(crud.usernameExists(tfUsername.getText()) && !crud.readDataPassword(pfPassword.getText())){
            lblInfo.setText("Username already taken!");
        }else if(!crud.usernameExists(tfUsername.getText())){
            crud.insertData(tfUsername.getText(), pfPassword.getText());
        }
    }

    public void updatePassword(ActionEvent e) {
        CRUD crud = new CRUD();
        System.out.println("Updating...");

        if(crud.readData(sessionUsername, tfOldPassword.getText())){
            crud.updateData(sessionUsername, tfNewPassword.getText());
            lblPasswordInfo.setText("Successfully changed password");
        }else{
            lblPasswordInfo.setText("Incorrect input/s");
        }
    }

    public void deactivate(ActionEvent e) {
        CRUD crud = new CRUD();
        System.out.println("Deleting...");
        crud.deleteData(sessionUsername);
        logout(e);
    }
}
