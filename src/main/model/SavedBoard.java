package model;

import org.json.JSONObject;

// represents an unsolved board, a solved board, a name, and a note
public class SavedBoard {
    private String name;
    private String note;
    private Board unsolved;
    private Board solved;

    // MODIFIES: this
    // EFFECTS: creates a board pairing with the given boards, name, and note
    public SavedBoard(String name, String note, Board unsolved, Board solved) {
        this.name = name;
        this.note = note;
        this.unsolved = unsolved;
        this.solved = solved;
    }

    // EFFECTS: converts a saved board into a JsonObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("note", note);
        json.put("unsolved", unsolved.toJson());
        json.put("solved", solved.toJson());
        return json;
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

    // EFFECTS: overrides toString to make UI easier
    @Override
    public String toString() {
        return name; 
    }
}
