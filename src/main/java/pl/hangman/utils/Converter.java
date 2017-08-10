package pl.hangman.utils;

import pl.hangman.models.Game;
import pl.hangman.models.GameFx;

public class Converter {

    public static Game convertGameFxToGame(GameFx gameFx) {
        Game game = new Game();
        game.setWord(gameFx.getWord());
        game.setId(gameFx.getId());
        return game;
    }
    public static GameFx convertToFx(Game game) {
        GameFx gameFx = new GameFx();
        gameFx.setId(game.getId());
        gameFx.setWord(game.getWord());
        return gameFx;
    }
}