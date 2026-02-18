package ui;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JTextField;

// reflection and Swing helpers for UI tests
class UiTestUtils {
    private UiTestUtils() {
    }

    static <T extends Component> T findComponent(Container root, Class<T> type) {
        for (Component component : root.getComponents()) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
            if (component instanceof Container) {
                T childMatch = findComponent((Container) component, type);
                if (childMatch != null) {
                    return childMatch;
                }
            }
        }
        return null;
    }

    static Object invokePrivate(Object target, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Object getPrivateField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void setPrivateField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void waitForCondition(Check condition, long timeoutMs) {
        long start = System.currentTimeMillis();
        while (!condition.isTrue()) {
            if (System.currentTimeMillis() - start > timeoutMs) {
                throw new AssertionError("Timed out waiting for condition");
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError("Interrupted while waiting", e);
            }
        }
    }

    static void fillGrid(JTextField[][] cells, int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    cells[row][col].setText("");
                } else {
                    cells[row][col].setText(Integer.toString(grid[row][col]));
                }
            }
        }
    }

    interface Check {
        boolean isTrue();
    }
}
