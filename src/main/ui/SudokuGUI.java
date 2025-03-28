package ui;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import model.Event;
import model.EventLog;
import model.SavedBoard;
import persistence.JsonReader;
import persistence.JsonWriter;

// main UI class 
public class SudokuGUI extends JFrame implements WindowListener {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ArrayList<SavedBoard> savedBoards = new ArrayList<>();

    // EFFECTS: sets up a new window and panels
    public SudokuGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        addWindowListener(this);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new MainMenuPanel(this), "MainMenu");
        cardPanel.add(new BoardInputPanel(this), "BoardInput");
        cardPanel.add(new SavedBoardsPanel(this), "SavedBoards");

        add(cardPanel);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Event Log:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // No action needed
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // No action needed
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // No action needed
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // No action needed
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // No action needed
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // No action needed
    }

    // EFFECTS: switches the current panel
    public void showPanel(String name) {
        if (name.equals("SavedBoards")) {
            Component comp = getComponentByName("SavedBoards");
            if (comp instanceof SavedBoardsPanel) {
                ((SavedBoardsPanel) comp).refreshList();
            }
        }
        cardLayout.show(cardPanel, name);
    }

    // EFFECTS: finds the first component with a certain name
    private Component getComponentByName(String name) {
        for (Component comp : cardPanel.getComponents()) {
            if (((JComponent) comp).getName() != null && ((JComponent) comp).getName().equals(name)) {
                return comp;
            }
        }
        return null;
    }

    // EFFECTS: saves boards to Json files
    public void saveBoardsToFile() {
        JsonWriter writer = new JsonWriter("./data/savedboards.json");
        try {
            writer.open();
            writer.write(savedBoards);
            writer.close();
            JOptionPane.showMessageDialog(this, "Boards saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save boards: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads boards from Json file
    public void loadBoardsFromFile() {
        JsonReader reader = new JsonReader("./data/savedboards.json");
        try {
            savedBoards = reader.read();
            JOptionPane.showMessageDialog(this, "Boards loaded successfully! "
                    + savedBoards.size() + " boards loaded.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load boards: " + e.getMessage());
        }
    }

    public ArrayList<SavedBoard> getSavedBoards() {
        return savedBoards;
    }

    public void setSavedBoards(ArrayList<SavedBoard> boards) {
        this.savedBoards = boards;
    }
}
