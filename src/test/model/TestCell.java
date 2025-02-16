package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Cell.*;

public class TestCell {

    Cell test;

    @BeforeEach
    void setUp() {
        test = new Cell(0,1);
    }

    @Test
    void testConstructor() {
        assertEquals(0, test.getNumber());
        assertEquals(1, test.getBoxID());
        Cell test1 = new Cell(9, 4);
        assertEquals(9, test1.getNumber());
        assertEquals(4, test1.getBoxID());
    }

    @Test
    void testSetNumber() {
        assertEquals(0, test.getNumber());
        test.setNumber(8);
        assertEquals(8, test.getNumber());
        assertEquals(1, test.getBoxID());
    }
}
