package ru.jadae.complayer;

import ru.jadae.model.Cell;

import java.util.*;


public class HardComputerPlayerStrategy extends Strategy{


    // Получить все сущности, необходимые для выполнения хода
    @Override
    public void findStepEntities() {
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
