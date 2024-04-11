package ru.jadae.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cell {

    private boolean isActive = false;
    private List<Cell> neighbours = new ArrayList<>();

    private Character cellValue;

    public Cell() {
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
