package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.FerGoApplication;
import org.example.domain.Rower;
import org.example.domain.Team;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class InputLocalController implements Initializable {

    List<TextField> fieldList;

    @FXML
    Label teamNumberLabel;

    @FXML
    TextField rowerField1;

    @FXML
    TextField rowerField2;

    @FXML
    TextField rowerField3;

    @FXML
    TextField rowerField4;

    @FXML
    TextField rowerField5;

    @FXML
    TextField rowerField6;

    @FXML
    TextField rowerField7;

    @FXML
    TextField rowerField8;

    @FXML
    TextField rowerField9;

    @FXML
    TextField rowerField10;

    @FXML
    TextField rowerField11;

    @FXML
    TextField rowerField12;

    @FXML
    TextField shortTeamField;

    @FXML
    TextField teamNameField;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        teamNumberLabel.setText(String.valueOf(FerGoApplication.currentTeam));
        fieldList = new LinkedList<>();
        fieldList.add(rowerField1);
        fieldList.add(rowerField2);
        fieldList.add(rowerField3);
        fieldList.add(rowerField4);
        fieldList.add(rowerField5);
        fieldList.add(rowerField6);
        fieldList.add(rowerField7);
        fieldList.add(rowerField8);
        fieldList.add(rowerField9);
        fieldList.add(rowerField10);
        fieldList.add(rowerField11);
        fieldList.add(rowerField12);

        for (int i = fieldList.size(); i > FerGoApplication.currentEvent.getNumberOfRowers(); i--) {
            fieldList.get(i - 1).setVisible(false);
        }
        for (int i = 0; i < FerGoApplication.currentEvent.getNumberOfRowers(); i++) {
            fieldList.get(i).setPromptText("Rower number " + (i+1)); //to set the hint text
            fieldList.get(i).getParent().requestFocus();
        }
    }
    
    @FXML
    public void clickNext() throws IOException {
        // check if false input
        if (teamNameField.getText().isEmpty()) return;
        for (int i = 0; i < FerGoApplication.currentEvent.getNumberOfRowers(); i++) {
            if (fieldList.get(i).getText().trim().isEmpty()) return;
        }


        //create new team
        Team team = new Team(teamNameField.getText());
        team.setShortName(shortTeamField.getText());
        for (var currentRower: fieldList) {
            if (currentRower.getText() == ""){break;}
            Rower rower = new Rower(currentRower.getText().trim().concat(" - ").concat(team.getShortName()));
            team.setRower(rower);
        }

        if (shortTeamField.getText().isEmpty()) {
            return;
//            team.setShortName(shortTeamField.getText().trim()); // check if necessary
        }
        team.setShortName(shortTeamField.getText().trim());

        FerGoApplication.currentEvent.addTeam(team);
        FerGoApplication.currentTeam++;

        if (FerGoApplication.currentTeam > FerGoApplication.currentEvent.getNumberOfTeams()) {
        FerGoApplication.showManualEnd();
        }
        else FerGoApplication.showInputLocal();

    }

    @FXML
    public void clickBack() throws IOException {
        if (FerGoApplication.currentTeam == 1) {
            FerGoApplication.isInputLocal = false;
            FerGoApplication.showCreateChoice();
        }
        else {
            FerGoApplication.currentTeam--;
            FerGoApplication.currentEvent.removeTeam();
            FerGoApplication.showInputLocal();
        }

    }
}
