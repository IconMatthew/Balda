import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.in.PlayerActionListener;
import ru.jadae.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTests {

    private final Dictionary dictionary = mock(Dictionary.class);
    private final Field field = mock(Field.class);
    private final PlayerActionListener playerActionListener = mock(PlayerActionListener.class);

    @Test
    void enterCellCoordsMethodCallForGame() {
        when(playerActionListener.readUserAction()).thenReturn("1");
        when(playerActionListener.readHeightAndWidth()).thenReturn(new int[]{1, 1});
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

        Game game = new Game(players, field, playerActionListener);
        game.gameCycle();
        Assertions.assertTrue(players.get(0).isFirstStepIsDone());
    }

    @Test
    void enterCellCoordsMethodCallForGameInvalid() {
        when(playerActionListener.readUserAction()).thenReturn("1");
        when(playerActionListener.readHeightAndWidth()).thenReturn(new int[]{1, 1});
        when(field.containsEmptyCells()).thenReturn(true)
                .thenReturn(false);

        Cell cellToReturn = new Cell(1, 1);
        cellToReturn.setActive(true);

        when(field.getCellByPosIndexes(1, 1)).thenReturn(cellToReturn);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));

        Game game = new Game(players, field, playerActionListener);
        game.gameCycle();
        Assertions.assertFalse(players.get(0).isFirstStepIsDone());
    }

    @Test
    void changeActivePlayer1Time() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field, playerActionListener);
        when(playerActionListener.readUserAction()).thenReturn("8");

        game.gameCycle();
        Assertions.assertTrue(players.get(0).isActive());
        Assertions.assertFalse(players.get(1).isActive());
    }

    @Test
    void changeActivePlayer2Times() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field, playerActionListener);
        when(playerActionListener.readUserAction()).thenReturn("6").thenReturn("8");
        when(field.containsEmptyCells()).thenReturn(true);

        game.gameCycle();
        Assertions.assertFalse(players.get(0).isActive());
        Assertions.assertTrue(players.get(1).isActive());
    }

    @Test
    void changeActivePlayer3Times() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("1", new WordFormer(dictionary)));
        players.add(new Player("2", new WordFormer(dictionary)));
        Game game = new Game(players, field, playerActionListener);
        when(playerActionListener.readUserAction())
                .thenReturn("6")
                .thenReturn("6")
                .thenReturn("8");
        when(field.containsEmptyCells()).thenReturn(true);

        game.gameCycle();
        Assertions.assertTrue(players.get(0).isActive());
        Assertions.assertFalse(players.get(1).isActive());
    }

}
