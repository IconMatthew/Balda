package ru.jadae.complayer;

import ru.jadae.model.Cell;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MediumComputerPlayerStrategy extends Strategy{
    @Override
    public void findStepEntities() {
        Random random = new Random();
        if (random.nextBoolean()){
            return;
        }

        Map<String, List<Cell>> wordToUnfinishedCellSubSequence = getWordsToSequencesMap();

        Map.Entry<String, List<Cell>> entry = wordToUnfinishedCellSubSequence.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getKey().length()))
                .orElse(null);

        if (entry == null) return;

        String biggestLengthOfWordToForm = entry.getKey();
        setLetterToEnter(biggestLengthOfWordToForm.charAt(0));

        Cell neighbourCell = entry.getValue().get(0).getNeighbours().stream()
                .filter(e -> e.getCellValue() == null)
                .findFirst()
                .orElse(null);

        if (neighbourCell == null) return;

        setCellToChose(neighbourCell);
        setSequence(entry.getValue());
    }
}
