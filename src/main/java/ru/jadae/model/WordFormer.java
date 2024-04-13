package ru.jadae.model;

import lombok.Getter;
import ru.jadae.exceptions.InvalidFormedWord;
import ru.jadae.exceptions.StepInterruptedException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WordFormer {

    List<Cell> queueOfCells = new ArrayList<>();

    private final Dictionary dictionary;

    public WordFormer(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void addCellToWordQueue(Cell cell) {
        if (!cell.isActive() && cell.hasFilledNeighbours() && cell.getCellValue() != null) {
            if (!queueOfCells.isEmpty() && !queueOfCells.get(queueOfCells.size() - 1).isNeighbourToCell(cell)) {
                throw new StepInterruptedException("You cannot choose cell that is not neighbour to the previous one");
            }
            queueOfCells.add(cell);
        } else if (!cell.hasFilledNeighbours()) {
            throw new StepInterruptedException("You cannot choose cell that has no filled neighbours");
        } else if (cell.getCellValue() == null) {
            throw new StepInterruptedException("You cannot choose empty cell");
        }
    }

    public String finishWordFormation(Cell cellWithAddedLetter) {
        List<Character> wordCharList = queueOfCells.stream().map(Cell::getCellValue).toList();
        StringBuilder word = new StringBuilder();

        for (char character : wordCharList) {
            word.append(character);
        }

        if (!dictionary.containsWord(word.toString())
                || dictionary.wasFormedBefore(word.toString())
                || !queueOfCells.contains(cellWithAddedLetter)) {
            throw new InvalidFormedWord();
        }

        dictionary.addFormedWord(word.toString());

        return word.toString();
    }

    public void deleteLastSelectedCell() {
        if (!queueOfCells.isEmpty()) {
            this.queueOfCells.remove(queueOfCells.get(queueOfCells.size() - 1));
        }
    }

    public void dropSubSequenceOfSelectedCells() {
        this.queueOfCells = new ArrayList<>();
    }

}
