/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Dictionary;

/**
 *
 * @author Captain
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private JFXButton addPaneButton;
    @FXML
    private JFXButton deletePaneButton;
    @FXML
    private JFXButton searchPaneButton;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private TextField searchBox;
    @FXML
    private AnchorPane addPane;
    @FXML
    private TextField keyword;
    @FXML
    private TextArea meaningOne;
    @FXML
    private TextArea meaning_two;
    @FXML
    private AnchorPane deletePane;
    @FXML
    private TextField deleteTextBox;
    @FXML
    private Label wordField;
    @FXML
    private Label meaningField;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onAddPaneButton(ActionEvent event) {
        searchPane.setVisible(false);
        addPane.setVisible(true);
        deletePane.setVisible(false);

    }

    @FXML
    private void onDeletePaneButton(ActionEvent event) {
        searchPane.setVisible(false);
        addPane.setVisible(false);
        deletePane.setVisible(true);
    }

    @FXML
    private void onSearchPaneButton(ActionEvent event) {
        searchPane.setVisible(true);
        addPane.setVisible(false);
        deletePane.setVisible(false);
    }

    @FXML
    private void searchWord(ActionEvent event) {
        Alert Falert = new Alert(Alert.AlertType.ERROR);
        Falert.setHeaderText("Error");
        try {
            Socket socket = new Socket("localhost", 1234);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Dictionary dictionary = new Dictionary(searchBox.getText(), null);
            dictionary.setRequest("find");
            objectOutputStream.writeObject(dictionary);
            Dictionary recevied = (Dictionary) objectInputStream.readObject();
            if (recevied.getWasSucceful()) {
                wordField.setText("DEFINATION TERM:  " + recevied.getKeyword());
                String meanings = "";
                int count = 1;
                for (String meaning : recevied.getMeanings()) {
                    meanings = meanings + "#DEFINATION " + count + "\n";
                    meanings = meanings + meaning + "\n\n";
                    count++;

                }
                meaningField.setText(meanings);
            } else {
                Falert.setContentText("Word Does Not Exist!!");
                Falert.showAndWait();

            }

            socket.close();

        } catch (IOException | ClassNotFoundException ex) {

        }

    }

    @FXML
    private void addWord(ActionEvent event) {
        Alert Falert = new Alert(Alert.AlertType.ERROR);
        Falert.setHeaderText("Error");
        try {
            Socket socket = new Socket("localhost", 1234);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> meanings = new ArrayList<>();
            meanings.add(meaningOne.getText());
            if(!meaning_two.getText().isEmpty()){
                meanings.add(meaning_two.getText());
            }
            Dictionary dictionary = new Dictionary(keyword.getText(), meanings);
            dictionary.setRequest("add");
            objectOutputStream.writeObject(dictionary);
            Dictionary recevied = (Dictionary) objectInputStream.readObject();
            if (recevied.getWasSucceful()) {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Smart Dectionary");
                        alert.setContentText("Wrod Removed Sussfully!!");
                        alert.showAndWait();
            } else {
                Falert.setContentText("Word Already Exist!!");
                Falert.showAndWait();

            }

            socket.close();

        } catch (IOException | ClassNotFoundException ex) {

        }

    }

    @FXML
    private void deleteWord(ActionEvent event) {
        Alert Falert = new Alert(Alert.AlertType.ERROR);
        Falert.setHeaderText("Error");
        try {
            Socket socket = new Socket("localhost", 1234);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Dictionary dictionary = new Dictionary(deleteTextBox.getText(), null);
            dictionary.setRequest("remove");
            objectOutputStream.writeObject(dictionary);
            Dictionary recevied = (Dictionary) objectInputStream.readObject();
            if (recevied.getWasSucceful()) {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Smart Dectionary");
                        alert.setContentText("Wrod Removed Sussfully!!");
                        alert.showAndWait();
            } else {
                Falert.setContentText("Word Does Not Exist!!");
                Falert.showAndWait();

            }

            socket.close();

        } catch (IOException | ClassNotFoundException ex) {

        }

    }

}
