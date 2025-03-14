package ui;

import java.awt.*;
import javax.swing.*;

// class that represents the main menu
public class MainMenuPanel extends JPanel {
    private SudokuGUI parent;

    // EFFECTS: sets up a new main menu panel
    @SuppressWarnings("methodlength")
    public MainMenuPanel(SudokuGUI parent) {
        this.parent = parent;
        setName("MainMenu");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton btnInputBoard = new JButton("Input Board & Solve");
        btnInputBoard.addActionListener(e -> parent.showPanel("BoardInput"));
        JButton btnSavedBoards = new JButton("View Saved Boards");
        btnSavedBoards.addActionListener(e -> parent.showPanel("SavedBoards"));
        JButton btnSaveToFile = new JButton("Save Boards to File");
        btnSaveToFile.addActionListener(e -> parent.saveBoardsToFile());
        JButton btnLoadFromFile = new JButton("Load Boards from File");
        btnLoadFromFile.addActionListener(e -> parent.loadBoardsFromFile());
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> System.exit(0));
        init(gbc, btnInputBoard, btnSavedBoards, btnSaveToFile, btnLoadFromFile, btnExit);
    }

    private void init(GridBagConstraints gbc, JButton btnInputBoard, JButton btnSavedBoards, JButton btnSaveToFile,
            JButton btnLoadFromFile, JButton btnExit) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(btnInputBoard, gbc);
        gbc.gridy = 1;
        add(btnSavedBoards, gbc);
        gbc.gridy = 2;
        add(btnSaveToFile, gbc);
        gbc.gridy = 3;
        add(btnLoadFromFile, gbc);
        gbc.gridy = 4;
        add(btnExit, gbc);
    }
}