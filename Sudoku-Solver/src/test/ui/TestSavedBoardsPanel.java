package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Frame;
import java.io.File;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.Board;
import model.SavedBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSavedBoardsPanel {
    private static final String TEST_FILE = "./data/testSavedBoardsPanel.json";
    private FakeDialogService dialogService;
    private SudokuGUI gui;
    private SavedBoardsPanel panel;
    private SavedBoard board;

    @BeforeEach
    void setup() {
        dialogService = new FakeDialogService();
        gui = new SudokuGUI(dialogService, TEST_FILE);
        panel = UiTestUtils.findComponent(gui, SavedBoardsPanel.class);

        Board unsolved = new Board();
        int[][] grid = new int[9][9];
        grid[0][0] = 5;
        unsolved.fill(grid);
        Board solved = new Board();
        solved.fill(grid);
        solved.solve();
        board = new SavedBoard("board-name", "board-note", unsolved, solved);

        gui.getSavedBoards().clear();
        gui.getSavedBoards().add(board);
        panel.refreshList();
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

    @SuppressWarnings("unchecked")
    @Test
    void testDisplaySelectedBoard() {
        JList<SavedBoard> boardList = (JList<SavedBoard>) UiTestUtils.getPrivateField(panel, "boardList");
        JTextArea boardDetails = (JTextArea) UiTestUtils.getPrivateField(panel, "boardDetails");

        boardList.setSelectedIndex(0);
        UiTestUtils.invokePrivate(panel, "displaySelectedBoard", new Class<?>[] {});

        String details = boardDetails.getText();
        assertTrue(details.contains("Name: board-name"));
        assertTrue(details.contains("Note: board-note"));
        assertTrue(details.contains("Unsolved Board:"));
        assertTrue(details.contains("Solved Board:"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testDeleteSelectedBoardBranches() {
        JList<SavedBoard> boardList = (JList<SavedBoard>) UiTestUtils.getPrivateField(panel, "boardList");

        boardList.clearSelection();
        UiTestUtils.invokePrivate(panel, "deleteSelectedBoard", new Class<?>[] {});
        assertEquals(1, gui.getSavedBoards().size());

        boardList.setSelectedIndex(0);
        dialogService.queueConfirm(JOptionPane.NO_OPTION);
        UiTestUtils.invokePrivate(panel, "deleteSelectedBoard", new Class<?>[] {});
        assertEquals(1, gui.getSavedBoards().size());

        dialogService.queueConfirm(JOptionPane.YES_OPTION);
        UiTestUtils.invokePrivate(panel, "deleteSelectedBoard", new Class<?>[] {});
        assertEquals(0, gui.getSavedBoards().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testEditSelectedBoardBranches() {
        JList<SavedBoard> boardList = (JList<SavedBoard>) UiTestUtils.getPrivateField(panel, "boardList");

        boardList.clearSelection();
        UiTestUtils.invokePrivate(panel, "editSelectedBoard", new Class<?>[] {});
        assertEquals("board-name", board.getName());
        assertEquals("board-note", board.getNote());

        boardList.setSelectedIndex(0);
        dialogService.queueInput("updated");
        dialogService.queueInput("updated note");
        UiTestUtils.invokePrivate(panel, "editSelectedBoard", new Class<?>[] {});
        assertEquals("updated", board.getName());
        assertEquals("updated note", board.getNote());

        dialogService.queueInput("   ");
        dialogService.queueInput(null);
        UiTestUtils.invokePrivate(panel, "editSelectedBoard", new Class<?>[] {});
        assertEquals("updated", board.getName());
        assertEquals("updated note", board.getNote());
    }
}
