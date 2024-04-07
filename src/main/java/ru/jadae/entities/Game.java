package ru.jadae.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Player, List<String>> playerListMap = new HashMap<>();
    private List<Player> players;

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

    }
}
