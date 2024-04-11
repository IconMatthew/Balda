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
        changePlayersStatus();
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

//    private void gameCycle() {
//        while (!checkGameEnd()) {
//            changePlayersStatus();
//        }
//    }

    private boolean checkGameEnd() {
        if (!this.field.containsEmptyCells()) {
            Map<Player, Integer> playerToScore = detectWinner();
            if (playerToScore.size() > 1) {
                System.out.println("Ничья!\n"
                        + "Очки для игрока - " + players.get(0).getPlayerName() + ": " + playerToScore.get(players.get(0)) + "\n"
                        + "Очки для игрока - " + players.get(1).getPlayerName() + ": " + playerToScore.get(players.get(1)));
            } else {
                Player winner = playerToScore.containsKey(players.get(0)) ? players.get(0) : players.get(1);
                System.out.println("Победил " + winner + "\n" +
                        "Очки: " + playerToScore.get(winner));
            }
            return true;
        }
        return false;
    }

    private Map<Player, Integer> detectWinner() {
        Map<Player, Integer> winners = new HashMap<>();
        int maxScore = Integer.MIN_VALUE;

        for (Map.Entry<Player, List<String>> entry : playerListMap.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue().stream().mapToInt(String::length).sum();

            if (score >= maxScore) {
                maxScore = score;
                winners.put(player, score);
            }
        }

        return winners;
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
