package pl.hangman.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.hangman.models.GameLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class Dialogs {

    public static void generateDialogAndSaveData() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Dodaj");
        dialog.setHeaderText("Dodaj słowo do bazy:");
        dialog.setContentText("Wpisz słowo");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            GameLogic gameLogic = new GameLogic();
            try {
                gameLogic.addWordToDataBase(result.get());
                gameLogic.getWordsFromDataBase();
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveDataFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dokument tekstowy ", "*.txt*")
        );
        File file = fileChooser.showOpenDialog(new Stage());

           try (
                   FileReader fileReader = new FileReader(file.getAbsoluteFile());
                   BufferedReader bufferedReader = new BufferedReader(fileReader);
           ) {
               String nextLine = null;
               GameLogic gameLogic = new GameLogic();
               while ((nextLine = bufferedReader.readLine()) != null) {
                   gameLogic.addWordToDataBase(nextLine);
               }
               gameLogic.getWordsFromDataBase();
           } catch (IOException | AppException e) {
               e.printStackTrace();
           }
    }

    public static void dialogAboutApplication() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("O aplikacji");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("Aplikacja treningowa \nAutor: Karol Stachowicz");
        infoAlert.showAndWait();
    }


    public static Optional<ButtonType> showConfirmationDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Zamknij aplikacje");
        confirmationAlert.setHeaderText("Czy na pewno chcesz zamknąć aplikacje?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static Optional<ButtonType> showGameOverDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Koniec gry");
        confirmationAlert.setHeaderText("Przegrałeś, chcesz zagrać ponownie? ");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static Optional<ButtonType> wonGameDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Koniec gry");
        confirmationAlert.setHeaderText("Wygrałeś!!!! Gratulacje, chcesz zagrać ponownie? ");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

}