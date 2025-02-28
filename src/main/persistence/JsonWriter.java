package persistence;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.SavedBoard;

public class JsonWriter {

    // effects: creates a JsonWriter that writes in a given file
    public JsonWriter(String destination) {

    }

    // modifies: this
    // effects: opens writer in the given file
    public void open() throws FileNotFoundException {

    }

    // effects: writes given array list of SavedBoard's to Json
    public void write(ArrayList<SavedBoard> savedBoards) {

    }

    // effects: closes writer 
    public void close() {

    }
}
