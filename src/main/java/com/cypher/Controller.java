package com.cypher;

import static java.lang.System.out;

import com.cypher.encryption.CryptoImplementation;
import com.cypher.encryption.KeyFile;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

@SuppressWarnings("Duplicates")
public class Controller {

    @FXML
    private TextArea inputDecrypt, inputEncrypt, outputDecrypt, outputEncrypt;
    @FXML
    private TextField saltDecrypt, saltEncrypt;
    @FXML
    private PasswordField passwordDecrypt, passwordEncrypt;

    private static KeyFile keyFile;
    private static CryptoImplementation cryptoImplementation;

    private static String stringEncrypt(String str, String password) throws Exception {
        cryptoImplementation = new CryptoImplementation(str);
        keyFile = new KeyFile(password, 1);
        return cryptoImplementation.encryptString(keyFile);
    }

    private static String stringEncrypt(String str, String password, String salt) throws Exception {
        cryptoImplementation = new CryptoImplementation(str);
        keyFile = new KeyFile(password, 1);
        keyFile.setSalt(salt);
        return cryptoImplementation.encryptString(keyFile);
    }

    private static String stringDecrypt(String str, String password) throws Exception {
        return cryptoImplementation.decryptString(str, keyFile);
    }

    private static String stringDecrypt(String str, String password, String salt) throws Exception {
        return cryptoImplementation.decryptString(str, keyFile);
    }

    public void encrypt() throws Exception {

        String password = passwordEncrypt.getText();
        String input = inputEncrypt.getText();
        String salt = saltEncrypt.getText();
        String output;

        out.printf("[DEBUG] Encrypt input: %s%n", input);

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

        out.printf("[DEBUG] Decrypt input: %s%n", input);

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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File saveFile = fileChooser.showSaveDialog(null);


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
        // Clears textbox and starts reading in text from file
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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File saveFile = fileChooser.showSaveDialog(null);


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
