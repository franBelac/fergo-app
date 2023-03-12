package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.FerGoApplication;

import java.io.IOException;

public class InputPopupController {

private static Stage window;

    public static void showPopup() throws IOException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("FerGO");
        window.setResizable(false);
        window.getIcons().add(new Image("/images/LogoNonTrans.png"));
        window.setMinWidth(500);
        window.setMinHeight(300);

        FXMLLoader loader = new FXMLLoader();
        AnchorPane popupLayout = new AnchorPane();
        loader.setLocation(FerGoApplication.class.getResource("/fxml/InputPopup.fxml"));
        popupLayout = loader.load();
        Scene scene = new Scene(popupLayout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void clickOK () {
        FerGoApplication.isInputLocal = false;
        window.close();
    }

    public void clickCancel () {
        window.close();
    }
}
