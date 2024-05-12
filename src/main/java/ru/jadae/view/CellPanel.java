package ru.jadae.view;

import ru.jadae.model.Cell;
import ru.jadae.view.utils.AppStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class CellPanel extends JButton {
    public enum CellSelectionState { NOT_SELECTED, SELECTED_TO_WRITE_LETTER, SELECTED_TO_CREATE_WORD }

    private final Cell _cell;

    public CellPanel(Cell cell, int fieldSize) {
        _cell = cell;
        int fontSize;
        switch (fieldSize) {
            case 5 -> fontSize = 44;
            case 7 -> fontSize = 20;
            default -> fontSize = 8;
        }
        setFont(new Font("Century Gothic", Font.PLAIN, fontSize));
        if (_cell.getCellValue() != null) setSymbol(_cell.getCellValue());
        //setSelection(CellSelectionState.NOT_SELECTED);
        setFocusable(false);
    }

    public void setSelection(CellSelectionState state) {
        switch (state) {
            case NOT_SELECTED -> setBackground(AppStyles.PRIMARY_COLOR);
            case SELECTED_TO_WRITE_LETTER -> setBackground(AppStyles.SELECTED_TO_WRITE_LETTER_COLOR);
            case SELECTED_TO_CREATE_WORD -> setBackground(AppStyles.SELECTED_TO_CREATE_WORD_COLOR);
        }
    }

    public void setSymbol(Character symbol) { setText(symbol == null? "" : symbol.toString().toUpperCase(Locale.ROOT)); }
}
