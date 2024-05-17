import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jadae.builder.GameBuilder;
import ru.jadae.enums.Languages;
import ru.jadae.exceptions.InitException;

public class GameBuilderTests {

    @BeforeEach
    void resetGameBuilder() throws IllegalAccessException {
        FieldUtils.writeStaticField(GameBuilder.class, "instance", null, true);
    }

    @Test
    void testCreatingField() {
        GameBuilder gameBuilder = GameBuilder.getInstance();
        gameBuilder.setDictionary(Languages.RUS);
        gameBuilder.setField(5);
        Assertions.assertEquals(5, gameBuilder.getField().getFieldHeight());
        Assertions.assertEquals(5, gameBuilder.getField().getFieldWidth());
    }

    @Test
    void testFieldContainsWord() {
        GameBuilder gameBuilder = GameBuilder.getInstance();
        gameBuilder.setDictionary(Languages.RUS);
        gameBuilder.setField(3);

        Assertions.assertNotNull(gameBuilder.getField().getCells()[1][0]);
        Assertions.assertNotNull(gameBuilder.getField().getCells()[1][1]);
        Assertions.assertNotNull(gameBuilder.getField().getCells()[1][2]);
    }

    @Test
    void testFieldInvalidHeightWidth() {
        GameBuilder gameBuilder = GameBuilder.getInstance();
        gameBuilder.setDictionary(Languages.RUS);

        Assertions.assertThrows(InitException.class, () -> gameBuilder.setField(11));
    }

}
