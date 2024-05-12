package ru.jadae.view;


import org.jetbrains.annotations.NotNull;
import ru.jadae.model.Cell;
import ru.jadae.model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class GameFieldPanel extends JPanel {
    private final GamePanel _owner;
    private CellPanel[][] _cells;

    public GameFieldPanel(@NotNull GamePanel owner) {
        _owner = owner;
        setVisible(false);
    }

//    public void initField() {
//        Field field = _owner.currentGame().getField();
//        _cells = new CellPanel[field.getFieldHeight()][field.getFieldWidth()];
//        setLayout(new GridLayout(field.getFieldHeight(), field.getFieldWidth(), 3, 3));
//
//        List<Cell> flattenedList = Arrays.stream(field.getCells())
//                .flatMap(Arrays::stream)
//                .toList();
//
//        for (Cell cell : flattenedList) {
//            var _cellPanel = new CellPanel(cell, field.getFieldHeight());
//
//            _cellPanel.addMouseListener(new MouseAdapter() {
////                public void mouseClicked(MouseEvent e) {
////                    try {
////                        // Если игрок еще не выбрал ячейку для нового символа
////                        if (_owner.currentGame().activePlayer().getSelectedCell() == null) {
////                            // Выбрать эту ячейку для указания символа
////                            _owner.currentGame().activePlayer().setSelectedCell(cell);
////                        }
////                        // Иначе если игрок выбрал новую ячейку и символ
////                        else if (_owner.currentGame().activePlayer().getSelectedSymbol() != null){
////                            // Указать ячейку в последовательность
////                            _owner.currentGame().activePlayer().addedCellInSequence(cell);
////                        }
////                    } catch (CustomGameException exception) {
////                        JOptionPane.showMessageDialog(_owner, exception.getMessage(),"Ошибка", JOptionPane.ERROR_MESSAGE);
////                    }
////                }
//            });
//
////            _cells[cell.position().row()][cell.position().column()] = _cellPanel;
////            add(_cellPanel);
//        }
//
//        setVisible(true);
//    }

    //public void update() { for (Cell cell : _owner.currentGame().field()) { updateCell(cell); } }


   // public void updateCell(Cell cell) {
   //     _cells[cell.getHeightPos()][cell.getWidthPos()].setSymbol(cell.getCellValue());

        // Если ячейка выбрана в последовательность
//        if (_owner.currentGame().activePlayer().currentSequence().contains(cell)) {
//            _cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.SELECTED_TO_CREATE_WORD);
//        }
//        // Если ячейка выбрана для указания символа
//        else if (_owner.currentGame().activePlayer().getSelectedCell() == cell) {
//            _cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.SELECTED_TO_WRITE_LETTER);
//        }
//        // Иначе
//        else {
//            _cells[cell.getHeightPos()][cell.getWidthPos()].setSelection(CellPanel.CellSelectionState.NOT_SELECTED);
//        }
    }
//}
