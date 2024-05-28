package ru.jadae.builder;

import lombok.Getter;
import ru.jadae.complayer.ComputerPlayer;
import ru.jadae.enums.Languages;
import ru.jadae.exceptions.InitException;
import ru.jadae.exceptions.PlayerInitException;
import ru.jadae.model.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameBuilder {

    private static GameBuilder instance;

    private Dictionary dictionary;
    private List<Player> players;
    private Field field;

    private final List<Integer> validFieldSizes = new ArrayList<>(6);
    private final List<Languages> validLanguages = new ArrayList<>(2);

    private void initializeValidFieldSizes() {
        for (int i = 3; i < 9; i += 2) {
            validFieldSizes.add(i);
        }
    }

    private void initializeValidLanguages() {
        validLanguages.add(Languages.RUS);
        validLanguages.add(Languages.ENG);
    }

    private GameBuilder() {
        initializeValidLanguages();
        initializeValidFieldSizes();
    }

    public static GameBuilder getInstance() {
        if (instance == null) instance = new GameBuilder();
        return instance;
    }

    public void setDictionary(Languages language) {
        
        if (language.equals(Languages.RUS)) {
            this.dictionary = new Dictionary("dictionary1.txt", Languages.RUS);
        } else {
            this.dictionary = new Dictionary("dictionary2.txt", Languages.ENG);
        }
    }

    public void setField(int dimension) {
        if (dimension > 9 || dimension < 3 || dimension % 2 == 0) {
            throw new InitException("Invalid dimension size");
        }
        this.field = new Field(dimension, dimension);
        this.field.writeTheWordIntoMiddleRow(dictionary.findValidWordForLength(dimension));
        this.dictionary.addFormedWord(field.getWord());
    }

    public void setHumanPlayers(String name1, String name2) {
        players = new ArrayList<>(2);
        if (name1.equals(name2)) throw new PlayerInitException("Entered names are equal");
        this.players.add(new Player(name1, new WordFormer(this.dictionary)));
        this.players.add(new Player(name2, new WordFormer(this.dictionary)));
    }
    public void setHumanAndComputerPlayers(String name1) {
        players = new ArrayList<>(2);
        if (name1.equals("Компьютер")) throw new PlayerInitException("Entered names are equal");
        this.players.add(new Player(name1, new WordFormer(this.dictionary)));
        this.players.add(new ComputerPlayer("Компьютер", new WordFormer(this.dictionary), this.field));
    }

    public Game initGame() {
        if (field == null || players.isEmpty()) throw new InitException("Not all parameters are set");
        return new Game(players, field);
    }

}
