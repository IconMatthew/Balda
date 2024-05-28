package ru.jadae.complayer;

import lombok.Getter;

import lombok.Setter;
import ru.jadae.model.*;


@Getter
public class ComputerPlayer extends Player {

    private Field field;
    private CompPlayerEntity compPlayerEntity;

    @Override
    public void setActive(boolean isActive) {
        super.setActive(isActive);
    }

    public ComputerPlayer(String playerName, WordFormer wordFormer, Field field) {
        super(playerName, wordFormer);
        this.field = field;
        compPlayerEntity = new CompPlayerEntity(this);
    }

    public void makeStep(){
        compPlayerEntity.findStepEntities();

        if (compPlayerEntity.getCellToChose() == null){
            super.skipMove();
            return;
        }

        super.setCellActiveForInsertingLetter(compPlayerEntity.getCellToChose());
        super.setFirstStepIsDone(true);
        super.enterLetterToCell(compPlayerEntity.getLetterToEnter());
        super.setSecondStepIsDone(true);
        super.addCellToWord(compPlayerEntity.getCellToChose());

        for (Cell cell: compPlayerEntity.getSequence()) {
            super.addCellToWord(cell);
        }

        super.submitMoveFinished();
    }

}
