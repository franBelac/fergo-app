package org.example.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.example.FerGoApplication;

import java.io.IOException;

public class MainMenuController {

    @FXML
    Button excelBtn;

    @FXML
    ImageView gear;

    @FXML
    private void clickCreateRac () throws IOException {
        FerGoApplication.showArgsInput();
    }

    @FXML
    private void clickRunRace () throws IOException {
        FerGoApplication.showRunRace();
    }

    @FXML
    private void clickGear () throws IOException {
        FerGoApplication.showSettings();
    }

    @FXML
    private void clickExcel () throws IOException {
        FerGoApplication.showExcelInput();
    }



    public void gearRotationEnter () {
        RotateTransition transition = new RotateTransition(Duration.seconds(0.5),gear);
        transition.setFromAngle(0);
        transition.setToAngle(90);
        transition.setAutoReverse(true);
        transition.play();
    }

    public void gearRotationExit () {
        RotateTransition transition = new RotateTransition(Duration.seconds(0.5),gear);
        transition.setFromAngle(90);
        transition.setToAngle(0);
        transition.setAutoReverse(true);
        transition.play();
    }



}
