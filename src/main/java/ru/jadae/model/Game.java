package ru.jadae.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Player, List<String>> playerListMap = new HashMap<>();
    private final List<Player> players;
    private final Field field;
    private boolean gameIsStarted = false;

    public Game(List<Player> players, Field field) {
        this.players = players;
        this.field = field;
    }

    public void saveWordForPlayer(String word, Player player) {
        List<String> words;
        if (this.playerListMap.containsKey(player)) {
            words = new ArrayList<>();
        } else {
            words = playerListMap.get(player);
        }
        words.add(word);
        playerListMap.put(player, words);

        checkGameEnd();
    }

    private void checkGameEnd() {

    }

    private Map<Player, Integer> detectWinner() {
        Map<Player, Integer> playerToScore = new HashMap<>(2);

        for (Map.Entry<Player, List<String>> entry : playerListMap.entrySet()) {
            playerToScore.put(entry.getKey(), entry.getValue().stream().mapToInt(String::length).sum());
        }

        return playerToScore;
    }

    public void startGame() {
        if (gameIsStarted) return;

        changePlayersStatus();

        //TODO:тело метода

        gameIsStarted = true;
    }

    private Player changePlayersStatus() {
        if (!players.get(0).isActive() && !players.get(1).isActive()) {
            players.get(0).setActive(true);
            return players.get(0);
        } else if (players.get(0).isActive() && !players.get(1).isActive()) {
            players.get(0).setActive(false);
            players.get(1).setActive(true);
            return players.get(1);
        } else {
            players.get(0).setActive(true);
            players.get(1).setActive(false);
            return players.get(0);
        }
    }
}
