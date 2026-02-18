package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import persistence.JsonReader;

public class TestJsonReader {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/test/noSuchFile.json");
        try {
            ArrayList<SavedBoard> savedBoards = reader.read();
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmpty() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
            ArrayList<SavedBoard> savedBoards = reader.read();
            assertTrue(savedBoards.isEmpty());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReaderGeneral() {
        try {
            JsonReader reader = new JsonReader("./data/testReader.json");
            ArrayList<SavedBoard> savedBoards = reader.read();

            assertEquals(2, savedBoards.size());

            SavedBoard sb1 = savedBoards.get(0);
            assertEquals("MyTestBoard1", sb1.getName());
            assertEquals("This is a test board.", sb1.getNote());

            Board unsolved = sb1.getUnsolved();
            assertFalse(unsolved.isSolved());

            Board solved = sb1.getSolved();
            int[][] solvedGrid = solved.getBoard();
            assertTrue(solved.isSolved());
            unsolved.solve();
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    assertEquals(solvedGrid[x][y], unsolved.getBoard()[x][y]);
                }
            }

            SavedBoard sb2 = savedBoards.get(1);
            assertEquals("MyTestBoard2", sb2.getName());
            assertEquals("Another puzzle", sb2.getNote());
            unsolved = sb2.getUnsolved();
            assertFalse(unsolved.isSolved());

            solved = sb2.getSolved();
            solvedGrid = solved.getBoard();
            assertTrue(solved.isSolved());
            unsolved.solve();
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    assertEquals(solvedGrid[x][y], unsolved.getCell(x, y).getNumber());
                }
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReaderMissingTextFields() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderMissingText.json");
            ArrayList<SavedBoard> savedBoards = reader.read();
            assertEquals(1, savedBoards.size());
            assertEquals("", savedBoards.get(0).getName());
            assertEquals("", savedBoards.get(0).getNote());
        } catch (IOException e) {
            fail();
        }
    }
}
