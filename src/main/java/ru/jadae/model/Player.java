package ru.jadae.model;

import lombok.Getter;
import lombok.Setter;
import ru.jadae.exceptions.InvalidFormedWord;
import ru.jadae.exceptions.StepInterruptedException;

@Getter
@Setter
public class Player {
    private final String playerName;
    private boolean isActive = false;
    private Cell cellToAddLetter;

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
        if (cellToAddLetter != null)
            throw new StepInterruptedException("You've already picked cell. Use cancel move option.");

        if (!cell.isActive() && cell.hasFilledNeighbours() && cell.getCellValue() == null) {
            cell.setActive(true);
            firstStepIsDone = true;
            cellToAddLetter = cell;
        } else if (!cell.hasFilledNeighbours()) {
            throw new StepInterruptedException("This cell has no filled neighbours! Pick another one.");
        } else if (cell.getCellValue() != null) {
            throw new StepInterruptedException("This cell is not empty. Pick another one.");
        }

    }

    public void enterLetterToCell(Character letter) {
        if (!firstStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell first!");
        }
        if (secondStepIsDone || thirdStepIsDone) throw new StepInterruptedException();

        if (cellToAddLetter.isActive() && wordFormer.getDictionary().letterIsPartOfAlphabet(letter)) {
            cellToAddLetter.setCellValue(letter);
            cellToAddLetter.setActive(false);
            secondStepIsDone = true;
        } else if (!wordFormer.getDictionary().letterIsPartOfAlphabet(letter)) {
            throw new StepInterruptedException("Incorrect letter");
        }

    }

    public void addCellToWord(Cell cell) {
        if (!firstStepIsDone && !secondStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell and enter letter first!");
        }
        if (thirdStepIsDone) throw new StepInterruptedException();

        wordFormer.addCellToWordQueue(cell);
    }

    public void submitMoveFinished() {
        if (!firstStepIsDone && !secondStepIsDone) {
            throw new StepInterruptedException("Make sure to pick cell, enter letter and form word first!");
        }
        if (thirdStepIsDone) throw new StepInterruptedException();

        try {
            String word = wordFormer.finishWordFormation(cellToAddLetter);
            thirdStepIsDone = true;
            game.saveWordForPlayer(word, this);
            finalizeMove();

        } catch (InvalidFormedWord e) {
            System.out.println(e.getMessage());
        } finally {
            thirdStepIsDone = false;
        }
    }

    private void finalizeMove() {
        wordFormer.dropSubSequenceOfSelectedCells();
        cellToAddLetter.setActive(false);
        cellToAddLetter = null;

        firstStepIsDone = false;
        secondStepIsDone = false;
    }

    public void cancelMove() {
        if (secondStepIsDone && !wordFormer.getQueueOfCells().isEmpty()) {
            wordFormer.deleteLastSelectedCell();
        } else if (secondStepIsDone) {
            secondStepIsDone = false;
            this.cellToAddLetter.setCellValue(null);
        } else if (firstStepIsDone) {
            firstStepIsDone = false;
            this.cellToAddLetter.setActive(false);
            cellToAddLetter = null;
        }
    }

    public void skipMove() {
        game.saveWordForPlayer("", this);
    }
}
