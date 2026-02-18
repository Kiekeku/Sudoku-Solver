package ui;

import java.awt.Component;
import javax.swing.JOptionPane;

// production dialog implementation backed by JOptionPane
public class SwingDialogService implements DialogService {
    @Override
    public void showMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    @Override
    public int showConfirm(Component parent, String message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parent, message, title, optionType);
    }

    @Override
    public String showInput(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message);
    }

    @Override
    public String showInput(Component parent, String message, String initialValue) {
        return JOptionPane.showInputDialog(parent, message, initialValue);
    }
}
