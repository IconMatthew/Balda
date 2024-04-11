package ru.jadae.builder;

import ru.jadae.enums.Languages;
import ru.jadae.exceptions.InitException;
import ru.jadae.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class GameBuilder {

    private Dictionary dictionary;
    private List<Player> players;
    private Field field;
    private Game game;

    public GameBuilder() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            String filePath = properties.getProperty("dictionary1.filepath");
            String alphabetStr = properties.getProperty("dictionary1.alphabet");

            if ("RUS".equals(alphabetStr)) {
                this.dictionary = new Dictionary(filePath, Languages.RUS);
            } else if ("ENG".equals(alphabetStr)) {
                this.dictionary = new Dictionary(filePath, Languages.ENG);
            } else {
                throw new IllegalArgumentException("Unknown alphabet: " + alphabetStr);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.dictionary = null;
        }
    }

    public void setField(int fieldHeight, int fieldWidth) {
        if (field != null) throw new InitException("You've already set field parameters");
        this.field = new Field(fieldHeight, fieldWidth);

        try {
            this.field.writeTheWordIntoMiddleRow(dictionary.findValidWordForLength(fieldWidth));
        } catch (InitException e) {
            System.out.println(e.getMessage());
            this.field = null;
        }
    }

    public void setPlayer(String name) {
        if (players.size() > 2) throw new InitException("All players are already created");
        this.players.add(new Player(name, new WordFormer(this.dictionary)));
    }

    public void initGame() {
        if (this.game != null) throw new InitException("Game is already created");
        if (this.players.size() < 2) throw new InitException("Not enough players to init game");
        if (this.field == null) throw new InitException("Create field first");

        this.game = new Game(players, field);
    }
}
