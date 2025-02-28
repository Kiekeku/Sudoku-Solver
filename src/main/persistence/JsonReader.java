package persistence;

import java.io.IOException;
import java.util.ArrayList;

import model.Board;
import model.SavedBoard;
public class JsonReader {

    // modifies: this
    // effetcs: creates a reader that reads from the file at the given path
    public JsonReader(String source) {

    }

    // effects: returns list of saved boards in the given file
    public ArrayList<SavedBoard> read() throws IOException {
        return new ArrayList<SavedBoard>(); //stub
    }

    // effects: returns a string version of the given Json file
    private String readfile() {
        return ""; //stub
    }

    // effects: returns saved board pairing from Json
    private SavedBoard getSavedBoard() {
        return new SavedBoard(null, null, null, null); //stub
    }

    // effects: returns Board from a Json array
    private Board getBoard() {
        return new Board();
    }
}
