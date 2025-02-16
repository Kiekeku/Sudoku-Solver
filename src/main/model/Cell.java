package model;

// int that represents a cell in the sudoku board. 0 represnts an empty cell.
public class Cell {

    private int number;
    private int boxID;

    // effects: constructs a new cell with the given number and boxID.
    public Cell(int number, int boxID) {
        this.number = number;
        this.boxID = boxID;
    }

    // requires: 0 <= number <= 9
    // modifies: this
    // effects: sets this cell's number to number
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
