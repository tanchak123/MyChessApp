package movement;

import action.Action;
import field.ChessField;
import field.ChessFieldCreationImpl;
import figures.Figures;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JButton;

public class ChessMovement {

    private static Action action;
    private static final int MAX = ChessField.getMax();
    private static final int MIN = ChessField.getMin();
    private static Method method;

    public ChessMovement(Action action) {
        ChessMovement.action = action;
        try {
            method = action.getClass().getDeclaredMethod(
                    "addToList", int.class, int.class, char.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean addToList(int x, int y, char enemySuffix) {
        boolean answer = false;
        try {
            answer = (boolean) method.invoke(action, x, y, enemySuffix);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static void oneStepMovement(int x, int y, char enemySuffix) {
        addToList(x - 100, y - 100, enemySuffix);
        addToList(x - 100, y, enemySuffix);
        addToList(x - 100, y + 100, enemySuffix);
        addToList(x + 100, y - 100, enemySuffix);
        addToList(x + 100, y, enemySuffix);
        addToList(x + 100, y + 100, enemySuffix);
        addToList(x, y - 100, enemySuffix);
        addToList(x, y + 100, enemySuffix);
    }

    public static void verticalAndHorizontalMovement(int x, int y, char enemySuffix) {
        int zeroY = y;
        int zeroX = x;
        while (zeroX > MIN) {
            if (!addToList(zeroX -= 100, zeroY, enemySuffix)) {
                break;
            }
        }
        zeroX = x;
        while (zeroX < MAX) {
            if (!addToList(zeroX += 100, zeroY, enemySuffix)) {
                break;
            }
        }
        zeroX = x;
        while (zeroY < MAX) {
            if (!addToList(zeroX, zeroY += 100, enemySuffix)) {
                break;
            }
        }
        zeroY = y;
        while (zeroY > MIN) {
            if (!addToList(zeroX, zeroY -= 100, enemySuffix)) {
                break;
            }
        }
    }

    public static void diagonalMovement(int x, int y, char enemySuffix) {
        int zeroY = y;
        int zeroX = x;
        while (zeroX > MIN && zeroY > MIN) {
            if (!addToList(zeroX -= 100, zeroY -= 100, enemySuffix)) {
                break;
            }
        }
        zeroY = y;
        zeroX = x;
        while (zeroX < MAX && zeroY > MIN) {
            if (!addToList(zeroX += 100, zeroY -= 100, enemySuffix)) {
                break;
            }
        }
        zeroY = y;
        zeroX = x;
        while (zeroX < MAX && zeroY < MAX) {
            if (!addToList(zeroX += 100, zeroY += 100, enemySuffix)) {
                break;
            }
        }
        zeroY = y;
        zeroX = x;
        while (zeroX > MIN && zeroY < MAX) {
            if (!addToList(zeroX -= 100, zeroY += 100, enemySuffix)) {
                break;
            }
        }
    }

    public static void horseMovement(int x, int y, char enemySuffix) {
        int zeroY = y + 200;
        int zeroX = x - 100;
        if (zeroY <= MAX) {
            if (zeroX >= MIN) {
                addToList(zeroX, zeroY, enemySuffix);
            }
            zeroX = x + 100;
            if (zeroX <= MAX) {
                addToList(zeroX, zeroY, enemySuffix);
            }
        }
        zeroY = y - 200;
        if (zeroY >= MIN) {
            zeroX = x + 100;
            if (zeroX <= MAX) {
                addToList(zeroX, zeroY, enemySuffix);
            }
            zeroX = x - 100;
            if (zeroX >= MIN) {
                addToList(zeroX, zeroY, enemySuffix);
            }
        }
        zeroX = x + 200;
        if (zeroX <= MAX) {
            zeroY = y - 100;
            if (zeroY >= MIN) {
                addToList(zeroX, zeroY, enemySuffix);
            }
            zeroY = y + 100;
            if (zeroY <= MAX) {
                addToList(zeroX, zeroY, enemySuffix);
            }
        }
        zeroX = x - 200;
        if (zeroX >= MIN) {
            zeroY = y + 100;
            if (zeroY <= MAX) {
                addToList(zeroX, zeroY, enemySuffix);
            }
            zeroY = y - 100;
            if (zeroY >= MIN) {
                addToList(zeroX, zeroY, enemySuffix);
            }
        }
    }

    public static void pawnMovement(int x, int y, char enemySuffix, ChessFieldCreationImpl field) {
        JButton b1;
        if (enemySuffix == 'W') {
            if (x - 100 >= MIN) {
                b1 = (JButton) field.getComponentAt(x - 100, y + 100);
                if (b1.getName().charAt(b1.getName().length() - 1) == enemySuffix) {
                    addToList(x - 100, y + 100, enemySuffix);
                }
            }
            b1 = (JButton) field.getComponentAt(x + 100, y + 100);
            if (x + 100 <= MAX) {
                if (b1.getName().charAt(b1.getName().length() - 1) == enemySuffix) {
                    addToList(x + 100, y + 100, enemySuffix);
                }
            }
            if (field.getComponentAt(x, y + 100).getName().equals(Figures.SPACE.name())) {
                addToList(x, y + 100, enemySuffix);
                if (y == 100) {
                    b1 = (JButton) field.getComponentAt(x, y + 200);
                    if (b1.getName().equals(Figures.SPACE.name())) {
                        addToList(x, y + 200, enemySuffix);
                    }
                }
            }
        } else if (enemySuffix == 'B') {
            if ((x - 100 >= ChessField.getMin())) {
                b1 = (JButton) field.getComponentAt(x - 100, y - 100);
                if (b1.getName().charAt(b1.getName().length() - 1) == enemySuffix) {
                    addToList(x - 100, y - 100, enemySuffix);
                }
            }
            if (x + 100 <= ChessField.getMax()) {
                b1 = (JButton) field.getComponentAt(x + 100, y - 100);
                if (b1.getName().charAt(b1.getName().length() - 1) == enemySuffix) {
                    addToList(x + 100, y - 100, enemySuffix);
                }
            }
            if (field.getComponentAt(x, y - 100).getName().equals(Figures.SPACE.name())) {
                addToList(x, y - 100, enemySuffix);
                if (y == 600) {
                    b1 = (JButton) field.getComponentAt(x, y - 200);
                    if (b1.getName().equals(Figures.SPACE.name())) {
                        addToList(x, y - 200, enemySuffix);
                    }
                }
            }
        }
    }
}
