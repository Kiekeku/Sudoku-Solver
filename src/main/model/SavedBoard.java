package model;

// represents an unsolved board, a solved board, a name, and a note
public class SavedBoard {
    private String name;
    private String note;
    private Board unsolved;
    private Board solved;

    // modifies: this
    // effects: creates a board pairing with the given boards, name, and note
    public SavedBoard(String name, String note, Board unsolved, Board solved) {
        this.name = name;
        this.note = note;
        this.unsolved = unsolved; 
        this.solved = solved; 
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
