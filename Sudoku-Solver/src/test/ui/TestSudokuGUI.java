package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Frame;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.Board;
import model.SavedBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSudokuGUI {
    private static final String TEST_FILE = "./data/testGuiSavedBoards.json";
    private FakeDialogService dialogService;
    private SudokuGUI gui;

    @BeforeEach
    void setup() {
        dialogService = new FakeDialogService();
        gui = new SudokuGUI(dialogService, TEST_FILE);
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
    void testShowPanelAndWindowCallbacks() {
        gui.showPanel("SavedBoards");
        gui.showPanel("MainMenu");
        gui.windowOpened(null);
        gui.windowClosed(null);
        gui.windowIconified(null);
        gui.windowDeiconified(null);
        gui.windowActivated(null);
        gui.windowDeactivated(null);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        try {
            gui.windowClosing(null);
        } finally {
            System.setOut(originalOut);
        }

        JPanel contentPane = (JPanel) gui.getContentPane();
        assertTrue(contentPane.getComponentCount() > 0);
    }

    @Test
    void testSaveAndLoadBoardsSuccess() {
        Board unsolved = new Board();
        Board solved = new Board();
        solved.solve();
        ArrayList<SavedBoard> boards = new ArrayList<>();
        boards.add(new SavedBoard("GUI test", "note", unsolved, solved));
        gui.setSavedBoards(boards);

        gui.saveBoardsToFile();
        assertTrue(dialogService.getMessages().contains("Boards saved successfully!"));

        gui.setSavedBoards(new ArrayList<>());
        gui.loadBoardsFromFile();

        assertEquals(1, gui.getSavedBoards().size());
        assertTrue(dialogService.getMessages().stream().anyMatch(m -> m.contains("Boards loaded successfully!")));
    }

    @Test
    void testLoadBoardsFailureAndAccessors() {
        SudokuGUI badGui = new SudokuGUI(dialogService, "./data/no-such-file-" + System.nanoTime() + ".json");
        badGui.loadBoardsFromFile();
        assertTrue(dialogService.getMessages().stream().anyMatch(m -> m.startsWith("Failed to load boards:")));

        ArrayList<SavedBoard> boards = new ArrayList<>();
        badGui.setSavedBoards(boards);
        assertEquals(boards, badGui.getSavedBoards());
        badGui.dispose();
    }
}
