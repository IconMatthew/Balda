package ru.jadae.view;

import org.jetbrains.annotations.NotNull;
import ru.jadae.exceptions.StepInterruptedException;
import ru.jadae.model.Cell;
import ru.jadae.model.Field;
import ru.jadae.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class FieldPanel extends JPanel {
    private final GamePanel owner;
    private CellPanel[][] cells;

    public FieldPanel(@NotNull GamePanel owner) {
        this.owner = owner;
        setVisible(false);
    }

    public void initField() {
        Field field = owner.getGame().getField();
        cells = new CellPanel[field.getFieldHeight()][field.getFieldWidth()];
        setLayout(new GridLayout(field.getFieldHeight(), field.getFieldWidth(), 3, 3));

        List<Cell> flattenedList = Arrays.stream(field.getCells())
                .flatMap(Arrays::stream)
                .toList();

        for (Cell cell : flattenedList) {
            var _cellPanel = new CellPanel(cell, field.getFieldHeight());

            _cellPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        Game game = owner.getGame();
                        List<Cell> activeQueueOfCells = game.getActivePlayer().getWordFormer().getQueueOfCells();
                        boolean cellSelectedToInsertLetter = game.getActivePlayer().isFirstStepIsDone();
                        boolean letterIsInsertedInCell = game.getActivePlayer().isSecondStepIsDone();


                        if (activeQueueOfCells.contains(cell)) {
                            game.additionalStep1CancelMove();
                        } else if (cellSelectedToInsertLetter && !letterIsInsertedInCell &&
                                !cell.equals(game.getActivePlayer().getCellToAddLetter())) {

                            game.additionalStep1CancelMove();
                            game.step1SelectCell(cell);

                        } else if (!cellSelectedToInsertLetter) {
                            game.step1SelectCell(cell);
                        } else if (letterIsInsertedInCell) {
                            game.step3ChooseCell(cell);
                        }
                        owner.update();
                    } catch (StepInterruptedException exception) {
                        JOptionPane.showMessageDialog(owner, exception.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cells[cell.getHeightPos()][cell.getWidthPos()] = _cellPanel;
            add(_cellPanel);
        }

        setVisible(true);
    }

    public void update() {
        List<Cell> flattenedList = Arrays.stream(owner.getGame().getField().getCells())
                .flatMap(Arrays::stream)
                .toList();
        for (Cell cell : flattenedList) {
            updateCell(cell);
        }
    }


    public void updateCell(Cell cell) {
        cells[cell.getHeightPos()][cell.getWidthPos()].setSymbol(cell.getCellValue());

        if (owner.getGame().getActivePlayer().isSecondStepIsDone()
                && owner.getGame().getActivePlayer().getWordFormer().getQueueOfCells().contains(cell)) {
            cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.SELECTED_TO_CREATE_WORD);
        } else if (owner.getGame().getActivePlayer().isFirstStepIsDone()
                && cell.equals(owner.getGame().getActivePlayer().getCellToAddLetter())) {
            cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.SELECTED_TO_WRITE_LETTER);
        } else {
            cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.NOT_SELECTED);
        }
    }
}
