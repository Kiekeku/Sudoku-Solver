package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Board;

// the main UI console that deals with all user inputs
public class Console {

    Scanner scanner;
    ArrayList<SavedBoard> savedBoards;

    // modifies: this
    // effects: creates new console with new scanner and no saved boards
    public Console() {
        scanner = new Scanner(System.in);
        savedBoards = new ArrayList<SavedBoard>();
    }

    // effects: runs main loop
    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleInputBoard();
                    break;
                case "2":
                    handleViewSavedBoards();
                    break;
                case "3":
                    handleDeleteSavedBoard();
                    break;
                case "4":
                    handleEditSavedBoard();
                    break;
                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input. Please choose 1-5.");
            }
        }
    }

    // effects: prints menu
    private void printMenu() {
        System.out.println("\n--- Sudoku Solver ---");
        System.out.println("1) Input an unsolved board and view solution (plus save).");
        System.out.println("2) View all saved board pairings.");
        System.out.println("3) Delete a saved board pairing.");
        System.out.println("4) Edit name/note of a saved board pairing.");
        System.out.println("5) Exit.");
        System.out.print("Choose an option: ");
    }

    // modifies: this
    // effects: inputs sudoku board, solves it and saves the pairing if the user
    // wants to
    private void handleInputBoard() {
        System.out.println("\nPlease input 9 lines, each with 9 digits (0 for empty), separated by spaces.");
        int[][] unsolved = new int[9][9];
        for (int row = 0; row < 9; row++) {
            while (true) {
                System.out.print("Row " + (row + 1) + ": ");
                String[] input = scanner.nextLine().trim().split(" ");
                if (input.length != 9) {
                    System.out.println("Invalid input. Please enter exactly 9 integers.");
                    continue;
                }
                boolean validRow = true;
                for (int col = 0; col < 9; col++) {
                    try {
                        int val = Integer.parseInt(input[col]);
                        if (val < 0 || val > 9) {
                            System.out.println("Number out of range (0-9). Re-enter this row.");
                            validRow = false;
                            break;
                        }
                        unsolved[row][col] = val;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid integer. Re-enter this row.");
                        validRow = false;
                        break;
                    }
                }
                if (validRow) {
                    break;
                }
            }
        }

        // Create a Board for the unsolved puzzle
        Board unsolvedBoard = new Board();
        unsolvedBoard.fill(unsolved);

        // Make a copy for solving
        Board solvedBoard = new Board();
        solvedBoard.fill(unsolved);

        if (solvedBoard.solve()) {
            System.out.println("\nSolution found:");
            printBoard(solvedBoard);
        } else {
            System.out.println("\nThis board is unsolvable.");
        }

        // Ask user to save
        System.out.print("Do you want to save this board pairing? (y for yes) ");
        String input = scanner.nextLine().toLowerCase();
        if ("y".equals(input)) {
            saveBoardPairing(unsolvedBoard, solvedBoard);
        }
    }

    // modifies: this
    // effects: saves a board pairing with a name and note
    private void saveBoardPairing(Board unsolved, Board solved) {
        System.out.print("Enter a name for this board pairing: ");
        String name = scanner.nextLine();
        System.out.print("Enter a note or description: ");
        String note = scanner.nextLine();

        SavedBoard sb = new SavedBoard(name, note, unsolved, solved);
        savedBoards.add(sb);

        System.out.println("Saved board pairing!");
    }

    // effects: allows the user to pick which board they want to see
    private void handleViewSavedBoards() {
        if (savedBoards.isEmpty()) {
            System.out.println("\nNo saved boards.");
            return;
        }

        System.out.println("\n--- Saved Boards ---");
        for (int i = 0; i < savedBoards.size(); i++) {
            SavedBoard sb = savedBoards.get(i);
            System.out.println((i + 1) + ") Name: " + sb.getName()
                    + " | Note: " + sb.getNote());
        }

        System.out.print("Select a board index to view details (or anything else to go back): ");
        int choice = readInt();
        if (choice > 0 && choice <= savedBoards.size()) {
            SavedBoard selected = savedBoards.get(choice - 1);
            displaySavedBoard(selected);
        } else {
            System.out.println("Returning to main menu.");
        }
    }

    // effects: prints name, note, unsolved board, and solved board
    private void displaySavedBoard(SavedBoard sb) {
        System.out.println("\nName: " + sb.getName());
        System.out.println("Note: " + sb.getNote());
        System.out.println("Unsolved Board:");
        printBoard(sb.getUnsolved());
        System.out.println("Solved Board:");
        printBoard(sb.getSolved());
    }

    // modifies: this
    // effects: deletes the indicated board pairing
    private void handleDeleteSavedBoard() {
        if (savedBoards.isEmpty()) {
            System.out.println("\nNo saved boards to delete.");
            return;
        }

        System.out.println("\n--- Delete a Saved Board ---");
        for (int i = 0; i < savedBoards.size(); i++) {
            System.out.println((i + 1) + ") " + savedBoards.get(i).getName());
        }
        System.out.print("Select a board index to delete (or anything else to cancel): ");
        int choice = readInt();
        if (choice > 0 && choice <= savedBoards.size()) {
            savedBoards.remove(choice - 1);
            System.out.println("Board pairing deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // modifies: this and SolvedBoard
    // effects: enables the user to edit the name and note of a board pairing
    private void handleEditSavedBoard() {
        if (savedBoards.isEmpty()) {
            System.out.println("\nNo saved boards to edit.");
            return;
        }

        System.out.println("\n--- Edit Name/Note of a Saved Board ---");
        for (int i = 0; i < savedBoards.size(); i++) {
            System.out.println((i + 1) + ") " + savedBoards.get(i).getName());
        }
        System.out.print("Select a board index to edit (or anything else to cancel): ");
        int choice = readInt();
        if (choice > 0 && choice <= savedBoards.size()) {
            SavedBoard sb = savedBoards.get(choice - 1);
            System.out.print("Enter new name (leave blank to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                sb.setName(newName);
            }
            System.out.print("Enter new note (leave blank to keep current): ");
            String newNote = scanner.nextLine();
            if (!newNote.isEmpty()) {
                sb.setNote(newNote);
            }
            System.out.println("Updated board pairing!");
        } else {
            System.out.println("Edit cancelled.");
        }
    }

    // effects: prints out given board
    private void printBoard(Board b) {
        if (b == null) {
            System.out.println("No board to display.");
            return;
        }
        int[][] grid = b.getBoard(); // actual int[][] from your model
        for (int row = 0; row < 9; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < 9; col++) {
                sb.append(grid[row][col]).append(" ");
            }
            System.out.println(sb.toString());
        }
    }

    // effects: handles all int inputs
    private int readInt() {
        int val = -1;
        try {
            String input = scanner.nextLine().trim();
            val = Integer.parseInt(input);
        } catch (NumberFormatException e) {}
        return val;
    }
}
