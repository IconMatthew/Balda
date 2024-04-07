package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Cell {
    private final int rowIndex;
    private final int columnIndex;
    private boolean isActive = false;
    List<Cell> neighbours = new ArrayList<>();

    private Character cellValue;

    public Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public void addNeighbour(Cell cell){
        this.neighbours.add(cell);
    }

    public boolean hasFilledNeighbours(){
        for (Cell cell:neighbours) {
            if (cell.getCellValue() == null) return false;
        }
        return true;
    }

}
