package ui;

import java.awt.*;
import javax.swing.*;
import model.SavedBoard;

// a panel that displayes the saved boards
public class SavedBoardsPanel extends JPanel {

    private SudokuGUI parent;
    private DefaultListModel<SavedBoard> listModel;
    private JList<SavedBoard> boardList;
    private JTextArea boardDetails;

    // EFFECTS: sets up the panel
    public SavedBoardsPanel(SudokuGUI parent) {
        this.parent = parent;
        setName("SavedBoards");
        setLayout(new BorderLayout());
        add(createListScrollPane(), BorderLayout.WEST);
        add(createDetailsScrollPane(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        refreshList();
    }

    // EFFECTS: sets up a panel that can scroll through the enitire window
    private JScrollPane createListScrollPane() {
        listModel = new DefaultListModel<>();
        boardList = new JList<>(listModel);
        boardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boardList.addListSelectionListener(e -> displaySelectedBoard());
        JScrollPane listScroll = new JScrollPane(boardList);
        listScroll.setPreferredSize(new Dimension(200, 0));
        return listScroll;
    }

    // EFFECTS: sets up a panel that can scroll through text
    private JScrollPane createDetailsScrollPane() {
        boardDetails = new JTextArea();
        boardDetails.setEditable(false);
        return new JScrollPane(boardDetails);
    }

    // EFFECTS: creates a menu for the saved boards
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSelectedBoard());
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editSelectedBoard());
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> parent.showPanel("MainMenu"));
        btnPanel.add(btnDelete);
        btnPanel.add(btnEdit);
        btnPanel.add(btnBack);
        return btnPanel;
    }

    // MODIFIES: this
    // EFFECTS: updates list of Boards
    public void refreshList() {
        listModel.clear();
        for (SavedBoard sb : parent.getSavedBoards()) {
            listModel.addElement(sb);
        }
    }

    // EFFECTS: displays a selected board
    private void displaySelectedBoard() {
        SavedBoard sb = boardList.getSelectedValue();
        if (sb != null) {
            StringBuilder details = new StringBuilder();
            details.append("Name: ").append(sb.getName()).append("\n");
            details.append("Note: ").append(sb.getNote()).append("\n\n");
            details.append("Unsolved Board:\n");
            appendBoard(details, sb.getUnsolved().getBoard());
            details.append("\nSolved Board:\n");
            appendBoard(details, sb.getSolved().getBoard());
            boardDetails.setText(details.toString());
        }
    }

    // EFFECTS: appends a board
    private void appendBoard(StringBuilder details, int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                details.append(board[row][col]).append(" ");
            }
            details.append("\n");
        }
    }

    // EFFECTS: deletes a selected board
    private void deleteSelectedBoard() {
        SavedBoard sb = boardList.getSelectedValue();
        if (sb != null) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this board pairing?",
                    "Delete Board", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                parent.getSavedBoards().remove(sb);
                refreshList();
            }
        }
    }

    // EFFECTS: allows edditing of a selected board
    private void editSelectedBoard() {
        SavedBoard sb = boardList.getSelectedValue();
        if (sb != null) {
            String newName = JOptionPane.showInputDialog(this, "Enter new name:", sb.getName());
            String newNote = JOptionPane.showInputDialog(this, "Enter new note:", sb.getNote());
            if (newName != null && !newName.trim().isEmpty()) {
                sb.setName(newName);
            }
            if (newNote != null && !newNote.trim().isEmpty()) {
                sb.setNote(newNote);
            }
            refreshList();
            displaySelectedBoard();
        }
    }
}
