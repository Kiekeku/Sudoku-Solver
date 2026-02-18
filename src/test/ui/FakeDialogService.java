package ui;

import java.awt.Component;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

// deterministic dialog test double for UI tests
class FakeDialogService implements DialogService {
    private final Deque<Integer> confirmResponses = new ArrayDeque<>();
    private final Deque<String> inputResponses = new LinkedList<>();
    private final List<String> messages = new ArrayList<>();

    void queueConfirm(int response) {
        confirmResponses.addLast(response);
    }

    void queueInput(String response) {
        inputResponses.addLast(response);
    }

    List<String> getMessages() {
        return messages;
    }

    void clearMessages() {
        messages.clear();
    }

    @Override
    public void showMessage(Component parent, String message) {
        messages.add(message);
    }

    @Override
    public int showConfirm(Component parent, String message, String title, int optionType) {
        if (confirmResponses.isEmpty()) {
            return JOptionPane.NO_OPTION;
        }
        return confirmResponses.removeFirst();
    }

    @Override
    public String showInput(Component parent, String message) {
        if (inputResponses.isEmpty()) {
            return null;
        }
        return inputResponses.removeFirst();
    }

    @Override
    public String showInput(Component parent, String message, String initialValue) {
        return showInput(parent, message);
    }
}
