package pl.hangman.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import pl.hangman.models.GameLogic;
import pl.hangman.utils.Dialogs;


public class MainController {

    @FXML
    private GameController gameController;
    private GameLogic gameLogic;

    @FXML
    private void initialize() {
        gameController.setMainController(this);
    }

    @FXML
    public void addWordToData(ActionEvent actionEvent) {
        Dialogs.generateDialogAndSaveData();
    }

    @FXML
    public void addWordsFromFile(ActionEvent actionEvent) {
       Dialogs.saveDataFromFile();
    }

    @FXML
    public void closeApp() {
        if (Dialogs.showConfirmationDialog().get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void showInfo() {
      Dialogs.dialogAboutApplication();
    }
}