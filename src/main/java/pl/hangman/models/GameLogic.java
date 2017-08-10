package pl.hangman.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.hangman.database.GameDao;
import pl.hangman.utils.AppException;
import pl.hangman.utils.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameLogic  {

    private ObjectProperty<GameFx> gameFxObjectProperty = new SimpleObjectProperty<>(new GameFx());
    private ObservableList<GameFx> gameFxObservableList = FXCollections.observableArrayList();
    private   List<Game> gameList = new ArrayList<>();
    private int mistakes = 0;
    private Boolean wonTheGame = false;

    public Boolean isLost() {
       Boolean gameOver = false;
       if(mistakes==7) {
           gameOver= true;
           this.mistakes=0;
       }
       return gameOver;
    }

    public Boolean isWon(){
        Boolean finishGame = false;
        if(wonTheGame) {
            finishGame = true;
            this.mistakes=0;
        }
        return finishGame;
    }

    public void getWordsFromDataBase() throws AppException {
        GameDao gameDao = new GameDao();
        this.gameFxObservableList.clear();
        this.gameList = gameDao.queryForAll(Game.class);
        gameList.forEach(game-> {
            GameFx gameFx = Converter.convertToFx(game);
            this.gameFxObservableList.add(gameFx);
        });
    }

    public String getRandomWordFromData() {
        Random random = new Random();
        List<String> words = new ArrayList<>();
        String word;
        this.gameList.forEach(e-> words.add(e.getWord()));
        if(words.isEmpty()) {
            word= "Brak słów w bazie";
        }
        else {
            word = words.get(random.nextInt(words.size()));
        }
        return word.trim();
    }

    public String checkLetter(String letter, String currentWord, String codeWord) {
        StringBuilder tempWord = new StringBuilder();
        Boolean guessed = false;
            if (letter.toLowerCase().equals(currentWord.toLowerCase())) {
                guessed = true;
                tempWord.append(currentWord);
            }
            else {
                for (int i = 0; i < currentWord.length(); i++) {
                    if (currentWord.toLowerCase().charAt(i) == letter.toLowerCase().charAt(0) && letter.length()==1) {
                        tempWord.append(currentWord.charAt(i));
                        guessed = true;
                    } else
                        tempWord.append(codeWord.charAt(i));
                }
            }

        if(!guessed) {
            mistakes++;
        }
        if(tempWord.toString().equals(currentWord)) {
            this.wonTheGame = true;
        }
        return tempWord.toString();
    }

    public String codeRandomWord(String word){
        StringBuilder codeWord = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            if(word.charAt(i)== ' ') {
                codeWord.append(" ");
            }
            else {
                codeWord.append("-");
            }
        }
        return codeWord.toString();
    }

    public void addWordToDataBase(String word) throws AppException {
        GameDao gameDao = new GameDao();
        Game game = new Game();
        game.setWord(word);
        gameDao.createOrUpdate(game);
    }

    public String getUrlToImage(int i) {
      String [] urlToImage = {"/images/1.jpg", "/images/2.jpg", "/images/3.jpg", "/images/4.jpg", "/images/5.jpg",
                "/images/6.jpg", "/images/7.jpg", "/images/8.jpg"};
      return urlToImage[i];
    }

    public GameFx getGameFxObjectProperty() {
        return gameFxObjectProperty.get();
    }

    public ObjectProperty<GameFx> gameFxObjectPropertyProperty() {
        return gameFxObjectProperty;
    }

    public void setGameFxObjectProperty(GameFx gameFxObjectProperty) {
        this.gameFxObjectProperty.set(gameFxObjectProperty);
    }

    public ObservableList<GameFx> getGameFxObservableList() {
        return gameFxObservableList;
    }

    public void setGameFxObservableList(ObservableList<GameFx> gameFxObservableList) {
        this.gameFxObservableList = gameFxObservableList;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }
}