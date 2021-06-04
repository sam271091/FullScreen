package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class launcherController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField PathToTheFile;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnStartFullScreen;

    private String filePath;

    @FXML
    void initialize() {


        btnOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
//                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.setFileSelectionMode(JFileChooser.FILES_ONLY);

                f.setFileFilter(new FileFilter() {

                    public String getDescription() {
                        return "Text files (*.txt)";
                    }

                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            String filename = f.getName().toLowerCase();
                            return filename.endsWith(".txt") || filename.endsWith(".txt") ;
                        }
                    }
                });

                f.showSaveDialog(null);

                filePath = f.getSelectedFile().toString();

                PathToTheFile.setText(filePath);
            }
        });



        btnStartFullScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
                try {
                    Parent root = (Parent)loader.load();

                    Stage primaryStage = new Stage();

                    Controller controller = (Controller) loader.getController();
                    controller.setFilePath(filePath);


                    Scene sc = new Scene(root, 600, 85);
                    
                    primaryStage.setScene(sc);
                    primaryStage.setMaximized(true);
                    primaryStage.setFullScreen(true);
                    primaryStage.setResizable(false);

                    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.ESCAPE) {

                                event.consume(); // <-- stops passing the event to next node
                                primaryStage.close();
                            }

                        }
                    });

                    primaryStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }






//                Parent root = null;
//                try {
////                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//


            }
        });

    }
}
