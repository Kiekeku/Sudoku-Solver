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
                assertEquals(b[x][y], test.getCell(x, y));
            }
        }
    }

    @Test
    void testIsSolved() {
        int[][] b = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                b[x][y] = 1;
            }
        }
        test.fill(b);
        assertFalse(test.isSolved());
        int[][] solved = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 5, 4, 2, 8, 6, 1, 7, 9 } };
        test.fill(solved);
        assertTrue(test.isSolved());
        // double in a row
        solved[0][0] = 1;
        solved[0][2] = 5;
        test.fill(solved);
        assertFalse(test.isSolved());
        solved[0][0] = 5;
        solved[0][2] = 1;
        test.fill(solved);
        // double in a column
        solved[4][4] = 3;
        solved[4][5] = 5;
        test.fill(solved);
        assertFalse(test.isSolved());
        solved[4][4] = 5;
        solved[4][5] = 3;
        test.fill(solved);
        // double in a box
        int[][] box = { { 1, 9, 8, 7, 6, 5, 4, 3, 2 },
                { 2, 1, 9, 8, 7, 6, 5, 4, 3 },
                { 3, 2, 1, 9, 8, 7, 6, 5, 4 },
                { 4, 3, 2, 1, 9, 8, 7, 6, 5 },
                { 5, 4, 3, 2, 1, 9, 8, 7, 6 },
                { 6, 5, 4, 3, 2, 1, 9, 8, 7 },
                { 7, 6, 5, 4, 3, 2, 1, 9, 8 },
                { 8, 7, 6, 5, 4, 3, 2, 1, 9 },
                { 9, 8, 7, 6, 5, 4, 3, 2, 1 } };
        test.fill(box);
        assertFalse(test.isSolved());
        test.fill(solved);
        // unsolved cell
        solved[0][0] = 0;
        test.fill(solved);
        assertFalse(test.isSolved());
        solved[0][0] = 5;
        solved[RAND.nextInt(10)][RAND.nextInt(10)] = 0;
        test.fill(solved);
        assertFalse(test.isSolved());
    }

    @Test
    void testSolve() {
        int[][] b = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                b[x][y] = 0;
            }
        }
        test.fill(b);
        assertTrue(test.solve().isSolved());
        int[][] box = { { 0, 0, 0, 4, 0, 0, 0, 7, 1 },
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
        assertTrue(test.solve().isSolved());
    }
}
