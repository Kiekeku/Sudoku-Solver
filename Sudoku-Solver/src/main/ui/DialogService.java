package ui;

import java.awt.Component;

// abstraction for UI dialogs to make UI logic testable
public interface DialogService {
    void showMessage(Component parent, String message);

    int showConfirm(Component parent, String message, String title, int optionType);

    String showInput(Component parent, String message);

    String showInput(Component parent, String message, String initialValue);
}
