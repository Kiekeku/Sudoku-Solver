package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;
import persistence.JsonWriter;

public class TestJsonWriter {
    private ArrayList<SavedBoard> testBoards;

    @BeforeEach
    void setup() {
        testBoards = new ArrayList<>();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\0illegalFileName.json");
            writer.open();
        } catch (IOException e) {
        }
    }

    @Test
    void testWriterEmptyList() {
        // Save an empty list of boards
        String testFile = "./data/testWriterEmptyList.json";
        JsonWriter writer = new JsonWriter(testFile);

        try {
            writer.open();
            writer.write(testBoards); // testBoards is empty at this point
            writer.close();
        } catch (IOException e) {
            fail();
        }
        try {
            JsonReader reader = new JsonReader(testFile);
            ArrayList<SavedBoard> readBoards = reader.read();
            assertTrue(readBoards.isEmpty());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriterGeneralList() {
        try {
            Board unsolved1 = new Board();
            int[][] grid1 = new int[9][9];
            grid1[0][0] = 5;
            grid1[1][1] = 7;
            unsolved1.fill(grid1);

            Board solved1 = new Board();
            int[][] solvedGrid1 = new int[9][9];
            solvedGrid1[0][0] = 9;
            solvedGrid1[0][1] = 8;
            solved1.fill(solvedGrid1);

            SavedBoard sb1 = new SavedBoard("Board #1", "Note #1", unsolved1, solved1);

            Board unsolved2 = new Board();
            int[][] grid2 = new int[9][9];
            grid2[2][2] = 3;
            unsolved2.fill(grid2);

            Board solved2 = new Board();
            int[][] solvedGrid2 = new int[9][9];
            solvedGrid2[0][0] = 1;
            solvedGrid2[8][8] = 9;
            solved2.fill(solvedGrid2);

            SavedBoard sb2 = new SavedBoard("Board #2", "Note #2", unsolved2, solved2);

            testBoards.add(sb1);
            testBoards.add(sb2);

            // Write to a file
            String testFile = "./data/testWriter.json";
            JsonWriter writer = new JsonWriter(testFile);

            try {
                writer.open();
                writer.write(testBoards);
                writer.close();
            } catch (IOException e) {
                fail();
            }

            // read back and compare
            JsonReader reader = new JsonReader(testFile);
            ArrayList<SavedBoard> readBoards = reader.read();

            assertEquals(2, readBoards.size());

            SavedBoard readSb1 = readBoards.get(0);
            assertEquals("Board #1", readSb1.getName());
            assertEquals("Note #1", readSb1.getNote());
            assertFalse(readSb1.getUnsolved().isSolved());
            assertTrue(readSb1.getSolved().isSolved());

            int[][] readUnsolvedGrid1 = readSb1.getUnsolved().getBoard();
            assertEquals(5, readUnsolvedGrid1[0][0]);
            assertEquals(7, readUnsolvedGrid1[1][1]);

            int[][] readSolvedGrid1 = readSb1.getSolved().getBoard();
            assertEquals(9, readSolvedGrid1[0][0]);
            assertEquals(8, readSolvedGrid1[0][1]);

            SavedBoard readSb2 = readBoards.get(1);
            assertEquals("Board #2", readSb2.getName());
            assertEquals("Note #2", readSb2.getNote());

            int[][] readUnsolvedGrid2 = readSb2.getUnsolved().getBoard();
            assertEquals(3, readUnsolvedGrid2[2][2]);

            int[][] readSolvedGrid2 = readSb2.getSolved().getBoard();
            assertEquals(1, readSolvedGrid2[0][0]);
            assertEquals(9, readSolvedGrid2[8][8]);
        } catch (IOException e) {
            fail();
        }
    }
}
