import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jadae.complayer.ComputerPlayer;
import ru.jadae.complayer.Strategy;
import ru.jadae.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComputerPlayerTests {
    private ComputerPlayer computerPlayer;
    private Strategy mockStrategy;
    private Game mockGame;
    private WordFormer mockWordFormer;
    private Dictionary mockDictionary;

    @BeforeEach
    void setUp() {
        mockStrategy = mock(Strategy.class);
        mockGame = mock(Game.class);
        mockWordFormer = mock(WordFormer.class);
        mockDictionary = mock(Dictionary.class);

        // Настройка поведения мока WordFormer
        when(mockWordFormer.getDictionary()).thenReturn(mockDictionary);

        // Создаем ComputerPlayer с использованием моков
        computerPlayer = new ComputerPlayer("TestPlayer", mockWordFormer, new Field(3, 3), mockStrategy);

        // Установка активного игрока в моке Game
        when(mockGame.getActivePlayer()).thenReturn(computerPlayer);
    }

    @Test
    void testInitiateStepWithValidMove() {
        Cell mockCell = mock(Cell.class);

        // Настройка поведения мока Cell
        when(mockCell.hasFilledNeighbours()).thenReturn(true);
        when(mockCell.getCellValue()).thenReturn(null);

        // Настройка поведения мока Dictionary
        when(mockDictionary.letterIsPartOfAlphabet('а')).thenReturn(true);

        // Настройка поведения мока Strategy
        when(mockStrategy.getCellToChose()).thenReturn(mockCell);
        when(mockStrategy.getLetterToEnter()).thenReturn('а');
        when(mockStrategy.getSequence()).thenReturn(List.of(mockCell));

        // Выполнение шага компьютера
        computerPlayer.initiateStep(mockGame);

        // Проверка вызовов методов и состояния
        verify(mockStrategy).findStepEntities(computerPlayer);
        verify(mockGame).step4FinishMove();
    }

    @Test
    void testInitiateStepWithNoMove() {
        when(mockStrategy.getCellToChose()).thenReturn(null);

        computerPlayer.initiateStep(mockGame);

        verify(mockStrategy).findStepEntities(computerPlayer);
        verify(mockGame, never()).step4FinishMove();
        assertTrue(computerPlayer.getFormedWords().stream().mapToInt(String::length).sum() == 0);
    }

    @Test
    void testSkipMove() {
        computerPlayer.skipMove();
        assertTrue(computerPlayer.getFormedWords().contains(""));
    }

    @Test
    void testAddWordToDictionary() {
        Dictionary mockDictionary = mock(Dictionary.class);
        when(mockWordFormer.getDictionary()).thenReturn(mockDictionary);
        when(mockDictionary.containsWord("testword")).thenReturn(false);
        when(mockDictionary.isCorrectWord("testword")).thenReturn(true);

        computerPlayer.addWordToDictionary("testword");

        verify(mockDictionary).addWordToDictionary("testword");
    }

    @Test
    void testAddWordToDictionaryAlreadyExists() {
        Dictionary mockDictionary = mock(Dictionary.class);
        when(mockWordFormer.getDictionary()).thenReturn(mockDictionary);
        when(mockDictionary.containsWord("testword")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            computerPlayer.addWordToDictionary("testword");
        });
    }

    @Test
    void testAddWordToDictionaryInvalidWord() {
        Dictionary mockDictionary = mock(Dictionary.class);
        when(mockWordFormer.getDictionary()).thenReturn(mockDictionary);
        when(mockDictionary.isCorrectWord("testword")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            computerPlayer.addWordToDictionary("testword");
        });
    }
}
