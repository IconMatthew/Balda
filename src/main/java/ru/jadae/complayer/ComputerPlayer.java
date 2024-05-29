package ru.jadae.complayer;

import lombok.Getter;
import ru.jadae.model.*;


@Getter
public class ComputerPlayer extends Player {

    private Field field;
    private Strategy strategy;

    @Override
    public void setActive(boolean isActive) {
        super.setActive(isActive);
    }

    public ComputerPlayer(String playerName, WordFormer wordFormer, Field field, Strategy strategy) {
        super(playerName, wordFormer);
        this.field = field;
        this.strategy = strategy;
        strategy.setComputerPlayer(this);
    }

    public void makeStep(){
        strategy.findStepEntities();

        if (strategy.getCellToChose() == null){
            super.skipMove();
            return;
        }

        super.setCellActiveForInsertingLetter(strategy.getCellToChose());
        super.setFirstStepIsDone(true);
        super.enterLetterToCell(strategy.getLetterToEnter());
        super.setSecondStepIsDone(true);
        super.addCellToWord(strategy.getCellToChose());

        for (Cell cell: strategy.getSequence()) {
            super.addCellToWord(cell);
        }

        super.submitMoveFinished();
    }

}
