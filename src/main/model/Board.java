package model;

import org.json.JSONArray;
import org.json.JSONObject;

// represents a sudoku board where 0 represents an unsolved cell
public class Board {

    private Cell[][] board;

    // EFFECTS: creates an empty sudoku board
    public Board() {
        board = new Cell[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                board[x][y] = new Cell(0, 3 * (x / 3) + (y / 3) + 1);
            }
        }
    }

    // REQUIRES: board must be 9x9
    // MODIFIES: this
    // EFECTS: fills the board with the given board
    public void fill(int[][] board) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                this.board[x][y] = new Cell(board[x][y], 3 * (x / 3) + (y / 3) + 1);
            }
        }
    }

    // EFFECTS: returns true if the current board is solved.
    public boolean isSolved() {
        return (row() && column() && box() && unsolvedCells()); // stub
    }

    // EFFECTS: returns true if there are no duplicates in any row in the current
    // board
    public boolean row() {
        boolean twice;
        for (int x = 0; x < 9; x++) {
            for (int n = 1; n < 10; n++) {
                twice = false;
                for (int y = 0; y < 9; y++) {
                    if (board[x][y].getNumber() == n && !twice) {
                        twice = true;
                    } else if (board[x][y].getNumber() == n) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // EFFECTS: returns true if there are no duplicates in any column in the given
    // board
    public boolean column() {
        boolean twice;
        for (int x = 0; x < 9; x++) {
            for (int n = 1; n < 10; n++) {
                twice = false;
                for (int y = 0; y < 9; y++) {
                    if (board[y][x].getNumber() == n && !twice) {
                        twice = true;
                    } else if (board[y][x].getNumber() == n) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // EFFECTS: returns true if there are no duplicates in any box in the given
    // board
    public boolean box() {
        boolean twice;
        for (int box = 1; box < 10; box++) {
            for (int n = 1; n < 10; n++) {
                twice = false;
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (board[x][y].getNumber() == n && board[x][y].getBoxID() == box && !twice) {
                            twice = true;
                        } else if (board[x][y].getNumber() == n && board[x][y].getBoxID() == box) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // EFFECTS: returns true if there are no unsolved cells in the current board
    public boolean unsolvedCells() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board[x][y].getNumber() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: returns true if board is solvable. False if not solvable. Changes
    // the current board to the solution
    public boolean solve() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board[x][y].getNumber() == 0) { // Find the first empty cell
                    for (int n = 1; n <= 9; n++) {
                        if (isValid(x, y, n)) { // Check if valid
                            setCell(x, y, n);

                            if (solve()) {
                                return true;
                            }

                            setCell(x, y, 0); // Reset if it fails
                        }
                    }
                    return false;
                }
            }
        }
        if (isSolved()) {
            EventLog.getInstance().logEvent(new Event("Board solved."));
        }
        return isSolved(); // No empty cells left, solution found
    }

    // REQUIRES: 0 < n < 10 and 0 < x, y < 9
    // EFFECTS: returns true if setting the cell at (x, y) to n is validF
    public boolean isValid(int x, int y, int n) {
        int temp = board[x][y].getNumber();
        setCell(x, y, n);
        boolean result = (row() && column() && box());
        setCell(x, y, temp);
        return result;
    }

    // EFFECTS: returns the board as a 2d array of int
    public int[][] getBoard() {
        int[][] answer = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                answer[x][y] = board[x][y].getNumber();
            }
        }
        return answer;
    }

    // EFFECTS: returns the board as a JsonArray
    public JSONObject toJson() {
        JSONObject boardJson = new JSONObject();

        // Convert the 9x9 grid into JSON
        JSONArray rowsArray = new JSONArray();
        int[][] grid = getBoard();
        for (int i = 0; i < 9; i++) {
            JSONArray rowArray = new JSONArray();
            for (int j = 0; j < 9; j++) {
                rowArray.put(grid[i][j]);
            }
            rowsArray.put(rowArray);
        }

        boardJson.put("grid", rowsArray);
        return boardJson;
    }

    // REQUIRES: x and y are between 0 and 8 inclusive
    // EFFECTS: returns cell at given x and y location
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    // REQUIRES: x and y are between 0 and 8 inclusive and n is between 0 and 9
    // inclusive
    // EFFECTS: returns cell at given x and y location
    public void setCell(int x, int y, int n) {
        board[x][y] = new Cell(n, 3 * (x / 3) + (y / 3) + 1);
    }
}
