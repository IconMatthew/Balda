package ru.jadae.model;

import lombok.Getter;
import ru.jadae.in.PlayerActionReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Game {
    private final List<Player> players;
    private final Field field;
    private final PlayerActionReader playerActionReader;
    private boolean breakTheGameFlow = false;

    public Game(List<Player> players, Field field, PlayerActionReader playerActionReader) {
        this.players = players;
        this.field = field;
        this.playerActionReader = playerActionReader;
    }

    public void gameCycle() {
        Player activePlayer = changePlayersStatus();

        while (!checkGameEnd()) {
            System.out.println("//---------- Current player - " + activePlayer.getPlayerName() + " ----------//\n" +
                    "Actions available:\n" +
                    "Set cell active for inserting letter (1)\n" +
                    "Enter letter into cell (2)\n" +
                    "Select cell for word formation (3)\n" +
                    "Submit finishing move (4)\n" +
                    "Cancel move (5)\n" +
                    "Skip move (6)\n" +
                    "Violate the game flow (7)");
            String action = playerActionReader.readUserAction();

            switch (action) {
                case "1" -> {
                    try {
                        System.out.println("Enter cell coords");
                        int[] params = playerActionReader.readHeightAndWidth();
                        activePlayer.setCellActiveForInsertingLetter(field.getCellByPosIndexes(params[0], params[1]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    try {
                        System.out.println("Enter letter");
                        Character letter = playerActionReader.readLetter();
                        activePlayer.enterLetterToCell(letter);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "3" -> {
                    try {
                        System.out.println("Enter cell coords");
                        int[] params = playerActionReader.readHeightAndWidth();
                        activePlayer.addCellToWord(field.getCellByPosIndexes(params[0], params[1]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "4" -> {
                    try {
                        activePlayer.submitMoveFinished();
                        activePlayer = changePlayersStatus();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "5" -> {
                    try {
                        activePlayer.cancelMove();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "6" -> {
                    try {
                        activePlayer.skipMove();
                        activePlayer = changePlayersStatus();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "7" -> {
                    try {
                        activePlayer.addWordToDictionary(playerActionReader.readUserAction());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "8" -> this.breakTheGameFlow = true;
            }
        }
    }

    private boolean checkGameEnd() {
        if (!this.field.containsEmptyCells() || this.breakTheGameFlow) {
            Map<Player, Integer> playerToScore = detectWinner();
            if (playerToScore.size() > 1) {
                System.out.println("Ничья!\n"
                        + "Очки для игрока - " + players.get(0).getPlayerName() + ": " + playerToScore.get(players.get(0)) + "\n"
                        + "Очки для игрока - " + players.get(1).getPlayerName() + ": " + playerToScore.get(players.get(1)));
            } else {
                if (!playerToScore.isEmpty()) {
                    Player winner = playerToScore.containsKey(players.get(0)) ? players.get(0) : players.get(1);
                    System.out.println("Победил " + winner.getPlayerName() + "\n" +
                            "Очки: " + playerToScore.get(winner));
                } else System.out.println("Победитель не выявлен");
            }
            return true;
        }
        return false;
    }

    private Map<Player, Integer> detectWinner() {
        Map<Player, Integer> winners = new HashMap<>();
        int maxScore = Integer.MIN_VALUE;

        for (Player player : players) {

            int score = player.getFormedWords().stream().mapToInt(String::length).sum();

            if (score > maxScore) {
                winners = new HashMap<>();
                maxScore = score;
                winners.put(player, score);
            } else if (score == maxScore) {
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
