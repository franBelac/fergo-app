package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.FerGoApplication;
import org.example.domain.RaceEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArgsInputController implements Initializable {
    Integer[] numOfItems = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    Integer[] numOfSplits = {1,2,4};

    @FXML
    TextField raceLengthField = new TextField();

    @FXML
    TextField eventNameField = new TextField();

    @FXML
    Label warningLabel;

    @FXML
    ChoiceBox<Integer> splitsBox;

    @FXML
    TextField teamsField = new TextField();

    @FXML
    ChoiceBox<Integer> rowersBox;

    @FXML
    ChoiceBox<Integer> ergsBox;

    @FXML
    private void confirmBtnClick() throws IOException {
        if (rowersBox.getSelectionModel().isEmpty()) return;
        if (ergsBox.getSelectionModel().isEmpty()) return;
        if (splitsBox.getSelectionModel().isEmpty()) return;
        if (eventNameField.getText().isEmpty()) return;
        if (raceLengthField.getText().isEmpty()) return;


        try {
        int rowers = rowersBox.getValue();
        int ergs = ergsBox.getValue();
        int splits = splitsBox.getValue();
        FerGoApplication.currentEvent = new RaceEvent(
                Integer.parseInt(teamsField.getText()),
                rowers,
                ergs,
                eventNameField.getText().trim(),
                Integer.parseInt(raceLengthField.getText()),splits
        );
        FerGoApplication.showCreateChoice(); } catch (NumberFormatException exception1) {
            warningLabel.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warningLabel.setVisible(false);
        rowersBox.getItems().addAll(numOfItems);
        ergsBox.getItems().addAll(numOfItems);
        splitsBox.getItems().addAll(numOfSplits);
    }
}
