package pl.hangman.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.hangman.models.GameLogic;
import pl.hangman.utils.AppException;
import pl.hangman.utils.Dialogs;

public class GameController {

    @FXML
    private Label label;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField inTextField;
    @FXML
    private Button checkButton;
    @FXML
    private Button resetButton;

    private MainController mainController;
    private GameLogic gameLogic;
    private String randomWord;
    private String codeWord;

    @FXML
    public void initialize() {

        gameLogic = new GameLogic();
        try {
            gameLogic.getWordsFromDataBase();
        } catch (AppException e) {
            e.printStackTrace();
        }
        this.gameLogic.setMistakes(0);
        this.imageView.setImage(new Image(this.gameLogic.getUrlToImage(this.gameLogic.getMistakes())));
        randomWord = this.gameLogic.getRandomWordFromData();
        codeWord = this.gameLogic.codeRandomWord(randomWord);
        label.setText(codeWord);
        this.checkButton.disableProperty().bind(this.inTextField.textProperty().isEmpty());
    }

    @FXML
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void checkLetter() {

        this.label.setText(this.gameLogic.checkLetter(this.inTextField.textProperty().get(),this.randomWord, this.codeWord));
        codeWord = this.label.getText();
        this.imageView.setImage(new Image(this.gameLogic.getUrlToImage(this.gameLogic.getMistakes())));
        this.inTextField.clear();

        getResultOfTheGame();

    }

    @FXML
    private void getResultOfTheGame() {
        if(this.gameLogic.isWon()) {
            if(Dialogs.wonGameDialog().get() == ButtonType.OK) {
                this.initialize();
            }
            else {
                setVisibility(false);
            }
        }
        if(this.gameLogic.isLost()) {
            if(Dialogs.showGameOverDialog().get() == ButtonType.OK) {
                this.initialize();
            }
            else {
                setVisibility(false);
                this.label.setText(this.randomWord);
            }
        }
    }

    @FXML
    private void setVisibility(Boolean result) {
        this.inTextField.setVisible(result);
        this.checkButton.setVisible(result);
    }

    @FXML
    public void refreshGame() {
        this.initialize();
        setVisibility(true);
    }
}