package ui;

import java.awt.*;
import javax.swing.*;

import model.Board;
import model.SavedBoard;

// class responsible for handling inputs
public class BoardInputPanel extends JPanel {
    private SudokuGUI parent;
    private DialogService dialogService;
    private JTextField[][] cells;
    private JButton btnSolve;
    private JButton btnBack;

    // EFFECTS: sets up a panel for user input
    public BoardInputPanel(SudokuGUI parent) {
        this(parent, new SwingDialogService());
    }

    // EFFECTS: sets up a panel for user input with injected dialogs for testing
    BoardInputPanel(SudokuGUI parent, DialogService dialogService) {
        this.parent = parent;
        this.dialogService = dialogService;
        setName("BoardInput");
        setLayout(new BorderLayout());
        add(createGridPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    // EFFECTS: creates the panel for the user to input
    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        cells = new JTextField[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }
        return gridPanel;
    }

    // EFFECTS: creates a menu for solving a board
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnSolve = new JButton("Solve");
        btnSolve.addActionListener(e -> solveBoardAsync());
        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> parent.showPanel("MainMenu"));
        btnPanel.add(btnSolve);
        btnPanel.add(btnBack);
        return btnPanel;
    }

    // MODIFIES: this
    // EFFECTS: solves the inputted board
    private void solveBoardAsync() {
        int[][] grid = readGridFromFields();
        if (grid == null) {
            return;
        }

        Board unsolvedBoard = new Board();
        unsolvedBoard.fill(grid);
        Board solvedBoard = new Board();
        solvedBoard.fill(grid);
        setControlsEnabled(false);

        SwingWorker<Boolean, Void> solveWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return solvedBoard.solve();
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        updateGridDisplay(solvedBoard.getBoard());
                        promptAndSaveBoardPairing(unsolvedBoard, solvedBoard);
                    } else {
                        dialogService.showMessage(BoardInputPanel.this, "This board is unsolvable.");
                    }
                } catch (Exception e) {
                    dialogService.showMessage(BoardInputPanel.this,
                            "An error occurred while solving the board: " + e.getMessage());
                } finally {
                    setControlsEnabled(true);
                }
            }
        };
        solveWorker.execute();
    }

    // MODIFIES: this
    // EFFECTS: enables/disables board inputs and action buttons
    private void setControlsEnabled(boolean isEnabled) {
        btnSolve.setEnabled(isEnabled);
        btnBack.setEnabled(isEnabled);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setEditable(isEnabled);
            }
        }
    }

    // EFFECTS: takes the user's input and transfers it into an array
    private int[][] readGridFromFields() {
        int[][] grid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText().trim();
                if (text.isEmpty()) {
                    grid[row][col] = 0; // unknown cell
                } else {
                    try {
                        int num = Integer.parseInt(text);
                        if (num < 1 || num > 9) {
                            throw new NumberFormatException();
                        }
                        grid[row][col] = num;
                    } catch (NumberFormatException ex) {
                        dialogService.showMessage(this,
                                "Invalid input at cell (" + (row + 1) + "," + (col + 1) + ").\n"
                                        + "Please enter a number between 1 and 9 or leave empty.");
                        return null;
                    }
                }
            }
        }
        return grid;
    }

    // MODIFIES: this
    // EFFECTS: updates the unsolved board to the solved one
    private void updateGridDisplay(int[][] solvedGrid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText(Integer.toString(solvedGrid[row][col]));
            }
        }
    }

    // EFFECTS: sets up a menu to save the board pairing and does so
    private void promptAndSaveBoardPairing(Board unsolvedBoard, Board solvedBoard) {
        int option = dialogService.showConfirm(this, "Solution found! Do you want to save this board pairing?",
                "Save Board", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String name = dialogService.showInput(this, "Enter a name for this board pairing:");
            if (name == null) {
                dialogService.showMessage(this, "Save canceled.");
                resetTextFields();
                return;
            }
            name = name.trim();
            if (name.isEmpty()) {
                dialogService.showMessage(this, "Board pairing not saved: name cannot be empty.");
                resetTextFields();
                return;
            }
            String note = dialogService.showInput(this, "Enter a note or description:");
            if (note == null) {
                note = "";
            } else {
                note = note.trim();
            }
            SavedBoard sb = new SavedBoard(name, note, unsolvedBoard, solvedBoard);
            parent.getSavedBoards().add(sb);
            dialogService.showMessage(this, "Board pairing saved!");
        }
        resetTextFields();
    }

    // EFFECTS: helper method to clear all text fields
    private void resetTextFields() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText("");
            }
        }
    }
}
