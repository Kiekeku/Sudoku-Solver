package ui;

import model.Board;

public class SavedBoard {
    private String name;
    private String note;
    private Board unsolved;
    private Board solved;

    public SavedBoard(String name, String note, Board unsolved, Board solved) {
        this.name = name;
        this.note = note;
        this.unsolved = unsolved; // store a copy in real usage
        this.solved = solved; // store a copy in real usage
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public Board getUnsolved() {
        return unsolved;
    }

    public Board getSolved() {
        return solved;
    }
}
