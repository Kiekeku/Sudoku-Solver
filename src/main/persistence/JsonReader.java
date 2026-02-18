package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Board;
import model.Event;
import model.EventLog;
import model.SavedBoard;

// reads from json files in ./data/
public class JsonReader {

    private String source;

    // EFFECTS: creates a reader to read from the given file path
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the JSON file and returns all SavedBoard objects stored in it
    // throws RuntimeException if any IO error or parse error occurs
    public ArrayList<SavedBoard> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded boards."));
        return parseSavedBoards(jsonArray);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the top-level JSON array and returns a list of SavedBoards
    private ArrayList<SavedBoard> parseSavedBoards(JSONArray jsonArray) {
        ArrayList<SavedBoard> boards = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject sbJson = jsonArray.getJSONObject(i);
            SavedBoard sb = parseSavedBoard(sbJson);
            boards.add(sb);
        }
        return boards;
    }

    // EFFECTS: parses a single SavedBoard from JSON
    private SavedBoard parseSavedBoard(JSONObject json) {
        String name = json.optString("name", "");
        String note = json.optString("note", "");

        // parse "unsolved" Board
        JSONObject unsolvedJson = json.getJSONObject("unsolved");
        Board unsolvedBoard = parseBoard(unsolvedJson);

        // parse "solved" Board
        JSONObject solvedJson = json.getJSONObject("solved");
        Board solvedBoard = parseBoard(solvedJson);

        return new SavedBoard(name, note, unsolvedBoard, solvedBoard);
    }

    // EFFECTS: parses a Board from JSON
    private Board parseBoard(JSONObject boardJson) {
        Board b = new Board();
        int[][] grid = new int[9][9];

        // get the "grid" array
        JSONArray rowsArray = boardJson.getJSONArray("grid");
        for (int i = 0; i < 9; i++) {
            JSONArray rowArray = rowsArray.getJSONArray(i);
            for (int j = 0; j < 9; j++) {
                grid[i][j] = rowArray.getInt(j);
            }
        }
        b.fill(grid);
        return b;
    }
}
