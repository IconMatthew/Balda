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
    private final int heightPos;
    private final int widthPos;
    private Character cellValue;

    public Cell(int heightPos, int widthPos) {
        this.heightPos = heightPos;
        this.widthPos = widthPos;
    }

    public void addNeighbour(Cell cell) {
        this.neighbours.add(cell);
    }

    public boolean hasFilledNeighbours() {
        for (Cell cell : neighbours) {
            if (cell.getCellValue() != null) return true;
        }
        return false;
    }

    public boolean isNeighbourToCell(Cell cell) {
        return this.neighbours.contains(cell);
    }

}
