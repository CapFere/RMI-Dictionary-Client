/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient;

import com.jfoenix.controls.JFXButton;
import interfaces.DictionaryServerInterface;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    public int PORT;
    public String IP;
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

    public void setParameters(int port, String ip) {
        this.PORT = port;
        this.IP = ip;
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
        if (searchBox.getText().isEmpty()) {
            Falert.setContentText("Empty Filed, Please Insert word");
            Falert.showAndWait();
        } else if (!searchBox.getText().matches("[a-zA-Z]+")) {
            Falert.setContentText("Word can only be letter");
            Falert.showAndWait();
        } else {
            try {
                Dictionary dictionary = new Dictionary(searchBox.getText(), null);
                Registry registry = LocateRegistry.getRegistry(this.IP,this.PORT);
                DictionaryServerInterface dictionaryServerInterface = (DictionaryServerInterface)registry.lookup("dict");
                Dictionary recevied = dictionaryServerInterface.findWord(dictionary);
                
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


            } catch (Exception ex) {
                Falert.setContentText("Connection To The Server Lost");
                Falert.showAndWait();
            }
        }

    }

    @FXML
    private void addWord(ActionEvent event) {
        Alert Falert = new Alert(Alert.AlertType.ERROR);
        Falert.setHeaderText("Error");
        if (keyword.getText().isEmpty()) {
            Falert.setContentText("Empty Keyword, Please Insert Word");
            Falert.showAndWait();
        } else if (!keyword.getText().matches("[a-zA-Z]+")) {
            Falert.setContentText("Word can only be letter");
            Falert.showAndWait();
        } else if (meaningOne.getText().isEmpty()) {
            Falert.setContentText("Empty Meaning, Please Insert Meaning");
            Falert.showAndWait();
        } else if (meaningOne.getText().length() < 10) {
            Falert.setContentText("Meaning Must be atleast 10 letters");
            Falert.showAndWait();
        } else {
            try {
                ArrayList<String> meanings = new ArrayList<>();
                meanings.add(meaningOne.getText());
                if (!meaning_two.getText().isEmpty()) {
                    meanings.add(meaning_two.getText());
                }
                Dictionary dictionary = new Dictionary(keyword.getText(), meanings);
                Registry registry = LocateRegistry.getRegistry(this.IP,this.PORT);
                DictionaryServerInterface dictionaryServerInterface = (DictionaryServerInterface)registry.lookup("dict");
                Dictionary recevied = dictionaryServerInterface.addWord(dictionary);
                if (recevied.getWasSucceful()) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Smart Dectionary");
                    alert.setContentText("Word Added Sussfully!!");
                    alert.showAndWait();
                } else {
                    Falert.setContentText("Word Already Exist!!");
                    Falert.showAndWait();

                }


            } catch (Exception ex) {
                Falert.setContentText("Connection To The Server Lost");
                Falert.showAndWait();
            }
        }

    }

    @FXML
    private void deleteWord(ActionEvent event) {
        Alert Falert = new Alert(Alert.AlertType.ERROR);
        Falert.setHeaderText("Error");
        if (deleteTextBox.getText().isEmpty()) {
            Falert.setContentText("Empty Filed, Please Insert word");
            Falert.showAndWait();
        } else if (!deleteTextBox.getText().matches("[a-zA-Z]+")) {
            Falert.setContentText("Word can only be letter");
            Falert.showAndWait();
        } else {
            try {
                Dictionary dictionary = new Dictionary(deleteTextBox.getText(), null);
                Registry registry = LocateRegistry.getRegistry(this.IP,this.PORT);
                DictionaryServerInterface dictionaryServerInterface = (DictionaryServerInterface)registry.lookup("dict");
                Dictionary recevied = dictionaryServerInterface.deleteWord(dictionary);
                if (recevied.getWasSucceful()) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Smart Dectionary");
                    alert.setContentText("Word Removed Sussfully!!");
                    alert.showAndWait();
                } else {
                    Falert.setContentText("Word Does Not Exist!!");
                    Falert.showAndWait();

                }


            } catch (Exception ex) {
                Falert.setContentText("Connection To The Server Lost");
                Falert.showAndWait();
            }
        }

    }

}
