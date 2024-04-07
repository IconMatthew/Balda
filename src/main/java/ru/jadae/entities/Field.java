package ru.jadae.entities;

import lombok.Getter;

@Getter
public class Field {
    private final int fieldHeight;
    private final int fieldWidth;
    private Cell[][] cells;

    public Field(int fieldHeight, int fieldWidth) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        fillFieldWithCells();
    }

    private void fillFieldWithCells(){

        cells = new Cell[fieldHeight][fieldHeight];

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        addNeighbourKnowledgeForAllCells();
    }

    private void addNeighbourKnowledgeForAllCells(){
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                // Cell above
                if (i > 0) cells[i][j].addNeighbour( cells[i - 1][j] );

                // Cell to the left
                if (j > 0) cells[i][j].addNeighbour( cells[i][j - 1] );

                // Cell below
                if (i < fieldHeight - 1) cells[i][j].addNeighbour( cells[i + 1][j] );

                // Cell to the right
                if (j < fieldWidth - 1) cells[i][j].addNeighbour( cells[i][j + 1] );
            }
        }
    }

    public void writeTheWordIntoMiddleRow(String word){

        if (word.length() != fieldWidth) throw new IllegalArgumentException("Invalid word length");

        char[] charsFromWord = word.toLowerCase().toCharArray();
        for (int i = 0; i < fieldWidth; i++) {
            cells[fieldHeight/2][i].setCellValue(charsFromWord[i]);
        }
    }

    public boolean containsEmptyCells(){
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (cells[i][j].getCellValue() == null) return true;
            }
        }
        return false;
    }
}
