import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jadae.builder.GameBuilder;
import ru.jadae.exceptions.InitException;
import ru.jadae.in.PlayerActionListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameBuilderTests {

    @BeforeEach
    void resetGameBuilder() throws IllegalAccessException {
        FieldUtils.writeStaticField(GameBuilder.class, "instance", null, true);
    }

    @Test
    void testCreatingField() {
        PlayerActionListener reader = mock(PlayerActionListener.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{5, 5});

        GameBuilder gameBuilder = GameBuilder.getInstance(reader);
        Assertions.assertEquals(5, gameBuilder.getField().getFieldHeight());
        Assertions.assertEquals(5, gameBuilder.getField().getFieldWidth());
    }

    @Test
    void testFieldContainsWord() {
        PlayerActionListener reader = mock(PlayerActionListener.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{5, 5});

        GameBuilder gameBuilder = GameBuilder.getInstance(reader);
        Assertions.assertNotNull(gameBuilder.getField().getCells()[2][2]);
    }

    @Test
    void testFieldNotContainsWord() {
        PlayerActionListener reader = mock(PlayerActionListener.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{6, 6});

        Assertions.assertThrows(InitException.class, () -> {
            GameBuilder.getInstance(reader);
        });
    }

    @Test
    void testFieldInvalidHeightWidth() {
        PlayerActionListener reader = mock(PlayerActionListener.class);
        when(reader.readUserAction()).thenReturn("name1").thenReturn("name2");
        when(reader.readHeightAndWidth()).thenReturn(new int[]{2, 2});

        Assertions.assertThrows(InitException.class, () -> {
            GameBuilder.getInstance(reader);
        });
    }

}
