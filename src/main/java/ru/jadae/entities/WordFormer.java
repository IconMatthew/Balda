package ru.jadae.entities;

import lombok.Getter;
import ru.jadae.Exceptions.InvalidFormedWord;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WordFormer {

    List<Cell> queueOfCells = new ArrayList<>();

    private final Dictionary dictionary;

    public WordFormer(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void addCellLetterToWord(Cell cell) {
        if (cell.isActive() && cell.hasFilledNeighbours() && cell.getCellValue() != null) {
            queueOfCells.add(cell);
        }
    }

    public String finishWordFormation() {
        List<Character> wordCharList = queueOfCells.stream().map(Cell::getCellValue).toList();
        StringBuilder word = new StringBuilder();

        for (char character : wordCharList) {
            word.append(character);
        }

        if (!dictionary.containsWord(word.toString()) || dictionary.wasFormedBefore(word.toString())) {
            throw new InvalidFormedWord();
        }

        dictionary.addFormedWord(word.toString());

        return word.toString();
    }

    public void dropSubSequenceOfSelectedCells() {
        this.queueOfCells = new ArrayList<>();
    }

}
