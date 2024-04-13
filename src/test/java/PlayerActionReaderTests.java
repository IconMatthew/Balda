import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.in.PlayerActionReader;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerActionReaderTests {
    private final BufferedReader bufferedReader = mock(BufferedReader.class);

    @Test
    void readTwoCharsInsteadOfIntsThrows() {

        PlayerActionReader playerActionReader = new PlayerActionReader(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("a v");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, playerActionReader::readHeightAndWidth);
    }

    @Test
    void readOneIntInsteadOfTwoIntsThrows() {

        PlayerActionReader playerActionReader = new PlayerActionReader(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, playerActionReader::readHeightAndWidth);
    }

    @Test
    void readTwoInts() {

        PlayerActionReader playerActionReader = new PlayerActionReader(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1 1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[] result = playerActionReader.readHeightAndWidth();
        Assertions.assertEquals(1, result[0]);
        Assertions.assertEquals(1, result[1]);
    }

    @Test
    void readMoreThanOneCharacterInReadLetter() {

        PlayerActionReader playerActionReader = new PlayerActionReader(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("ff");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Character character = playerActionReader.readLetter();
        });
    }

    @Test
    void readOneCharacterInReadLetter() {

        PlayerActionReader playerActionReader = new PlayerActionReader(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Character character = playerActionReader.readLetter();
        Assertions.assertEquals('1', character);
    }
}
