package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Word {
    private String word;
    private boolean isFinished = false;
    private final Dictionary dictionary;
    private final Alphabet alphabet;

    public Word(Dictionary dictionary, Alphabet alphabet) {
        this.dictionary = dictionary;
        this.alphabet = alphabet;
    }

    public void addCellLetterToWord(Cell cell){
        if (cell.hasFilledNeighbours() && alphabet.isValidLetter(cell.getCellValue())){
            word += cell.getCellValue();
        }
    }

}
