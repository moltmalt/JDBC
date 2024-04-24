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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @FXML
    TextField tfCatName;
    @FXML
    TextField tfCatImage;
    @FXML
    Label lblCreateCatWarning;
    @FXML
    Label lblCatImage1;
    @FXML
    Label lblCatImage2;
    @FXML
    Label lblCatImage3;
    @FXML
    Label lblCatImage4;
    @FXML
    Label lblCatImage5;
    @FXML
    Label lblCatName1;
    @FXML
    Label lblCatName2;
    @FXML
    Label lblCatName3;
    @FXML
    Label lblCatName4;
    @FXML
    Label lblCatName5;
    @FXML
    Button renameCat1, renameCat2, renameCat3, renameCat4, renameCat5;
    @FXML
    Button sellCat1, sellCat2, sellCat3, sellCat4, sellCat5;

    public static String sessionUsername;
    public static int sessionID;
    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void login(ActionEvent e) {
        CRUD crud = new CRUD();
        if(crud.readData(tfUsername.getText(), pfPassword.getText())){
            sessionUsername = tfUsername.getText();
            sessionID = crud.getUserID(sessionUsername);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                Parent p = loader.load();
                Controller controller = loader.getController();
                readCat(e);
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

        if(crud.readData(tfUsername.getText(), pfPassword.getText())){
            lblInfo.setText("Already registered! Log-in please!");
        }else if(crud.usernameExists(tfUsername.getText()) && !crud.readDataPassword(pfPassword.getText())){
            lblInfo.setText("Username already taken!");
        }else if(!crud.usernameExists(tfUsername.getText())){
            crud.insertData(tfUsername.getText(), pfPassword.getText());
            lblInfo.setText("Successful registration!");
        }
    }

    public void updatePassword(ActionEvent e) {
        CRUD crud = new CRUD();

        if(crud.readData(sessionUsername, tfOldPassword.getText())){
            crud.updateData(sessionUsername, tfNewPassword.getText());
            lblPasswordInfo.setText("Successfully changed password");
        }else{
            lblPasswordInfo.setText("Incorrect input/s");
        }
    }

    public void deactivate(ActionEvent e) {
        CRUD crud = new CRUD();
        crud.deleteData(sessionUsername);
        logout(e);
    }

    public void insertCat(ActionEvent e){
        CRUD crud = new CRUD();
        if(crud.catExists(tfCatName.getText())){
            lblCreateCatWarning.setText("Cat already exists!");
            return;
        }
        if(tfCatName.getText().equals("") && tfCatImage.getText().equals("")){
            lblCreateCatWarning.setText("Fill up the fields!");
            return;
        }
        crud.createCat(tfCatName.getText(), tfCatImage.getText(), sessionID);
        lblCreateCatWarning.setText("Cat created successfully!");
        readCat(e);
    }

    public void readCat(ActionEvent e) {
        CRUD crud = new CRUD();
        List<Map<String, String>> catData = crud.getCatData(sessionID);
        int labelIndex = 1;

        try{
            for (Map<String, String> catRow : catData) {
                String catName = catRow.get("catName");
                String catImage = catRow.get("catImage");

                switch (labelIndex) {
                    case 1:
                        if (lblCatName1 != null) {
                            lblCatName1.setText(catName);
                        }
                        if (lblCatImage1 != null) {
                            lblCatImage1.setText(catImage);
                        }
                        break;
                    case 2:
                        if (lblCatName2 != null) {
                            lblCatName2.setText(catName);
                        }
                        if (lblCatImage2 != null) {
                            lblCatImage2.setText(catImage);
                        }
                        break;
                    case 3:
                        if (lblCatName3 != null) {
                            lblCatName3.setText(catName);
                        }
                        if (lblCatImage3 != null) {
                            lblCatImage3.setText(catImage);
                        }
                        break;
                    case 4:
                        if (lblCatName4 != null) {
                            lblCatName4.setText(catName);
                        }
                        if (lblCatImage4 != null) {
                            lblCatImage4.setText(catImage);
                        }
                        break;
                    case 5:
                        if (lblCatName5 != null) {
                            lblCatName5.setText(catName);
                        }
                        if (lblCatImage5 != null) {
                            lblCatImage5.setText(catImage);
                        }
                        break;
                }

                labelIndex++;
                if (labelIndex > 5) {
                    break;
                }
            }
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

        while (labelIndex <= 5) {
            switch (labelIndex) {
                case 1:
                    if (lblCatName1!= null) {
                        lblCatName1.setText("None");
                    }
                    if (lblCatImage1 != null) {
                        lblCatImage1.setText("None");
                    }
                    break;
                case 2:
                    if (lblCatName2 != null) {
                        lblCatName2.setText("None");
                    }
                    if (lblCatImage2 != null) {
                        lblCatImage2.setText("None");
                    }
                    break;
                case 3:
                    if (lblCatName3 != null) {
                        lblCatName3.setText("None");
                    }
                    if (lblCatImage3 != null) {
                        lblCatImage3.setText("None");
                    }
                    break;
                case 4:
                    if (lblCatName4 != null) {
                        lblCatName4.setText("None");
                    }
                    if (lblCatImage4 != null) {
                        lblCatImage4.setText("None");
                    }
                    break;
                case 5:
                    if (lblCatName5 != null) {
                        lblCatName5.setText("None");
                    }
                    if (lblCatImage5 != null) {
                        lblCatImage5.setText("None");
                    }
                    break;
            }
            labelIndex++;
        }
    }

    public int getButtonNumber(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            Button clickedButton = (Button) e.getSource();

            if (clickedButton == renameCat1 || clickedButton == sellCat1) {
                return 1;
            } else if (clickedButton == renameCat2 || clickedButton == sellCat2) {
                return 2;
            } else if (clickedButton == renameCat3 || clickedButton == sellCat3) {
                return 3;
            } else if (clickedButton == renameCat4 || clickedButton == sellCat4) {
                return 4;
            } else if (clickedButton == renameCat5 || clickedButton == sellCat5) {
                return 5;
            }
        }
        return 0;
    }


    public void renameCat(ActionEvent e) {
        CRUD crud = new CRUD();
        List<Map<String, String>> catData = crud.getCatData(sessionID);
        int buttonNumber = getButtonNumber(e);

        if (buttonNumber <= catData.size()) {
            Map<String, String> catRow = catData.get(buttonNumber - 1);
            String oldCatName = catRow.get("catName");
            catRow.put("catName", "Jay");
            crud.updateCatData(oldCatName);
            readCat(new ActionEvent());
        }
    }

    public void deleteCat(ActionEvent e) {
        CRUD crud = new CRUD();
        List<Map<String, String>> catData = crud.getCatData(sessionID);
        int buttonNumber = getButtonNumber(e);

        if (buttonNumber <= catData.size()) {
            Map<String, String> catRow = catData.get(buttonNumber - 1);
            crud.deleteCatRow(catRow);
            readCat(new ActionEvent());
        }
    }
}
