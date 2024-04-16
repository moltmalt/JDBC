package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("Welcome to CIT");
        txtWelcome.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 69));
        txtWelcome.setFill(Color.RED);

        grid.setPadding(new Insets(20));

        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setTextFill(Color.LIGHTSKYBLUE);
        lbUsername.setFont(Font.font(30));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(30));


        Label lbPassword = new Label("Password");
        lbPassword.setFont(Font.font(30));
        lbPassword.setTextFill(Color.CHARTREUSE);
        grid.add(lbPassword, 0, 2);

        PasswordField pfPassword = new PasswordField();
        pfPassword.setFont(Font.font(30));
        grid.add(pfPassword, 1, 2);

        TextField tmpPassword = new TextField(pfPassword.getText());
        tmpPassword.setFont(Font.font(30));
        grid.add(tmpPassword, 1, 2);
        tmpPassword.setVisible(false);

        ToggleButton btnShow = new ToggleButton("( )");

        btnShow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setText(pfPassword.getText());
                tmpPassword.setVisible(true);
            }
        });

        EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setVisible(false);
                pfPassword.setText(tmpPassword.getText());
            }
        };

        btnShow.setOnMouseReleased(release);
        btnShow.setOnMouseExited(release);
        grid.add(btnShow, 2,2);

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(40));
        grid.add(btnRegister, 0, 5, 2, 1);

        Button btnLogin = new Button("Log In");
        btnLogin.setFont(Font.font(40));
        grid.add(btnLogin, 0, 6, 2, 1);

        Button btnUpdate = new Button("Update");
        btnLogin.setFont(Font.font(40));
        grid.add(btnUpdate, 0, 7, 2, 1);

        Button btnDelete = new Button("Deactivate");
        btnLogin.setFont(Font.font(40));
        grid.add(btnDelete, 0, 8, 2, 1);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CRUD crud = new CRUD();
                if(crud.readData(tfUsername.getText(), pfPassword.getText())){
                    txtWelcome.setText("Successful Log-in!");
                }else{
                    txtWelcome.setText("Unsuccessful Log-in!");
                }
            }
        });

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CRUD crud = new CRUD();
                System.out.println("Logging in...");

                if(!crud.readData(tfUsername.getText(), pfPassword.getText())){
                    crud.insertData(tfUsername.getText(), pfPassword.getText());
                    txtWelcome.setText("Successful registration! Hello " + tfUsername.getText());
                }else{
                    txtWelcome.setText("Already registered! Log-in please!");
                }
            }
        });

        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CRUD crud = new CRUD();
                System.out.println("Updating...");

                if(!crud.readData(tfUsername.getText(), pfPassword.getText())){
                    if(crud.readData(tfUsername.getText()) && crud.readData(tfUsername.getText())){
                        crud.updateData(tfUsername.getText(), pfPassword.getText());
                        txtWelcome.setText("Wrong pass so I changed it!");
                    }
                }else{
                    txtWelcome.setText("Wring inputs/Password still same");
                }
            }
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CRUD crud = new CRUD();
                System.out.println("Updating...");

                if(crud.readData(tfUsername.getText(), pfPassword.getText())){
                    crud.deleteData(tfUsername.getText(), pfPassword.getText());
                    txtWelcome.setText("Successful deactivation!");
                }else{
                    txtWelcome.setText("Incorrect credentials!");
                }
            }
        });

        Scene scene = new Scene(grid, 700, 500, Color.BLACK);
        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }

    public static void main(String[] args) {
        launch();
    }
}