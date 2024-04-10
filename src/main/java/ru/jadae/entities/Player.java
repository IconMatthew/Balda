package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;
import ru.jadae.Exceptions.InvalidFormedWord;
import ru.jadae.Exceptions.StepInterruptedException;

@Getter
@Setter
public class Player {
    private final String playerName;
    private boolean isActive = false;

    private boolean firstStepIsDone = false;
    private boolean secondStepIsDone = false;
    private boolean thirdStepIsDone = false;

    private Game game;
    private final WordFormer wordFormer;

    public Player(String playerName, WordFormer wordFormer) {
        this.playerName = playerName;
        this.wordFormer = wordFormer;
    }

    public void setCellActiveForInsertingLetter(Cell cell) {

        if (secondStepIsDone || thirdStepIsDone) throw new StepInterruptedException();

        if (!cell.isActive() && cell.hasFilledNeighbours()) {
            cell.setActive(true);
            firstStepIsDone = true;
        }

    }

    public void enterLetterToCell(Cell cell, Character letter) {
        if (!firstStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell first!");
        }
        if (secondStepIsDone || thirdStepIsDone) throw new StepInterruptedException();

        if (cell.isActive() && wordFormer.getDictionary().letterIsPartOfAlphabet(letter)) {
            cell.setCellValue(letter);
            cell.setActive(false);
            secondStepIsDone = true;
        }
    }

    public void setCellActiveForFormingWord(Cell cell) {
        if (!firstStepIsDone && !secondStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell and enter letter first!");
        }
        if (thirdStepIsDone) throw new StepInterruptedException();

        this.wordFormer.addCellLetterToWord(cell);
    }

    public void submitStepFinished() {
        if (!firstStepIsDone && !secondStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell, enter letter and form word first!");
        }
        if (thirdStepIsDone) throw new StepInterruptedException();

        try {
            String word = this.wordFormer.finishWordFormation();
            thirdStepIsDone = true;
            game.saveWordForPlayer(word, this);
        } catch (InvalidFormedWord e) {
            System.out.println(e.getMessage());
            wordFormer.dropSubSequenceOfSelectedCells();
        }
    }

}
