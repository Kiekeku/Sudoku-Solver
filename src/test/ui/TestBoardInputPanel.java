package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Frame;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBoardInputPanel {
    private static final String TEST_FILE = "./data/testBoardInputPanel.json";
    private static final int[][] SOLVED_BOARD = {
            { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
            { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
            { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
            { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
            { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
            { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
            { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
            { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
            { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

    private FakeDialogService dialogService;
    private SudokuGUI gui;
    private BoardInputPanel panel;
    private JTextField[][] cells;

    @BeforeEach
    void setup() {
        dialogService = new FakeDialogService();
        gui = new SudokuGUI(dialogService, TEST_FILE);
        panel = UiTestUtils.findComponent(gui, BoardInputPanel.class);
        cells = (JTextField[][]) UiTestUtils.getPrivateField(panel, "cells");
    }

    @AfterEach
    void tearDown() {
        for (Frame frame : Frame.getFrames()) {
            frame.dispose();
        }
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testReadGridFromFieldsValidAndInvalid() {
        cells[0][0].setText("x");
        int[][] invalidGrid = (int[][]) UiTestUtils.invokePrivate(panel, "readGridFromFields", new Class<?>[] {});
        assertNull(invalidGrid);
        assertTrue(dialogService.getMessages().stream().anyMatch(m -> m.contains("Invalid input at cell (1,1)")));

        dialogService.clearMessages();
        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        int[][] validGrid = (int[][]) UiTestUtils.invokePrivate(panel, "readGridFromFields", new Class<?>[] {});
        assertNotNull(validGrid);
        assertEquals(5, validGrid[0][0]);
        assertEquals(9, validGrid[8][8]);
    }

    @Test
    void testPromptAndSaveBoardPairingBranches() {
        Board unsolved = new Board();
        Board solved = new Board();
        solved.fill(SOLVED_BOARD);

        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        dialogService.queueConfirm(JOptionPane.NO_OPTION);
        UiTestUtils.invokePrivate(panel, "promptAndSaveBoardPairing", new Class<?>[] { Board.class, Board.class },
                unsolved, solved);
        assertEquals(0, gui.getSavedBoards().size());
        assertEquals("", cells[0][0].getText());

        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        dialogService.queueConfirm(JOptionPane.YES_OPTION);
        dialogService.queueInput(null);
        UiTestUtils.invokePrivate(panel, "promptAndSaveBoardPairing", new Class<?>[] { Board.class, Board.class },
                unsolved, solved);
        assertTrue(dialogService.getMessages().contains("Save canceled."));
        assertEquals(0, gui.getSavedBoards().size());

        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        dialogService.queueConfirm(JOptionPane.YES_OPTION);
        dialogService.queueInput("   ");
        UiTestUtils.invokePrivate(panel, "promptAndSaveBoardPairing", new Class<?>[] { Board.class, Board.class },
                unsolved, solved);
        assertTrue(dialogService.getMessages().contains("Board pairing not saved: name cannot be empty."));
        assertEquals(0, gui.getSavedBoards().size());

        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        dialogService.queueConfirm(JOptionPane.YES_OPTION);
        dialogService.queueInput("saved");
        dialogService.queueInput(null);
        UiTestUtils.invokePrivate(panel, "promptAndSaveBoardPairing", new Class<?>[] { Board.class, Board.class },
                unsolved, solved);
        assertEquals(1, gui.getSavedBoards().size());
        assertEquals("saved", gui.getSavedBoards().get(0).getName());
        assertEquals("", gui.getSavedBoards().get(0).getNote());
        assertTrue(dialogService.getMessages().contains("Board pairing saved!"));
    }

    @Test
    void testSolveBoardAsyncSolvableAndUnsolvable() {
        UiTestUtils.fillGrid(cells, SOLVED_BOARD);
        dialogService.queueConfirm(JOptionPane.YES_OPTION);
        dialogService.queueInput("from-solve");
        dialogService.queueInput("note");
        UiTestUtils.invokePrivate(panel, "solveBoardAsync", new Class<?>[] {});

        UiTestUtils.waitForCondition(() -> gui.getSavedBoards().size() == 1, 3000);
        assertEquals("from-solve", gui.getSavedBoards().get(0).getName());

        int[][] unsolvable = {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
        UiTestUtils.fillGrid(cells, unsolvable);
        dialogService.clearMessages();
        UiTestUtils.invokePrivate(panel, "solveBoardAsync", new Class<?>[] {});

        UiTestUtils.waitForCondition(
                () -> dialogService.getMessages().stream().anyMatch(m -> m.contains("This board is unsolvable.")),
                3000);
        assertTrue(dialogService.getMessages().stream().anyMatch(m -> m.contains("This board is unsolvable.")));
    }

    @Test
    void testSetControlsEnabled() {
        JButton btnSolve = (JButton) UiTestUtils.getPrivateField(panel, "btnSolve");
        JButton btnBack = (JButton) UiTestUtils.getPrivateField(panel, "btnBack");

        UiTestUtils.invokePrivate(panel, "setControlsEnabled", new Class<?>[] { boolean.class }, false);
        assertFalse(btnSolve.isEnabled());
        assertFalse(btnBack.isEnabled());
        assertFalse(cells[0][0].isEditable());

        UiTestUtils.invokePrivate(panel, "setControlsEnabled", new Class<?>[] { boolean.class }, true);
        assertTrue(btnSolve.isEnabled());
        assertTrue(btnBack.isEnabled());
        assertTrue(cells[0][0].isEditable());
    }
}
