package ru.jadae.model;

import lombok.Getter;
import ru.jadae.complayer.ComputerPlayer;
import ru.jadae.exceptions.DuplicateWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Game {
    private final List<Player> players;
    private final Field field;
    private boolean breakTheGameFlow = false;
    private Player activePlayer;
    private boolean gameOver = false;
    private String gameResultMessage;
    private int skipCounter = 0;

    public Game(List<Player> players, Field field) {
        this.players = players;
        this.field = field;
        changePlayersStatus();
    }

    public void step1SelectCell(Cell cell) {
        try {
            System.out.println("Pick cell");
            activePlayer.setCellActiveForInsertingLetter(field.getCellByPosIndexes(cell.getHeightPos(), cell.getWidthPos()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void step2InsertLetter(Character letter) {

        if (checkGameEnd()) return;

        try {
            System.out.println("Enter letter");
            activePlayer.enterLetterToCell(letter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void step3ChooseCell(Cell cell) {

        try {
            System.out.println("Pick cell");
            activePlayer.addCellToWord(field.getCellByPosIndexes(cell.getHeightPos(), cell.getWidthPos()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean step4FinishMove() {

        try {
            activePlayer.submitMoveFinished();
            checkGameEnd();
            changePlayersStatus();
            skipCounter = 0;
            return true;
        }
        catch (DuplicateWord e) {
            System.out.println(e.getMessage());
            throw new DuplicateWord();
        } catch (Exception e) {

            if (activePlayer instanceof ComputerPlayer){
                checkGameEnd();
                changePlayersStatus();
                skipCounter = 0;
                return true;
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public void additionalStep1CancelMove() {

        try {
            activePlayer.cancelMove();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void additionalStep2SkipMove() {

        try {
            Cell cell = activePlayer.skipMove();
            this.skipCounter++;
            if (cell != null) {
                this.field.getCellByPosIndexes(cell.getHeightPos(), cell.getWidthPos()).setCellValue(null);
            }
            changePlayersStatus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (skipCounter > 1){
            additionalStep4FinishGame();
        }
    }

    public void additionalStep3AddWordToDictionary(String word) {

        try {
            activePlayer.addWordToDictionary(word);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void additionalStep4FinishGame() {
        this.breakTheGameFlow = true;
        checkGameEnd();
    }

    private boolean checkGameEnd() {
        if (!this.field.containsEmptyCells() || this.breakTheGameFlow) {
            Map<Player, Integer> playerToScore = detectWinner();
            if (playerToScore.size() > 1) {
                gameResultMessage = "Ничья!\n"
                        + "Очки для игрока - " + players.get(0).getPlayerName() + ": " + playerToScore.get(players.get(0)) + "\n"
                        + "Очки для игрока - " + players.get(1).getPlayerName() + ": " + playerToScore.get(players.get(1));
            } else {
                if (!playerToScore.isEmpty()) {
                    Player winner = playerToScore.containsKey(players.get(0)) ? players.get(0) : players.get(1);
                    gameResultMessage = "Победил " + winner.getPlayerName() + "\n" +
                            "Очки: " + playerToScore.get(winner);
                } else System.out.println("Победитель не выявлен");
                for (Player player:players) {
                    player.cleanAllFormedWords();
                    player.getWordFormer().getDictionary().cleanFormedWords();
                }
            }

            dropActivePlayers();
            this.gameOver = true;
            this.skipCounter = 0;
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

    private void changePlayersStatus() {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isActive()){
                players.get(i).setActive(false);
                if (i != players.size() - 1){
                    players.get(i+1).setActive(true);
                    activePlayer = players.get(i+1);
                    break;
                }
                else {
                    players.get(0).setActive(true);
                    activePlayer = players.get(0);
                    break;
                }
            }
        }

        if (activePlayer == null){
            players.get(0).setActive(true);
            activePlayer = players.get(0);
        }

        if (activePlayer instanceof ComputerPlayer && this.field.containsEmptyCells()){
            ((ComputerPlayer) activePlayer).makeStep();
            //step4FinishMove();
        }
    }

    private void dropActivePlayers(){
        activePlayer.setActive(false);
        changePlayersStatus();
    }

}
