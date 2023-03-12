package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.FerGoApplication;
import org.example.domain.RaceEvent;
import org.example.util.FERGfileUtilities;
import org.example.util.FileUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManualEndController implements Initializable {

    @FXML
    Button generateBtn;

    @FXML
    Label confirmLabel;

    @FXML
    Label warningEndLabel;

    @FXML
    public void clickGenerate () throws IOException {
        try {
            FileUtilities.generateRacFilesFromRaceEvent(FerGoApplication.currentEvent, FerGoApplication.racSavePath.toString());
            String s = FERGfileUtilities.createFERGfileFromRaceEvent(FerGoApplication.currentEvent, "event1");

            //to be deleted
            RaceEvent helpingEvent = FERGfileUtilities.createRaceEventFromFergFile(s);

            confirmLabel.setVisible(true);
            generateBtn.setDisable(true);
        } catch (NullPointerException e) {
            warningEndLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmLabel.setVisible(false);
        warningEndLabel.setVisible(false);
    }
}
