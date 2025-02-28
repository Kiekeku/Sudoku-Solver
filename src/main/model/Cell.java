package model;

// int that represents a cell in the sudoku board. 0 represnts an empty cell.
public class Cell {

    private int number;
    private int boxID;

    // EFFECTS: constructs a new cell with the given number and boxID.
    public Cell(int number, int boxID) {
        this.number = number;
        this.boxID = boxID;
    }

    // REQUIRES: 0 <= number <= 9
    // MODIFIES: this
    // EFFECTS: sets this cell's number to number
    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getBoxID() {
        return boxID;
    }
}
