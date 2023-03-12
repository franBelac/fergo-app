package org.example.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.example.FerGoApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField excelPathField;

    @FXML
    private TextField racPathField;

    @FXML
    private TextField listenerPathField;

    @FXML
    private TextField resultsPathField;

    private final String homeDir = System.getProperty("user.home");

    @FXML
    private void clickSetExcel () {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(FerGoApplication.primaryStage);
        excelPathField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void clickSetRac () {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(FerGoApplication.primaryStage);
        racPathField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void clickListenerPath () {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(FerGoApplication.primaryStage);
        listenerPathField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void clickResultPath () {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(FerGoApplication.primaryStage);
        resultsPathField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void clickApply () throws IOException, URISyntaxException {
        File directoryPath = new File(homeDir + File.separator + ".fergoConfig");
        System.out.println(directoryPath.getAbsolutePath());
        if (!directoryPath.exists()) {
            directoryPath.mkdir();
        }
        File excelFile = new File(homeDir + File.separator + ".fergoConfig"+ File.separator +"ExcelSavePath.txt");
        File racFile = new File(homeDir + File.separator +".fergoConfig" + File.separator + "RacSavePath.txt");
        File listenerFile = new File(homeDir + File.separator + ".fergoConfig" + File.separator + "ListenerPath.txt");
        File finalResFile = new File(homeDir + File.separator + ".fergoConfig" + File.separator + "FinalResPath.txt");
        if(!excelPathField.getText().trim().isEmpty()) {
            FileWriter writer = new FileWriter(excelFile);
            writer.write(excelPathField.getText().trim());
            writer.flush();
            writer.close();
            FerGoApplication.excelSavePath = Path.of(excelPathField.getText());
        } else {

            FileWriter writer = new FileWriter(excelFile);
            writer.write("");
            writer.flush();
            writer.close();
            FerGoApplication.excelSavePath = null;
        }

        if (!racPathField.getText().trim().isEmpty()) {
            FileWriter writer = new FileWriter(racFile);
            writer.write(racPathField.getText().trim());
            writer.flush();
            writer.close();
            FerGoApplication.racSavePath = Path.of(racPathField.getText());
        } else {
            FileWriter writer = new FileWriter(racFile);
            writer.write("");
            writer.flush();
            writer.close();
            FerGoApplication.racSavePath = null;
        }



        if(!listenerPathField.getText().trim().isEmpty()) {
            FileWriter writer = new FileWriter(listenerFile);
            writer.write(listenerPathField.getText().trim());
            writer.flush();
            writer.close();
            FerGoApplication.listenerPath = Path.of(listenerPathField.getText());
        } else {
            FileWriter writer = new FileWriter(listenerFile);
            writer.write("");
            writer.flush();
            writer.close();
            FerGoApplication.listenerPath = null;
        }

        if(!resultsPathField.getText().trim().isEmpty()) {
            FileWriter writer = new FileWriter(finalResFile);
            writer.write(resultsPathField.getText().trim());
            writer.flush();
            writer.close();
            FerGoApplication.finalResPath = Path.of(resultsPathField.getText());
        } else {
            FileWriter writer = new FileWriter(finalResFile);
            writer.write("");
            writer.flush();
            writer.close();
            FerGoApplication.finalResPath = null;
        }
    }

    @FXML
    public void showCurrentSettings () {
        if(FerGoApplication.excelSavePath != null) excelPathField.setText(FerGoApplication.excelSavePath.toString());
        if(FerGoApplication.racSavePath != null) racPathField.setText(FerGoApplication.racSavePath.toString());
        if (FerGoApplication.listenerPath != null) listenerPathField.setText(FerGoApplication.listenerPath.toString());
        if(FerGoApplication.finalResPath != null) resultsPathField.setText(FerGoApplication.finalResPath.toString());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCurrentSettings();
    }
}
