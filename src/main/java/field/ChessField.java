package field;

import java.awt.Font;

public class ChessField {
    private static final Font FONT = new Font("SanSerif", Font.BOLD, 20);
    private static final int MAX = 700;
    private static final int MIN = 0;
    private static final int HEIGHT = 100;
    private static boolean black = false;
    private static boolean turnW = true;
    private static boolean step = false;

    public ChessField() {
    }

    public static Font getFont() {
        return FONT;
    }

    public static int getMax() {
        return MAX;
    }

    public static int getMin() {
        return MIN;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static boolean isBlack() {
        return black;
    }

    public static void setBlack(boolean black) {
        ChessField.black = black;
    }

    public static boolean isTurnW() {
        return turnW;
    }

    public static void setTurnW(boolean turnW) {
        ChessField.turnW = turnW;
    }

    public static boolean isStep() {
        return step;
    }

    public static void setStep(boolean step) {
        ChessField.step = step;
    }
}
