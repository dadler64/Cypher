package com.cypher;

/**
 * Created by adlerd on 3/2/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


@SuppressWarnings("Duplicates")
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Cypher.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cypher");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
