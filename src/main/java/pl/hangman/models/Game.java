package pl.hangman.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.hangman.database.BaseModel;

@DatabaseTable(tableName = "game")
public class Game implements BaseModel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "words")
    private String word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}