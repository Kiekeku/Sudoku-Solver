package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBoard {

    private static Random RAND = new Random();

    Board test;

    @BeforeEach
    void setUp() {
        test = new Board();
    }

    @Test
    void testConstructor() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                assertEquals(0, test.getCell(x, y).getNumber());
                assertEquals(0, test.getBoard()[x][y]);
            }
        }
    }

    @Test
    void testFill() {
        int[][] b = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                b[x][y] = RAND.nextInt(10);
            }
        }
        test.fill(b);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                assertEquals(b[x][y], test.getCell(x, y).getNumber());
                assertEquals(3 * (x / 3) + (y / 3) + 1, test.getCell(x, y).getBoxID());
            }
        }
    }

    @Test
    void testIsSolvedEmpty() {
        int[][] b = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                b[x][y] = 1;
            }
        }
        test.fill(b);
        assertFalse(test.isSolved());
    }

    @Test
    void testIsSolvedSolved() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        test.fill(solved);
        assertTrue(test.row());
        assertTrue(test.column());
        assertTrue(test.box());
        assertTrue(test.unsolvedCells());
        assertTrue(test.isSolved());
    }

    @Test
    void testIsSolvedRow() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        solved[0][0] = 1;
        solved[2][0] = 5;
        test.fill(solved);
        assertFalse(test.isSolved());
        assertFalse(test.row());
        assertTrue(test.column());
        assertTrue(test.box());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedColumn() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        solved[4][4] = 3;
        solved[4][5] = 5;
        test.fill(solved);
        assertFalse(test.isSolved());
        assertFalse(test.column());
        assertTrue(test.row());
        assertTrue(test.box());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedBox() {
        int[][] boxError = {
                { 1, 9, 8, 7, 6, 5, 4, 3, 2 },
                { 2, 1, 9, 8, 7, 6, 5, 4, 3 },
                { 3, 2, 1, 9, 8, 7, 6, 5, 4 },
                { 4, 3, 2, 1, 9, 8, 7, 6, 5 },
                { 5, 4, 3, 2, 1, 9, 8, 7, 6 },
                { 6, 5, 4, 3, 2, 1, 9, 8, 7 },
                { 7, 6, 5, 4, 3, 2, 1, 9, 8 },
                { 8, 7, 6, 5, 4, 3, 2, 1, 9 },
                { 9, 8, 7, 6, 5, 4, 3, 2, 1 } };
        test.fill(boxError);
        assertFalse(test.isSolved());
        assertFalse(test.box());
        assertTrue(test.column());
        assertTrue(test.row());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedUnsolved() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        solved[0][0] = 0;
        test.fill(solved);
        assertFalse(test.isSolved());
        assertFalse(test.unsolvedCells());
        assertTrue(test.column());
        assertTrue(test.row());
        assertTrue(test.box());
        solved[0][0] = 5;
        solved[RAND.nextInt(9)][RAND.nextInt(9)] = 0;
        test.fill(solved);
        assertFalse(test.isSolved());
        assertFalse(test.unsolvedCells());
        assertTrue(test.column());
        assertTrue(test.row());
        assertTrue(test.box());
    }

    @Test
    void testIsSolvedRowAndColumn() {
        int[][] boardWithRowAndColumnError = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 9, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 3 } };
        test.fill(boardWithRowAndColumnError);
        assertFalse(test.isSolved());
        assertFalse(test.row());
        assertFalse(test.column());
        assertTrue(test.box());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedRowAndBox() {
        int[][] boardWithRowAndBoxError = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 9 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 2 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 5 } };
        test.fill(boardWithRowAndBoxError);

        assertFalse(test.isSolved());
        assertFalse(test.row());
        assertTrue(test.column());
        assertFalse(test.box());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedColumnAndBox() {
        int[][] boardWithColumnAndBoxError = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 7, 2, 8, 6, 1, 5, 9 } };
        test.fill(boardWithColumnAndBoxError);

        assertFalse(test.isSolved());
        assertTrue(test.row());
        assertFalse(test.column());
        assertFalse(test.box());
        assertTrue(test.unsolvedCells());
    }

    @Test
    void testIsSolvedAllFail() {
        int[][] allFail = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 7 } };
        test.fill(allFail);
        assertFalse(test.isSolved());
        assertFalse(test.row());
        assertFalse(test.column());
        assertFalse(test.box());
    }

    @Test
    void testIsSolvedRow2() {
        int[][] board = new int[9][9];
        board[0][0] = 5;
        board[0][1] = 5;
        test.fill(board);

        // This ensures row() sees 5 once, sets 'twice=true', then sees 5 again
        assertFalse(test.row());
    }

    @Test
    void testIsSolvedColumn2() {
        int[][] board1 = new int[9][9];
        board1[0][0] = 5;
        board1[1][0] = 5;
        test.fill(board1);
        // This ensures column() sees 5 once, sets 'twice=true', then sees 5 again
        assertFalse(test.column());
    }

    @Test
    void testIsSolvedBox2() {
        int[][] board2 = new int[9][9];
        board2[0][0] = 5;
        board2[1][1] = 5;
        test.fill(board2);

        // This ensures box() sees 5 once, sets 'twice=true', then sees 5 again
        assertFalse(test.box());
    }

    @Test
    void testSolve() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        test.fill(solved);
        assertTrue(test.solve());
        int[][] box = {
                { 0, 0, 0, 4, 0, 0, 0, 7, 1 },
                { 0, 8, 0, 0, 3, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 5, 0, 0, 1, 0, 4, 0, 0, 0 },
                { 0, 0, 0, 6, 0, 0, 8, 0, 0 },
                { 0, 9, 0, 0, 0, 0, 0, 3, 0 },
                { 0, 0, 0, 0, 2, 0, 9, 0, 0 },
                { 7, 0, 4, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0 } };
        test.fill(box);
        assertFalse(test.isSolved());
        assertTrue(test.solve());
        int[][] unsolvableBoard = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 5 } };
        test.fill(unsolvableBoard);
        assertFalse(test.solve());
        int[][] partialBoard = {
                { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
                { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
                { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
                { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
                { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
                { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
                { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
                { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
                { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
        test.fill(partialBoard);
        assertTrue(test.solve());
    }

    @Test
    void testIsVald() {
        int[][] testBoard = {
                { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
                { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
                { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
                { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
                { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
                { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
                { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
                { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
                { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
        test.fill(testBoard);

        assertTrue(test.isValid(0, 2, 4));
        assertFalse(test.isValid(0, 2, 3));
        assertFalse(test.isValid(1, 4, 7));
        assertFalse(test.isValid(4, 4, 8));
    }

    @Test
    void testSetCell() {
        int[][] b = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                b[x][y] = 0;
            }
        }
        test.fill(b);
        test.setCell(0, 0, 1);
        assertEquals(1, test.getCell(0, 0).getNumber());
        assertEquals(1, test.getCell(0, 0).getBoxID());
        test.setCell(6, 4, 8);
        assertEquals(8, test.getCell(6, 4).getNumber());
        assertEquals(8, test.getCell(6, 4).getBoxID());
    }

    @Test
    void testIsSolvedRejectsOutOfRangeValues() {
        int[][] solved = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
        test.fill(solved);
        test.setCell(0, 0, 10);

        assertFalse(test.isSolved());
        assertTrue(test.row());
        assertTrue(test.column());
        assertTrue(test.box());
        assertTrue(test.unsolvedCells());
    }
}
