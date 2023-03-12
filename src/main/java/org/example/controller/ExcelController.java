package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.FerGoApplication;
import org.example.domain.RaceEvent;
import org.example.util.FERGfileUtilities;
import org.example.util.FileUtilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ExcelController implements Initializable {

    @FXML
    private Label successLabel;

    @FXML
    private TextField pathField;

    @FXML
    private Button confirmBtn;

    @FXML
    private void clickExcelFinder () {
        FileChooser chooser = new FileChooser();
        File selectedPath = chooser.showOpenDialog(FerGoApplication.primaryStage);
        pathField.setText(selectedPath.toString());
    }

    @FXML
    private void clickConfirm () throws IOException {
        RaceEvent event = FileUtilities.createRacFilesFromExcelSheet(Path.of(pathField.getText().trim()).toFile());
        FileUtilities.generateRacFilesFromRaceEvent(event,FerGoApplication.racSavePath.toString());
        FERGfileUtilities.createFERGfileFromRaceEvent(event, event.getName());
        confirmBtn.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successLabel.setVisible(false);


    }
}
