package ru.jadae.builder;

import lombok.Getter;
import ru.jadae.enums.Languages;
import ru.jadae.exceptions.FieldInitException;
import ru.jadae.exceptions.InitException;
import ru.jadae.exceptions.PlayerInitException;
import ru.jadae.in.PlayerActionListener;
import ru.jadae.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class GameBuilder {


    private static GameBuilder instance;

    private Dictionary dictionary;
    private final List<Player> players = new ArrayList<>(2);
    private Field field;
    private Game game;
    private final PlayerActionListener playerActionListener;
    private final List<Integer> validFieldSizes = new ArrayList<>();

     {
        validFieldSizes.add(5);
        validFieldSizes.add(6);
        validFieldSizes.add(7);
        validFieldSizes.add(8);
        validFieldSizes.add(9);
        validFieldSizes.add(10);
    }

    private GameBuilder(PlayerActionListener playerActionListener) {
        setDictionary();
        this.playerActionListener = playerActionListener;
        initGame();
    }

    public static GameBuilder getInstance(){
        if (instance == null) instance = new GameBuilder(new PlayerActionListener());
        return instance;
    }

    public static GameBuilder getInstance(PlayerActionListener playerActionListener){
        if (instance == null) instance = new GameBuilder(playerActionListener);
        return instance;
    }

    private void setDictionary() {
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
            throw new RuntimeException(e);
        }
    }


    private void setField() {
        System.out.println("Enter field height and width dividing them with space [5-10]");

        int[] params = playerActionListener.readHeightAndWidth();
        if (params[0] < 5 || params[1] < 5 || params[0] != params[1]) throw new InitException("Invalid field params");
        this.field = new Field(params[0], params[1]);
        this.field.writeTheWordIntoMiddleRow(dictionary.findValidWordForLength(params[1]));

    }

    private void setPlayers() {
        System.out.println("Enter player name");
        String firstPlayerName = playerActionListener.readUserAction();
        System.out.println("Enter player name");
        String secondPlayerName = playerActionListener.readUserAction();
        if (firstPlayerName.equals(secondPlayerName)) throw new PlayerInitException("Entered names are equal");

        this.players.add(new Player(firstPlayerName, new WordFormer(this.dictionary)));
        this.players.add(new Player(secondPlayerName, new WordFormer(this.dictionary)));
    }

    private void initGame() {

        while (this.field == null || players.size() < 2) {
            try {
                if (this.field == null) setField();
                setPlayers();
            } catch (PlayerInitException | FieldInitException e) {
                System.out.println(e.getMessage());
            }
        }

        this.game = new Game(players, field, playerActionListener);
    }

}
