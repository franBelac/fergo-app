package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.InputPopupController;
import org.example.domain.RaceEvent;
import org.example.domain.Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class FerGoApplication extends Application {
    public static Stage primaryStage;

    private static BorderPane mainLayout;

    public static RaceEvent currentEvent;

    public static List<Team> currentTeamList;

    public static int currentTeam;

    public static Path excelSavePath;

    public static Path racSavePath;

    public static Path listenerPath;

    public static Path finalResPath;

    private static AnchorPane racePane;

    public static boolean isInputLocal = false;
    public static Object lock = new Object();
    public static LinkedList<String> racePaths;


    @Override
    public void start(Stage stage) throws Exception{
        String homeDir = System.getProperty("user.home");

        Path excel = Path.of(homeDir + File.separator +".fergoConfig" + File.separator + "ExcelSavePath.txt");
        try {
            BufferedReader read1 = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(excel)));
            String excelResult = read1.readLine();
            read1.close();
            if (!excelResult.isEmpty()) excelSavePath = Path.of(excelResult);
        } catch (Exception e) {

        }

        Path rac = Path.of(homeDir + File.separator + ".fergoConfig" + File.separator + "RacSavePath.txt");

        try {
            BufferedReader read2 = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(rac)));
            String racSavePathResult = read2.readLine();
            read2.close();
            if (!racSavePathResult.isEmpty()) racSavePath = Path.of(racSavePathResult);
        } catch (Exception e) {}

        Path listener = Path.of(homeDir + File.separator + ".fergoConfig" + File.separator + "ListenerPath.txt");
        try {
            BufferedReader read3 = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(listener)));
            String listenerPathResult = read3.readLine();
            read3.close();
            if(!listenerPathResult.isEmpty()) listenerPath = Path.of(listenerPathResult);
        } catch (Exception e) {}


        Path finalRes = Path.of(homeDir + File.separator + ".fergoConfig" + File.separator + "FinalResPath.txt");

        try {
            BufferedReader read4 = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(finalRes)));
            String finalResPathResult = read4.readLine();
            read4.close();
            if(!finalResPathResult.isEmpty()) finalResPath = Path.of(finalResPathResult);
        } catch (Exception e) {}


        FXMLLoader runRaceloader = new FXMLLoader();
        runRaceloader.setLocation(getClass().getResource("/fxml/RunRace.fxml"));
        racePane = runRaceloader.load();



        primaryStage = stage;
        primaryStage.setTitle("FerGO");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/LogoNonTrans.png"));
        showMainView();
        showDefaultCenter();
    }


    private void showMainView () throws IOException {
        mainLayout = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(mainLayout);
        String CSS = this.getClass().getResource("/styles/styles.css").toExternalForm();
        scene.getStylesheets().add(CSS);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDefaultCenter() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/DefaultCenter.fxml"));
        AnchorPane defCenter = loader.load();
        mainLayout.setCenter(defCenter);
    }

    public static void showArgsInput () throws IOException {
        if (isInputLocal) InputPopupController.showPopup();
        if (isInputLocal) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/ArgsInput.fxml"));
        AnchorPane argsInp = loader.load();
        mainLayout.setCenter(argsInp);
    }

    public static void showCreateChoice () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/CreateChoice.fxml"));
        AnchorPane createChoice = loader.load();
        mainLayout.setCenter(createChoice);
    }

    public static void showSettings () throws IOException {
        if(isInputLocal) InputPopupController.showPopup();
        if (isInputLocal) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/Settings.fxml"));
        AnchorPane settingsPane = loader.load();
        mainLayout.setCenter(settingsPane);
    }

    public static void showInputLocal () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/InputLocal.fxml"));
        AnchorPane inputPane = loader.load();
        mainLayout.setCenter(inputPane);
    }

    public static void showManualEnd () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/ManualEnd.fxml"));
        AnchorPane manualEndPane = loader.load();
        mainLayout.setCenter(manualEndPane);
    }

    public static void showExcelInput () throws IOException {
        if(isInputLocal) InputPopupController.showPopup();
        if (isInputLocal) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/ExcelPane.fxml"));
        AnchorPane excelPane = loader.load();
        mainLayout.setCenter(excelPane);
    }
    public static void showRunRace () throws IOException {
        if(isInputLocal) InputPopupController.showPopup();
        if (isInputLocal) return;


        mainLayout.setCenter(racePane);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
