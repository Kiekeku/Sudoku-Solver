package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSavedBoard {
    SavedBoard test;
    Board unsolved;
    Board solved;

    @BeforeEach
    void setup() {
        unsolved = new Board();
        solved = new Board();
        solved.solve();
        test = new SavedBoard("test", "t1", unsolved, solved);
    }

    @Test
    public void testConstructor() {
        assertFalse(test.getUnsolved().isSolved());
        assertTrue(test.getSolved().isSolved());
        assertEquals("test", test.getName());
        assertEquals("t1", test.getNote());
        assertEquals("test", test.toString());
    }

    @Test
    public void testSetters() {
        assertEquals(unsolved, test.getUnsolved());
        assertEquals(solved, test.getSolved());
        assertEquals("test", test.getName());
        assertEquals("t1", test.getNote());
        test.setName("testing");
        assertEquals(unsolved, test.getUnsolved());
        assertEquals(solved, test.getSolved());
        assertEquals("testing", test.getName());
        assertEquals("t1", test.getNote());
        test.setNote("t2");
        assertEquals(unsolved, test.getUnsolved());
        assertEquals(solved, test.getSolved());
        assertEquals("testing", test.getName());
        assertEquals("t2", test.getNote());
    }

    @Test
    public void testNullTextSanitized() {
        SavedBoard nullTextBoard = new SavedBoard(null, null, unsolved, solved);
        assertEquals("", nullTextBoard.getName());
        assertEquals("", nullTextBoard.getNote());

        nullTextBoard.setName(null);
        nullTextBoard.setNote(null);
        assertEquals("", nullTextBoard.getName());
        assertEquals("", nullTextBoard.getNote());
    }
}
