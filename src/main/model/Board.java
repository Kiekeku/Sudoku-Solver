package model;

// represents a sudoku board
public class Board {

    // creates an empty sudoku board
    public Board() {

    }

    // requires: board must be 9x9
    // modifies: this
    // effects: fills the board with the given board
    public void fill(int[][] board) {

    }

    // effects: returns true if the current board is solved.
    public boolean isSolved() {
        return true; // stub
    }

    // modifies: this
    // effects: returns solved board
    public Board solve() {
        return new Board(); // stub
    }

    public int[][] getBoard() {
        return new int[9][9]; // stub
    }

    // requires: x and y are between 0 and 8 inclusive
    // effects: returns cell at given x and y location
    public Cell getCell(int x, int y) {
        return new Cell(0, 0);
    }
}
