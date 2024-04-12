import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.enums.Languages;
import ru.jadae.exceptions.StepInterruptedException;
import ru.jadae.in.PlayerActionReader;
import ru.jadae.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.mock;

public class PlayerTests {

    Player init() {
        Dictionary dictionary = null;
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            String filePath = properties.getProperty("dictionary1.filepath");
            dictionary = new Dictionary(filePath, Languages.RUS);
        } catch (IOException ignored) {
        }

        return new Player("name", new WordFormer(dictionary));
    }

    @Test
    void setCellActiveForInsertingLetterTest() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        Assertions.assertFalse(cell.isActive());
        player.setCellActiveForInsertingLetter(cell);
        Assertions.assertTrue(cell.isActive());
    }

    @Test
    void enterValidLetterToCell() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        Assertions.assertNull(cell.getCellValue());
        player.setCellActiveForInsertingLetter(cell);
        player.enterLetterToCell('м');
        Assertions.assertNotNull(cell.getCellValue());
    }

    @Test
    void enterInValidLetterToCell() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        player.setCellActiveForInsertingLetter(cell);

        StepInterruptedException exception = Assertions.assertThrows(StepInterruptedException.class, () -> {
            player.enterLetterToCell('f');
        });

        Assertions.assertEquals("Incorrect letter", exception.getMessage());
    }


    @Test
    void enterLetterToUnselectedCell() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        StepInterruptedException exception = Assertions.assertThrows(StepInterruptedException.class, () -> {
            player.enterLetterToCell('f');
        });

        Assertions.assertEquals("Make sure to pick cell first!", exception.getMessage());
    }

    @Test
    void addCellToWordQueue() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        player.setCellActiveForInsertingLetter(cell);
        player.enterLetterToCell('м');
        player.addCellToWord(cell);

        Assertions.assertEquals(1, player.getWordFormer().getQueueOfCells().size());
    }

    @Test
    void submitMoveFinishedForValidWord() {
        Player player = init();
        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game(players, mock(Field.class), mock(PlayerActionReader.class));
        player.setGame(game);
        Cell cell = new Cell(0, 0);
        Cell neighbourCell = new Cell(0, 1);

        List<Cell> neighbours1 = new ArrayList<>(1);
        neighbours1.add(neighbourCell);
        List<Cell> neighbours2 = new ArrayList<>(1);
        neighbours2.add(cell);

        neighbourCell.setCellValue('ф');

        cell.setNeighbours(neighbours1);
        neighbourCell.setNeighbours(neighbours2);

        player.setCellActiveForInsertingLetter(cell);
        player.enterLetterToCell('м');
        player.addCellToWord(cell);
        player.addCellToWord(neighbourCell);

        player.submitMoveFinished();

        Assertions.assertEquals(1, game.getPlayerListMap().get(player).size());
    }

    @Test
    void submitMoveFinishedForInvalidWord() {
        Player player = init();
        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game(players, mock(Field.class), mock(PlayerActionReader.class));
        player.setGame(game);
        Cell cell = new Cell(0, 0);
        Cell neighbourCell = new Cell(0, 1);

        List<Cell> neighbours1 = new ArrayList<>(1);
        neighbours1.add(neighbourCell);
        List<Cell> neighbours2 = new ArrayList<>(1);
        neighbours2.add(cell);

        neighbourCell.setCellValue('f');

        cell.setNeighbours(neighbours1);
        neighbourCell.setNeighbours(neighbours2);

        player.setCellActiveForInsertingLetter(cell);
        player.enterLetterToCell('м');
        player.addCellToWord(cell);
        player.addCellToWord(neighbourCell);
        player.submitMoveFinished();

        Assertions.assertNull(game.getPlayerListMap().get(player));
    }

    @Test
    void cancelMoveForFirstStep() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        Assertions.assertFalse(cell.isActive());
        player.setCellActiveForInsertingLetter(cell);
        Assertions.assertTrue(cell.isActive());
        player.cancelMove();
        Assertions.assertFalse(cell.isActive());

    }

    @Test
    void cancelMoveForSecondStep() {
        Player player = init();

        Cell cell = new Cell(0, 0);
        List<Cell> neighbours = new ArrayList<>(1);
        Cell neighbourCell = new Cell(0, 1);
        neighbourCell.setCellValue('ф');
        neighbours.add(neighbourCell);
        cell.setNeighbours(neighbours);

        Assertions.assertNull(cell.getCellValue());
        player.setCellActiveForInsertingLetter(cell);
        Assertions.assertTrue(cell.isActive());
        player.enterLetterToCell('м');
        Assertions.assertNotNull(cell.getCellValue());
        player.cancelMove();
        Assertions.assertNull(cell.getCellValue());
        player.cancelMove();
        Assertions.assertFalse(cell.isActive());

    }

    @Test
    void cancelMoveForFormingWord() {
        Player player = init();
        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game(players, mock(Field.class), mock(PlayerActionReader.class));
        player.setGame(game);
        Cell cell = new Cell(0, 0);
        Cell neighbourCell = new Cell(0, 1);

        List<Cell> neighbours1 = new ArrayList<>(1);
        neighbours1.add(neighbourCell);
        List<Cell> neighbours2 = new ArrayList<>(1);
        neighbours2.add(cell);

        neighbourCell.setCellValue('f');

        cell.setNeighbours(neighbours1);
        neighbourCell.setNeighbours(neighbours2);

        player.setCellActiveForInsertingLetter(cell);
        player.enterLetterToCell('м');
        player.addCellToWord(cell);
        player.addCellToWord(neighbourCell);

        Assertions.assertFalse(player.getWordFormer().getQueueOfCells().isEmpty());

        player.cancelMove();
        player.cancelMove();

        Assertions.assertTrue(player.getWordFormer().getQueueOfCells().isEmpty());
    }

    @Test
    void skipMove() {
        Player player = init();
        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game(players, mock(Field.class), mock(PlayerActionReader.class));
        player.setGame(game);
        Cell cell = new Cell(0, 0);
        Cell neighbourCell = new Cell(0, 1);

        List<Cell> neighbours1 = new ArrayList<>(1);
        neighbours1.add(neighbourCell);
        List<Cell> neighbours2 = new ArrayList<>(1);
        neighbours2.add(cell);

        neighbourCell.setCellValue('f');

        cell.setNeighbours(neighbours1);
        neighbourCell.setNeighbours(neighbours2);

        player.skipMove();

        Assertions.assertNotNull(game.getPlayerListMap().get(player));
    }


    @Test
    void setCellActiveForInsertingLetter_WhenCellToAddLetterNotNull_ThrowsStepInterruptedException() {
        Player player = init();

        player.setCellToAddLetter(new Cell(1, 0));

        Cell cell = new Cell(0, 0);

        StepInterruptedException exception = Assertions.assertThrows(StepInterruptedException.class, () -> {
            player.setCellActiveForInsertingLetter(cell);
        });

        Assertions.assertEquals("You've already picked cell. Use cancel move option.", exception.getMessage());
    }

    @Test
    void setCellActiveForInsertingLetter_WhenCellHasNoFilledNeighbours_ThrowsStepInterruptedException() {
        Player player = init();

        Cell cell = new Cell(0, 0);

        cell.setNeighbours(List.of());

        StepInterruptedException exception = Assertions.assertThrows(StepInterruptedException.class, () -> {
            player.setCellActiveForInsertingLetter(cell);
        });

        Assertions.assertEquals("This cell has no filled neighbours! Pick another one.", exception.getMessage());
    }

    @Test
    void setCellActiveForInsertingLetter_WhenCellCellValueIsNotNull_ThrowsStepInterruptedException() {
        Player player = init();

        Cell cell = new Cell(0, 0);

        cell.setCellValue('X');

        StepInterruptedException exception = Assertions.assertThrows(StepInterruptedException.class, () -> {
            player.setCellActiveForInsertingLetter(cell);
        });

        Assertions.assertEquals("This cell has no filled neighbours! Pick another one.", exception.getMessage());
    }


}
