package ru.jadae.complayer;

import lombok.Getter;
import ru.jadae.model.*;


@Getter
public class ComputerPlayer extends Player implements CanInitiateStep {

    private Field field;
    private Strategy strategy;

    public ComputerPlayer(String playerName, WordFormer wordFormer, Field field, Strategy strategy) {
        super(playerName, wordFormer);
        this.field = field;
        this.strategy = strategy;
    }

    @Override
    public void initiateStep(Game game){
        strategy.findStepEntities(this);

        if (strategy.getCellToChose() == null){
            game.additionalStep2SkipMove();
            return;
        }

        super.setCellActiveForInsertingLetter(strategy.getCellToChose());
        super.enterLetterToCell(strategy.getLetterToEnter());
        super.addCellToWord(strategy.getCellToChose());

        for (Cell cell: strategy.getSequence()) {
            super.addCellToWord(cell);
        }

        game.step4FinishMove();
    }

}
