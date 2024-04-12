import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.model.Cell;
import ru.jadae.model.Field;

import java.util.List;

public class FieldTests {
    @Test
    void checkHeightAndWidth() {
        int initHeight = 5;
        int initWidth = 5;
        Field field = new Field(initHeight, initWidth);

        Assertions.assertEquals(initHeight, field.getFieldHeight());
        Assertions.assertEquals(initWidth, field.getFieldWidth());
    }

    @Test
    void checkNeighbourKnowledgeFor00Cell() {
        int initHeight = 6;
        int initWidth = 6;
        Field field = new Field(initHeight, initWidth);

        Cell[][] cells = field.getCells();
        Cell cell = cells[0][0];
        List<Cell> neighbours = cell.getNeighbours();

        Assertions.assertTrue(neighbours.contains(cells[0][1]));
        Assertions.assertTrue(neighbours.contains(cells[1][0]));
        Assertions.assertFalse(neighbours.contains(cells[2][1]));
        Assertions.assertFalse(neighbours.contains(cells[3][0]));
    }

    @Test
    void checkNeighbourKnowledgeFor33Cell() {
        int initHeight = 5;
        int initWidth = 5;
        Field field = new Field(initHeight, initWidth);

        Cell[][] cells = field.getCells();
        Cell cell = cells[3][3];
        List<Cell> neighbours = cell.getNeighbours();

        Assertions.assertEquals(4, neighbours.size());
        Assertions.assertTrue(neighbours.contains(cells[2][3]));
        Assertions.assertTrue(neighbours.contains(cells[3][4]));
        Assertions.assertTrue(neighbours.contains(cells[4][3]));
        Assertions.assertTrue(neighbours.contains(cells[3][2]));
    }

    @Test
    void insertWordIntoMiddleRowEvenHeight() {
        String word = "Баня";
        char[] expectedChars = word.toLowerCase().toCharArray();

        int initHeight = 4;
        int initWidth = 4;
        Field field = new Field(initHeight, initWidth);

        field.writeTheWordIntoMiddleRow(word);
        Cell[][] cells = field.getCells();

        for (int i = 0; i < initWidth; i++) {
            Assertions.assertEquals(expectedChars[i], cells[initHeight / 2][i].getCellValue());
            Assertions.assertNotEquals(expectedChars[i], cells[initHeight / 2 + 1][i].getCellValue());
            Assertions.assertNotEquals(expectedChars[i], cells[initHeight / 2 - 1][i].getCellValue());
        }
    }

    @Test
    void insertWordIntoMiddleRowOddHeight() {
        String word = "Замок";
        char[] expectedChars = word.toLowerCase().toCharArray();

        int initHeight = 5;
        int initWidth = 5;
        Field field = new Field(initHeight, initWidth);

        field.writeTheWordIntoMiddleRow(word);
        Cell[][] cells = field.getCells();

        for (int i = 0; i < initWidth; i++) {
            Assertions.assertEquals(expectedChars[i], cells[initHeight / 2][i].getCellValue());
            Assertions.assertNotEquals(expectedChars[i], cells[initHeight / 2 + 1][i].getCellValue());
            Assertions.assertNotEquals(expectedChars[i], cells[initHeight / 2 - 1][i].getCellValue());
        }
    }

    @Test
    void insertWordOfLength4IntoMiddleRowOfLength5OddHeightAnd() {
        String word = "Баня";

        int initHeight = 5;
        int initWidth = 5;
        Field field = new Field(initHeight, initWidth);

        String resultMessage = null;

        try {
            field.writeTheWordIntoMiddleRow(word);
        } catch (IllegalArgumentException e) {
            resultMessage = e.getMessage();
        }

        Assertions.assertEquals("Invalid word length", resultMessage);
    }

    @Test
    void getCellByIndexes() {
        int initHeight = 5;
        int initWidth = 5;
        int heightIndex = 2;
        int widthIndex = 3;
        String word = "слово";
        Character expectedLetter = 'в';

        Field field = new Field(initHeight, initWidth);
        field.writeTheWordIntoMiddleRow(word);
        Cell cell = field.getCellByPosIndexes(heightIndex, widthIndex);

        Assertions.assertEquals(expectedLetter, cell.getCellValue());
    }

    @Test
    void checkIfFieldHasEmptyCellsTrue() {
        int initHeight = 5;
        int initWidth = 5;
        String word = "слово";

        Field field = new Field(initHeight, initWidth);
        field.writeTheWordIntoMiddleRow(word);

        Assertions.assertTrue(field.containsEmptyCells());
    }

    @Test
    void checkIfFieldHasEmptyCellsFalse() {
        int initHeight = 5;
        int initWidth = 5;

        Field field = new Field(initHeight, initWidth);
        for (Cell[] cells1 : field.getCells()) {
            for (Cell cell : cells1) {
                cell.setCellValue('a');
            }
        }

        Assertions.assertFalse(field.containsEmptyCells());
    }

}
