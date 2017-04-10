package com.cypher;

/**
 * Created by adlerd on 3/2/17.
 */
import com.cypher.encryption.CryptoImpl;
import com.cypher.encryption.KeyFile;

import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.security.Key;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;


@SuppressWarnings("Duplicates")
public class MessageEncrypt extends Application {

    @FXML
    private Button buttonDecrypt, buttonEncrypt, buttonLoadDecrypt, buttonLoadEncrypt, buttonSaveDecrypt, buttonSaveEncrypt;
    @FXML
    private TextArea inputDecrypt, inputEncrypt, outputDecrypt, outputEncrypt;
    @FXML
    private TextField saltDecrypt, saltEncrypt;
    @FXML
    private PasswordField passwordDecrypt, passwordEncrypt;

    private static KeyFile keyFile;
    private static CryptoImpl cryptoImpl;
    private String password;
    private static Key key = new SecretKeySpec("pass".getBytes(), "AES");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("MessageEncrypt.fxml"));
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Encrypt");
        primaryStage.show();
    }

    private static String stringEncrypt(String str, String password) throws Exception {
        cryptoImpl = new CryptoImpl(str);
        keyFile = new KeyFile(password, 1);
        return cryptoImpl.encryptString(keyFile);
    }

    private static String stringEncrypt(String str, String password, String salt) throws Exception {
        cryptoImpl = new CryptoImpl(str);
        keyFile = new KeyFile(password, 1);
        keyFile.setSalt(salt);
        return cryptoImpl.encryptString(keyFile);
    }

    private static String stringDecrypt(String str, String password) throws Exception {
        return cryptoImpl.decryptString(keyFile);
    }

    private static String stringDecrypt(String str, String password, String salt) throws Exception {
        return cryptoImpl.decryptString(keyFile);
    }

    public void encrypt() throws Exception {

        String password = passwordEncrypt.getText();
        String input = inputEncrypt.getText();
        String salt = saltEncrypt.getText();
        String output;

//        out.printf("[DEBUG] Input: %s%n", input);

        if (!input.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
            if (!salt.equalsIgnoreCase(""))
                output = stringEncrypt(input, password, salt);
            else
                output = stringEncrypt(input, password);
            outputEncrypt.setText(output);
            out.println(output);
        }
    }

    public void decrypt() throws Exception{

        String password = passwordDecrypt.getText();
        String input = inputDecrypt.getText();
        String salt = saltDecrypt.getText();
        String output;

//        out.printf("[DEBUG] Input: %s%n", input);

        if (!input.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
            if (!salt.equalsIgnoreCase(""))
                output = stringDecrypt(input, password, salt);
            else
                output = stringDecrypt(input, password);
            outputDecrypt.setText(output);
            out.println(output);
        }
    }


    public void encryptPickFile() throws Exception{

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            out.printf("[DEBUG] File selected: %s%n", selectedFile.getName());
        }
        else {
            out.printf("[DEBUG] No file selected%n");
        }

        //clears textbox and starts reading in text from file
        inputEncrypt.setText("");
        assert selectedFile != null;
        Scanner read = new Scanner(selectedFile);
        while (read.hasNextLine()){
            inputEncrypt.appendText(read.nextLine());
            inputEncrypt.appendText("\n");
        }
    }

//saves files

    public void encryptSaveFile() throws Exception{

        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Save File");
        File saveFile = fileChooser2.showSaveDialog(null);


        if (saveFile != null) {
            out.printf("[DEBUG] File selected for save: %s%n", saveFile.getName());
        }
        else {
            out.printf("[DEBUG] No file selected%n");
        }

        assert saveFile != null;
        FileWriter writer = new FileWriter(saveFile.getAbsolutePath());
        writer.write(outputEncrypt.getText());
        writer.close();




    }

    public void decryptPickFile() throws Exception{

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        fileChooser.getExtensionFilters().addAll();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            out.printf("[DEBUG] File selected: %s%n", selectedFile.getName());
        }
        else {
            out.printf("[DEBUG] No file selected%n");
        }
        //clears textbox and starts reading in text from file
        inputDecrypt.setText("");
        assert selectedFile != null;
        Scanner read = new Scanner(selectedFile);
        while (read.hasNextLine()){
            inputDecrypt.appendText(read.nextLine());
            inputDecrypt.appendText("\n");
        }
    }

//saves files

    public void decryptSaveFile() throws Exception{

        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Save File");
        File saveFile = fileChooser2.showSaveDialog(null);


        if (saveFile != null) {
            out.printf("[DEBUG] File selected for save: %s%n", saveFile.getName());
        }
        else {
            out.printf("[DEBUG] No file selected%n");
        }

        assert saveFile != null;
        FileWriter writer = new FileWriter(saveFile.getAbsolutePath());
        writer.write(outputDecrypt.getText());
        writer.close();




    }
}
