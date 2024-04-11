package ru.jadae.builder;

import ru.jadae.exceptions.InitException;
import ru.jadae.model.*;
import ru.jadae.enums.Languages;
import java.util.List;

public class GameBuilder {
    private final Dictionary dictionary = new Dictionary("sampleFilePath", Languages.RUS);
    private List<Player> players;
    private Field field;
    private Game game;

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
        this.game.startGame();
    }
}
