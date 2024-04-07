package ru.jadae.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Player, List<String>> playerListMap = new HashMap<>();
    private final List<Player> players;
    private final Field field;

    private final Dictionary dictionary;

    public Game(List<Player> players, Field field, Dictionary dictionary) {
        this.players = players;
        this.field = field;
        this.dictionary = dictionary;
    }


    public void saveWordForPlayer(String word, Player player){
        List<String> words;
        if (this.playerListMap.containsKey(player)) {
            words = new ArrayList<>();
        }
        else {
            words = playerListMap.get(player);
        }
        words.add(word);
        playerListMap.put(player, words);
    }

    private Map<Player, Integer> detectWinner(){
        Map<Player, Integer> playerToScore = new HashMap<>(2);

        for (Map.Entry<Player, List<String>> entry:playerListMap.entrySet()) {
             playerToScore.put(entry.getKey(), entry.getValue().stream().mapToInt(String::length).sum());
        }

        return playerToScore;
    }

    public void startGame(){
        try {
            String word = dictionary.findValidWordForLength(field.getFieldWidth());
            field.writeTheWordIntoMiddleRow(word);

            while (field.containsEmptyCells()){
                Player activePlayer = changePlayersStatus();

                String formedWord = activePlayer.move();
                saveWordForPlayer(formedWord, activePlayer);
            }

            detectWinner();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private Player changePlayersStatus(){
        if (!players.get(0).isActive() && !players.get(1).isActive()) {
            players.get(0).setActive(true);
            return players.get(0);
        }
        else if (players.get(0).isActive() && !players.get(1).isActive()){
            players.get(0).setActive(false);
            players.get(1).setActive(true);
            return players.get(1);
        }
        else {
            players.get(0).setActive(true);
            players.get(1).setActive(false);
            return players.get(0);
        }
    }
}
