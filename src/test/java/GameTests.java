import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.builder.GameBuilder;
import ru.jadae.enums.Languages;
import ru.jadae.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTests {

    private final Dictionary dictionary = mock(Dictionary.class);
    private final Field field = mock(Field.class);
    private final WordFormer wordFormer = mock(WordFormer.class);

    @Test
    void enterCellCoordsMethodCallForGame() {
        when(field.containsEmptyCells()).thenReturn(true)
                .thenReturn(false);

        Cell cellToReturn = new Cell(1, 1);
        Cell neghbourCell = new Cell(1, 0);
        neghbourCell.setCellValue('a');

        cellToReturn.setNeighbours(List.of(neghbourCell));
        cellToReturn.setActive(false);

        when(field.getCellByPosIndexes(1, 1)).thenReturn(cellToReturn);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));

        Game game = new Game(players, field);
        game.step1SelectCell(game.getField().getCellByPosIndexes(1, 1));

        Assertions.assertTrue(players.get(0).isFirstStepIsDone());
    }

    @Test
    void enterCellCoordsMethodCallForGameInvalid() {
        when(field.containsEmptyCells()).thenReturn(true)
                .thenReturn(false);

        Cell cellToReturn = new Cell(1, 1);
        cellToReturn.setActive(true);

        when(field.getCellByPosIndexes(1, 1)).thenReturn(cellToReturn);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));

        Game game = new Game(players, field);
        game.step1SelectCell(game.getField().getCellByPosIndexes(0, 1));
        Assertions.assertFalse(players.get(0).isFirstStepIsDone());
    }

    @Test
    void changeActivePlayer1Time() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));

        Game game = new Game(players, field);

        game.additionalStep2SkipMove();

        Assertions.assertTrue(players.get(1).isActive());
        Assertions.assertFalse(players.get(0).isActive());
    }

    @Test
    void changeActivePlayer2TimesGameIsOver() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));

        Game game = new Game(players, field);
        game.additionalStep2SkipMove();
        game.additionalStep2SkipMove();

        Assertions.assertFalse(players.get(0).isActive());
        Assertions.assertFalse(players.get(1).isActive());
    }

    @Test
    void changeActivePlayer3Times() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        game.additionalStep2SkipMove();
        game.additionalStep2SkipMove();
        game.additionalStep2SkipMove();

        when(field.containsEmptyCells()).thenReturn(true);

        Assertions.assertFalse(players.get(0).isActive());
        Assertions.assertFalse(players.get(1).isActive());
    }

    @Test
    void skippedSecondAndThirdStepStepSubsViolated() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(game.getField().getCellByPosIndexes(0, 1));

        Assertions.assertFalse(game.step4FinishMove());
        Assertions.assertNotNull(game.getActivePlayer().getCellToAddLetter());
    }

    @Test
    void skippedThirdStepStepSubsViolated() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(game.getField().getCellByPosIndexes(0, 1));
        game.step2InsertLetter('ф');

        Assertions.assertFalse(game.step4FinishMove());
        Assertions.assertNotNull(game.getActivePlayer().getCellToAddLetter());
        Assertions.assertNotNull(game.getActivePlayer().getCellToAddLetter().getCellValue());
    }

    @Test
    void validStepSubs() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.step2InsertLetter('м');
        game.step3ChooseCell(neighbourCell);
        game.step3ChooseCell(selectedCell);

        Assertions.assertTrue(game.step4FinishMove());
        Assertions.assertFalse(game.getPlayers().get(0).getFormedWords().isEmpty());
    }

    @Test
    void gameEndsCorrectly1stWinner() {
        List<Player> players = new ArrayList<>();
        List<String> firstPlayerWords = new ArrayList<>(3);
        List<String> secondPlayerWords = new ArrayList<>(2);

        firstPlayerWords.add("word1");
        firstPlayerWords.add("word2");
        firstPlayerWords.add("word3");

        secondPlayerWords.add("word4");
        secondPlayerWords.add("word5");

        players.add(mock(Player.class));
        players.add(mock(Player.class));
        Game game = new Game(players, field);

        when(field.containsEmptyCells()).thenReturn(false);
        when(players.get(0).getFormedWords()).thenReturn(firstPlayerWords);
        when(players.get(1).getFormedWords()).thenReturn(secondPlayerWords);
        when(players.get(0).getPlayerName()).thenReturn("1st");
        when(players.get(1).getPlayerName()).thenReturn("2d");
        when(players.get(0).getWordFormer()).thenReturn(wordFormer);
        when(players.get(1).getWordFormer()).thenReturn(wordFormer);
        when(wordFormer.getDictionary()).thenReturn(dictionary);

        game.step4FinishMove();
        Assertions.assertTrue(game.isGameOver());
        Assertions.assertTrue(game.getGameResultMessage().contains("1st"));
    }

    @Test
    void gameEndsCorrectly2dWinner() {
        List<Player> players = new ArrayList<>();
        List<String> firstPlayerWords = new ArrayList<>(3);
        List<String> secondPlayerWords = new ArrayList<>(2);

        firstPlayerWords.add("word1");
        firstPlayerWords.add("word2");

        secondPlayerWords.add("word3");
        secondPlayerWords.add("word4");
        secondPlayerWords.add("word5");

        players.add(mock(Player.class));
        players.add(mock(Player.class));
        Game game = new Game(players, field);

        when(field.containsEmptyCells()).thenReturn(false);
        when(players.get(0).getFormedWords()).thenReturn(firstPlayerWords);
        when(players.get(1).getFormedWords()).thenReturn(secondPlayerWords);
        when(players.get(0).getPlayerName()).thenReturn("1st");
        when(players.get(1).getPlayerName()).thenReturn("2d");
        when(players.get(0).getWordFormer()).thenReturn(wordFormer);
        when(players.get(1).getWordFormer()).thenReturn(wordFormer);
        when(wordFormer.getDictionary()).thenReturn(dictionary);

        game.step4FinishMove();
        Assertions.assertTrue(game.isGameOver());
        Assertions.assertTrue(game.getGameResultMessage().contains("2d"));
    }

    @Test
    void gameEndsCorrectlyDraw() {
        List<Player> players = new ArrayList<>();
        List<String> firstPlayerWords = new ArrayList<>(3);
        List<String> secondPlayerWords = new ArrayList<>(2);

        firstPlayerWords.add("word1");
        firstPlayerWords.add("word2");
        firstPlayerWords.add("word3");

        secondPlayerWords.add("word4");
        secondPlayerWords.add("word5");
        secondPlayerWords.add("word6");

        players.add(mock(Player.class));
        players.add(mock(Player.class));
        Game game = new Game(players, field);

        when(field.containsEmptyCells()).thenReturn(false);
        when(players.get(0).getFormedWords()).thenReturn(firstPlayerWords);
        when(players.get(1).getFormedWords()).thenReturn(secondPlayerWords);
        when(players.get(0).getPlayerName()).thenReturn("1st");
        when(players.get(1).getPlayerName()).thenReturn("2d");

        game.step4FinishMove();
        Assertions.assertTrue(game.isGameOver());
        Assertions.assertTrue(game.getGameResultMessage().contains("2d"));
        Assertions.assertTrue(game.getGameResultMessage().contains("1st"));
    }

    @Test
    void cancelMoveForWordSubsForLength2Test() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.step2InsertLetter('м');
        game.step3ChooseCell(neighbourCell);
        game.step3ChooseCell(selectedCell);
        game.additionalStep1CancelMove();

        Assertions.assertEquals(1, game.getPlayers().get(0).getWordFormer().getQueueOfCells().size());
    }

    @Test
    void cancelMoveForWordSubsForLength1Test() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.step2InsertLetter('м');
        game.step3ChooseCell(neighbourCell);
        game.additionalStep1CancelMove();

        Assertions.assertEquals(0, game.getPlayers().get(0).getWordFormer().getQueueOfCells().size());
    }

    @Test
    void cancelMoveForFinishedMoveMakesNothing() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.step2InsertLetter('м');
        game.step3ChooseCell(neighbourCell);
        game.step3ChooseCell(selectedCell);
        game.step4FinishMove();
        game.additionalStep1CancelMove();

        Assertions.assertFalse(game.getPlayers().get(0).isFirstStepIsDone());
        Assertions.assertFalse(game.getPlayers().get(0).isSecondStepIsDone());
    }

    @Test
    void cancelMoveForInsertedLetter() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.step2InsertLetter('м');
        game.additionalStep1CancelMove();

        Assertions.assertTrue(game.getPlayers().get(0).isFirstStepIsDone());
        Assertions.assertFalse(game.getPlayers().get(0).isSecondStepIsDone());
    }

    @Test
    void cancelMoveForSelectedCell() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.additionalStep1CancelMove();

        Assertions.assertFalse(game.getPlayers().get(0).isFirstStepIsDone());
    }

    @Test
    void addValidWordToDictionary() {
        GameBuilder.getInstance().setDictionary(Languages.RUS);
        GameBuilder.getInstance().setHumanPlayers("1", "2");
        GameBuilder.getInstance().setField(3);
        Game game = GameBuilder.getInstance().initGame();

        when(field.getWord()).thenReturn("слово");

        Assertions.assertFalse(game.getActivePlayer().getWordFormer().getDictionary().containsWord("словы"));
        game.additionalStep3AddWordToDictionary("словы");
        Assertions.assertTrue(game.getActivePlayer().getWordFormer().getDictionary().containsWord("словы"));
    }

    @Test
    void addInvalidWordToDictionary() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field);

        Cell selectedCell = new Cell(0, 1);
        Cell neighbourCell = new Cell(1, 1);
        neighbourCell.setCellValue('ф');
        neighbourCell.addNeighbour(selectedCell);
        selectedCell.addNeighbour(neighbourCell);

        when(field.containsEmptyCells()).thenReturn(true);
        when(field.getCellByPosIndexes(0, 1)).thenReturn(selectedCell);
        when(field.getCellByPosIndexes(1, 1)).thenReturn(neighbourCell);
        when(dictionary.containsWord(any(String.class))).thenReturn(true);
        when(dictionary.letterIsPartOfAlphabet(any(Character.class))).thenReturn(true);

        game.step1SelectCell(selectedCell);
        game.additionalStep4FinishGame();

        Assertions.assertTrue(game.isGameOver());
    }

    @Test
    void interruptGameFlow() {
        GameBuilder.getInstance().setDictionary(Languages.RUS);
        GameBuilder.getInstance().setHumanPlayers("1", "2");
        GameBuilder.getInstance().setField(3);
        Game game = GameBuilder.getInstance().initGame();

        when(field.getWord()).thenReturn("слово");
        game.additionalStep3AddWordToDictionary("словj");

        game.additionalStep3AddWordToDictionary("словы");
        Assertions.assertFalse(game.getActivePlayer().getWordFormer().getDictionary().containsWord("словj"));
    }


}
