import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.in.PlayerActionListener;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerActionListenerTests {
    private final BufferedReader bufferedReader = mock(BufferedReader.class);

    @Test
    void readTwoCharsInsteadOfIntsThrows() {

        PlayerActionListener playerActionListener = new PlayerActionListener(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("a v");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, playerActionListener::readHeightAndWidth);
    }

    @Test
    void readOneIntInsteadOfTwoIntsThrows() {

        PlayerActionListener playerActionListener = new PlayerActionListener(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, playerActionListener::readHeightAndWidth);
    }

    @Test
    void readTwoInts() {

        PlayerActionListener playerActionListener = new PlayerActionListener(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1 1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[] result = playerActionListener.readHeightAndWidth();
        Assertions.assertEquals(1, result[0]);
        Assertions.assertEquals(1, result[1]);
    }

    @Test
    void readMoreThanOneCharacterInReadLetter() {

        PlayerActionListener playerActionListener = new PlayerActionListener(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("ff");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Character character = playerActionListener.readLetter();
        });
    }

    @Test
    void readOneCharacterInReadLetter() {

        PlayerActionListener playerActionListener = new PlayerActionListener(bufferedReader);

        try {
            when(bufferedReader.readLine()).thenReturn("1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Character character = playerActionListener.readLetter();
        Assertions.assertEquals('1', character);
    }
}
