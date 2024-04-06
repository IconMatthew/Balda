package ru.jadae.entities;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int fieldLendth;
    private final int fieldWidth;
    private List<Cell> cells;

    public Field(int fieldLendth, int fieldWidth) {
        this.fieldLendth = fieldLendth;
        this.fieldWidth = fieldWidth;
    }

    private void fillFieldWithCells(){
        cells = new ArrayList<>(fieldLendth*fieldWidth);

        for (int i = 0; i < fieldLendth; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                cells.add(new Cell(i, j));
            }
        }
    }
}
