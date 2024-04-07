import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jadae.entities.Alphabet;
import ru.jadae.enums.Languages;

public class AlphabetTests {

    private static final Alphabet alphabet = new Alphabet(Languages.RUS);

    @Test
    void isValidCharTest1(){
        boolean valid = alphabet.isValidLetter('в');
        Assertions.assertTrue(valid);
    }

    @Test
    void isNotValidCharTest1(){
        boolean valid = alphabet.isValidLetter('`');
        Assertions.assertFalse(valid);
    }

    @Test
    void isValidCharTest2(){
        boolean valid = alphabet.isValidLetter('а');
        Assertions.assertTrue(valid);
    }

    @Test
    void isNotValidCharTest2(){
        boolean valid = alphabet.isValidLetter('4');
        Assertions.assertFalse(valid);
    }

    @Test
    void isValidCharTest3CapitalLetter(){
        boolean valid = alphabet.isValidLetter('А');
        Assertions.assertTrue(valid);
    }

    @Test
    void isNotValidCharTest3CapitalLetter(){
        boolean valid = alphabet.isValidLetter('F');
        Assertions.assertFalse(valid);
    }

}
