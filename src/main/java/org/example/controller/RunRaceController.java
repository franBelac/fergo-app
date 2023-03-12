package org.example.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.FerGoApplication;
import org.example.domain.TableViewElement;
import org.example.util.FolderListenerUtilities;
import org.example.util.RaceResultParserUtilities;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunRaceController implements Initializable {

    private Thread t;
    AtomicBoolean running = new AtomicBoolean(false);

    @FXML
    Button startBtn;

    @FXML
    Button finishBtn;

    @FXML
    TableColumn<TableViewElement,String> column1;

    @FXML
    TableColumn<TableViewElement,String> column2;

    @FXML
    TableView<TableViewElement> wholeView;

    private final Label placeholderLabel = new Label("Click \"Start\" to start ");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeholderLabel.setStyle("-fx-text-fill: white;");
        wholeView.setPlaceholder(placeholderLabel);
        column1.setSortable(false);
        column2.setSortable(false);
        wholeView.setEditable(false);
        column1.setReorderable(false);
        column2.setReorderable(false);
        wholeView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        finishBtn.setDisable(true);
        column1.setCellValueFactory(new PropertyValueFactory<>("raceName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("finish"));
        column1.prefWidthProperty().bind(wholeView.widthProperty().divide(2));
        column2.prefWidthProperty().bind(wholeView.widthProperty().divide(2));
    }
    @FXML
    private void clickStart () {

        FerGoApplication.racePaths = new LinkedList<>();
        if(FerGoApplication.currentTeamList != null){for(var x : FerGoApplication.currentTeamList){System.out.println(x);}}
        FerGoApplication.currentTeamList = new LinkedList<>();

        t = new Thread(){
            @Override
            public void run() {
                running.set(true);
                while(running.get()) {
                    String pathName = FolderListenerUtilities.newFileCreated(FerGoApplication.listenerPath, running);

                    Platform.runLater(()->FerGoApplication.racePaths.add(pathName));
                    if(pathName != null) {
                        Platform.runLater(() -> {
                            TableViewElement el = new TableViewElement(pathName.substring(pathName.lastIndexOf("\\") + 1));
                            el.getFinish().setOnAction(e -> {
                                wholeView.getItems().remove(el);
                                FerGoApplication.racePaths.remove(pathName);
                            });
                            wholeView.getItems().add(el);
                        })
                    ;}

                }

            }
        };

        placeholderLabel.setText("Finished races will start appearing now");
        wholeView.setPlaceholder(placeholderLabel);
        startBtn.setDisable(true);
        finishBtn.setDisable(false);
        t.setDaemon(true);
        t.start();

    }

    @FXML
    private void clickFinish () throws InterruptedException {
        running.set(false);
        //
        finishBtn.setDisable(true);
        startBtn.setDisable(false);

        for (var racePath : FerGoApplication.racePaths) {
            if(racePath == null){break;}
            List<String> stringList = Arrays.asList(racePath.split("//"));
            Collections.reverse(stringList);
            String newFileName = RaceResultParserUtilities.createFormattedFile(racePath,stringList.get(0).substring(stringList.get(0).lastIndexOf("\\")));
        }

    }

}
