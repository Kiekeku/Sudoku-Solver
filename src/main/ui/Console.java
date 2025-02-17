package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Board;

public class Console {

    Scanner scanner;
    ArrayList<SavedBoard> savedBoards;
    public Console() {
        scanner = new Scanner(System.in);
        savedBoards = new ArrayList<SavedBoard>();
    }

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

    private void printMenu() {
        System.out.println("\n--- Sudoku Solver ---");
        System.out.println("1) Input an unsolved board and view solution (plus save).");
        System.out.println("2) View all saved board pairings.");
        System.out.println("3) Delete a saved board pairing.");
        System.out.println("4) Edit name/note of a saved board pairing.");
        System.out.println("5) Exit.");
        System.out.print("Choose an option: ");
    }

    /**
     * As a user, I want to input an unsolved sudoku board and see the solution.
     *    Then optionally save the pairing.
     */
    private void handleInputBoard() {
        System.out.println("\nPlease input 9 lines, each with 9 digits (0 for empty), separated by spaces.");
        int[][] unsolved = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.out.print("Row " + (row + 1) + ": ");
            String[] input = scanner.nextLine().split(" ");
            if (input.length < 9) {
                System.out.println("Invalid input, please re-enter row " + (row + 1));
                row--;
                continue;
            }
            for (int col = 0; col < 9; col++) {
                if (Integer.parseInt(input[col]) > 9 || Integer.parseInt(input[col]) < 0) {
                    row--;
                    break;
                }
                try {
                    unsolved[row][col] = Integer.parseInt(input[col]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please re-enter row " + (row + 1));
                    row--;
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
        System.out.print("Do you want to save this board pairing? (y/n) ");
        String input = scanner.nextLine().toLowerCase();
        if ("y".equals(input)) {
            saveBoardPairing(unsolvedBoard, solvedBoard);
        }
    }

    private void saveBoardPairing(Board unsolved, Board solved) {
        System.out.print("Enter a name for this board pairing: ");
        String name = scanner.nextLine();
        System.out.print("Enter a note or description: ");
        String note = scanner.nextLine();

        SavedBoard sb = new SavedBoard(name, note, unsolved, solved);
        savedBoards.add(sb);

        System.out.println("Saved board pairing!");
    }

    /**
     * As a user, I want to view all saved board pairings.
     */
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

        System.out.print("Select a board index to view details (or 0 to go back): ");
        int choice = readInt();
        if (choice > 0 && choice <= savedBoards.size()) {
            SavedBoard selected = savedBoards.get(choice - 1);
            displaySavedBoard(selected);
        } else {
            System.out.println("Returning to main menu.");
        }
    }

    private void displaySavedBoard(SavedBoard sb) {
        System.out.println("\nName: " + sb.getName());
        System.out.println("Note: " + sb.getNote());
        System.out.println("Unsolved Board:");
        printBoard(sb.getUnsolved());
        System.out.println("Solved Board:");
        printBoard(sb.getSolved());
    }

    /**
     * As a user, I want to delete a saved board pairing.
     */
    private void handleDeleteSavedBoard() {
        if (savedBoards.isEmpty()) {
            System.out.println("\nNo saved boards to delete.");
            return;
        }

        System.out.println("\n--- Delete a Saved Board ---");
        for (int i = 0; i < savedBoards.size(); i++) {
            System.out.println((i + 1) + ") " + savedBoards.get(i).getName());
        }
        System.out.print("Select a board index to delete (or 0 to cancel): ");
        int choice = readInt();
        if (choice > 0 && choice <= savedBoards.size()) {
            savedBoards.remove(choice - 1);
            System.out.println("Board pairing deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    /**
     * As a user, I want to name and add a note to each saved board pairing.
     */
    private void handleEditSavedBoard() {
        if (savedBoards.isEmpty()) {
            System.out.println("\nNo saved boards to edit.");
            return;
        }

        System.out.println("\n--- Edit Name/Note of a Saved Board ---");
        for (int i = 0; i < savedBoards.size(); i++) {
            System.out.println((i + 1) + ") " + savedBoards.get(i).getName());
        }
        System.out.print("Select a board index to edit (or 0 to cancel): ");
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

    // helpers
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

    private int readInt() {
        int val = -1;
        try {
            String input = scanner.nextLine().trim();
            val = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // do nothing, return -1
        }
        return val;
    }
}



