/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Captain
 */
public class DictionaryClient extends Application {
    public static int PORT;
    public static String IP;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = fXMLLoader.load();
        FXMLDocumentController controller = (FXMLDocumentController) fXMLLoader.getController();
        controller.setParameters(PORT,IP);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getIcons().add(
                new Image(
                        DictionaryClient.class.getResourceAsStream("app-icon.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("ERROR: Too few arguments(IP AND PORT needed)");
            Platform.exit();
            System.exit(0);
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (args[0].matches(PATTERN)) {
            System.out.println("ERROR: Invalid IP Address");
            Platform.exit();
            System.exit(0);
        }
        try {
            PORT = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("ERROR: PORT can only be integer");
            Platform.exit();
            System.exit(0);
        }
        IP = args[0];
        launch(args);
    }

}
