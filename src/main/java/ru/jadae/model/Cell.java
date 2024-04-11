package ru.jadae.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cell {
    private final int rowIndex;
    private final int columnIndex;
    private boolean isActive = false;
    private List<Cell> neighbours = new ArrayList<>();

    private Character cellValue;

    public Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public void addNeighbour(Cell cell) {
        this.neighbours.add(cell);
    }

    public boolean hasFilledNeighbours() {
        for (Cell cell : neighbours) {
            if (cell.getCellValue() == null) return false;
        }
        return true;
    }

}
