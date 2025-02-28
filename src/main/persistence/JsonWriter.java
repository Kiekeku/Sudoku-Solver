package persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Board;
import model.SavedBoard;

public class JsonWriter {

    private String destination;
    private PrintWriter writer;

    // EFFECTS: creates a JsonWriter to write to the given file path
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws IOException if file cannot be opened
    public void open() throws IOException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the given list of SavedBoard objects as a JSON array
    public void write(ArrayList<SavedBoard> savedBoards) {
        JSONArray jsonArray = new JSONArray();
        for (SavedBoard sb : savedBoards) {
            JSONObject sbJson = savedBoardToJson(sb);
            jsonArray.put(sbJson);
        }
        // 4 is for indenting
        writer.print(jsonArray.toString(4));
    }

    // EFFECTS: closes this writer
    public void close() {
        writer.close();
    }

    // EFFECTS: converts one SavedBoard object to JSON
    private JSONObject savedBoardToJson(SavedBoard sb) {
        JSONObject json = new JSONObject();
        json.put("name", sb.getName());
        json.put("note", sb.getNote());
        json.put("unsolved", boardToJson(sb.getUnsolved()));
        json.put("solved", boardToJson(sb.getSolved()));
        return json;
    }

    // EFFECTS: converts a Board to JSON
    private JSONObject boardToJson(Board board) {
        JSONObject boardJson = new JSONObject();

        // Convert the 9x9 grid into JSON
        JSONArray rowsArray = new JSONArray();
        int[][] grid = board.getBoard();
        for (int i = 0; i < 9; i++) {
            JSONArray rowArray = new JSONArray();
            for (int j = 0; j < 9; j++) {
                rowArray.put(grid[i][j]);
            }
            rowsArray.put(rowArray);
        }

        boardJson.put("grid", rowsArray);
        return boardJson;
    }
}
