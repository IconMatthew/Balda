package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Cell {
    private final int rowIndex;
    private final int columnIndex;
    private boolean isActive = false;

    public Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }


}
