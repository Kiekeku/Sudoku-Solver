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
        this.name = sanitizeText(name);
        this.note = sanitizeText(note);
        this.unsolved = unsolved;
        this.solved = solved;
        EventLog.getInstance().logEvent(new Event("Board pairing saved."));
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
        this.name = sanitizeText(newName);
        EventLog.getInstance().logEvent(new Event("Edited name of a board."));
    }

    public String getNote() {
        return note;
    }

    public void setNote(String newNote) {
        this.note = sanitizeText(newNote);
        EventLog.getInstance().logEvent(new Event("Edited note of a board."));
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

    // EFFECTS: converts null text values to empty strings for JSON compatibility
    private String sanitizeText(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }
}
