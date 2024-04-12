import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.builder.GameBuilder;
import ru.jadae.exceptions.InitException;
import ru.jadae.in.PlayerActionReader;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameBuilderTests {
    @Test
    void testCreatingField() {
        PlayerActionReader reader = mock(PlayerActionReader.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{5, 5});

        GameBuilder gameBuilder = new GameBuilder(reader);
        Assertions.assertEquals(5, gameBuilder.getField().getFieldHeight());
        Assertions.assertEquals(5, gameBuilder.getField().getFieldWidth());
    }

    @Test
    void testFieldContainsWord() {
        PlayerActionReader reader = mock(PlayerActionReader.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{5, 5});

        GameBuilder gameBuilder = new GameBuilder(reader);
        Assertions.assertNotNull(gameBuilder.getField().getCells()[2][2]);
    }

    @Test
    void testFieldNotContainsWord() {
        PlayerActionReader reader = mock(PlayerActionReader.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{6, 6});

        Assertions.assertThrows(InitException.class, () -> {
            new GameBuilder(reader);
        });
    }

    @Test
    void testFieldInvalidHeightWidth() {
        PlayerActionReader reader = mock(PlayerActionReader.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{2, 2});

        Assertions.assertThrows(InitException.class, () -> {
            new GameBuilder(reader);
        });
    }


}
