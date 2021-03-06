package action;

import field.ChessField;
import field.ChessFieldCreationImpl;
import figures.Figures;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import movement.ChessMovement;
import sounds.Sounds;

public class Action implements ActionListener {
    private static final int MIN = ChessField.getMin();
    private static final int MAX = ChessField.getMax();
    private final List<JButton> buttonList = new ArrayList<>();
    private final List<JButton> buttonListCopy = new ArrayList<>();
    private final ChessFieldCreationImpl field;
    private boolean isEnable = false;
    private boolean isPawn = false;
    private String name;
    private JButton actionButton;
    private JButton b1;
    private JButton king;
    private JButton copy = null;
    private Color color;

    {
        new ChessMovement(this);
    }

    public Action(ChessFieldCreationImpl field) {
        this.field = field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionButton = (JButton) e.getSource();
        String name = actionButton.getName();
        char suffix = name.charAt(name.length() - 1);
        if (!isEnable) {
            if (!actionButton.getText().equals(" ")) {
                if (suffix == 'W' && ChessField.isTurnW()
                        || suffix == 'B' && !ChessField.isTurnW()) {
                    isEnable = true;
                    copy = field.toCopy(actionButton);
                    actionButton.setBackground(Color.GREEN);
                    checkAvailableSteps(actionButton);
                    showAvailableSteps(Figures.valueOf(actionButton.getName()));
                } else {
                    field.playSound(Sounds.WARNING.getUrl());
                }
            }
        } else {
            int abX = actionButton.getX();
            int abY = actionButton.getY();
            boolean isOnList = false;
            if (abX == copy.getX()
                    && abY == copy.getY()) {
                isOnList = true;
            }
            if (!isOnList) {
                for (JButton button : buttonList) {
                    if (button.getX() == abX
                            && button.getY() == abY) {
                        isOnList = true;
                    }
                }
            }
            if (isOnList) {
                for (JButton buttonC : buttonListCopy) {
                    b1 = (JButton) field.getComponentAt(buttonC.getX(), buttonC.getY());
                    field.formCopy(b1, buttonC);
                }
                b1 = (JButton) field.getComponentAt(copy.getX(), copy.getY());
                field.formCopy(b1, copy);
                if (!(copy.getX() == abX && copy.getY() == abY)) {
                    step(copy);
                    ChessField.setTurnW(!ChessField.isTurnW());
                    clearLists();
                    if (!ChessField.isStep()) {
                        checkAvailableSteps(actionButton);
                    } else {
                        if (isPawn && (abY == MAX || abY == MIN)) {
                            checkAvailableSteps(actionButton);
                        }
                        king.setBackground(color);
                        ChessField.setStep(!ChessField.isStep());
                    }
                }
                isEnable = false;
                isPawn = false;
                clearLists();
                if (actionButton.getBackground().equals(Color.RED)) {
                    field.playSound(Sounds.CONGRATS.getUrl());
                }
                if (suffix != copy.getName().charAt(copy.getName().length() - 1)
                        && (!name.equals(Figures.SPACE.name()))) {
                    field.playSound(chooseRandomHit(2));
                } else {
                    field.playSound(Sounds.STEP.getUrl());
                }
            }
        }
    }

    private String chooseRandomHit(int num) {
        int x = new Random().nextInt(num);
        String answer;
        switch (x) {
            case 0:
                answer = Sounds.HIT1.getUrl();
                break;
            case 1:
                answer = Sounds.HIT2.getUrl();
                break;
            case 2:
                answer = Sounds.HIT3.getUrl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + x);
        }
        return answer;
    }

    private void checkAvailableSteps(JButton button) {
        name = button.getName();
        char suffix = name.charAt(name.length() - 1);
        char enemySuffix = 'B';
        if (suffix == 'B') {
            enemySuffix = 'W';
        }
        int x = button.getX();
        int y = button.getY();
        switch (name.substring(0, name.length() - 1)) {
            case "PAWN":
                isPawn = true;
                if (y == MAX || y == MIN) {
                    for (Component component : field.getComponents()) {
                        component.setVisible(false);
                    }
                    JLabel text = new JLabel("Выбирай фигуру");
                    text.setLocation(220, 300);
                    text.setFont(ChessField.getFont().deriveFont(Font.BOLD, 45));
                    text.setSize(text.getMaximumSize());
                    field.add(text);
                    ChooseFigureAction chooseFigureAction = new ChooseFigureAction(field, button);
                    field.createBottom(Figures.valueOf(
                            "CASTLE" + suffix), 210, 400, chooseFigureAction);
                    field.createBottom(Figures.valueOf(
                            "OFFICER" + suffix), 310, 400, chooseFigureAction);
                    field.createBottom(Figures.valueOf(
                            "QUEEN" + suffix), 410, 400, chooseFigureAction);
                    field.createBottom(Figures.valueOf(
                            "KNIGHT" + suffix), 510, 400, chooseFigureAction);
                    return;
                }
                ChessMovement.pawnMovement(x, y, enemySuffix, field);
                break;
            case "QUEEN":
                ChessMovement.diagonalMovement(x, y, enemySuffix);
                ChessMovement.verticalAndHorizontalMovement(x, y, enemySuffix);
                break;
            case "OFFICER":
                ChessMovement.diagonalMovement(x, y, enemySuffix);
                break;
            case "CASTLE":
                ChessMovement.verticalAndHorizontalMovement(x, y, enemySuffix);
                break;
            case "KING":
                ChessMovement.oneStepMovement(x, y, enemySuffix);
                break;
            case "KNIGHT":
                ChessMovement.horseMovement(x, y, enemySuffix);
                break;
            default:
                isEnable = false;
                break;
        }
    }

    private void showAvailableSteps(Figures figure) {
        if (buttonList.size() == 0) {
            isEnable = false;
            actionButton.setBackground(copy.getBackground());
            field.playSound(Sounds.WARNING.getUrl());
            return;
        }
        for (JButton button : buttonList) {
            b1 = button;
            buttonListCopy.add(field.toCopy(b1));
            b1.setText("");
            b1.setIcon(field.createIcon(figure));
        }
        field.playSound(Sounds.PICKUP.getUrl());
    }

    private boolean addToList(int zeroX, int zeroY, char enemySuffix) {
        if ((zeroX > MAX || zeroX < MIN)
                || (zeroY > MAX || zeroY < MIN)) {
            return false;
        }
        b1 = (JButton) field.getComponentAt(zeroX, zeroY);
        if (checkSpaces()) {
            buttonList.add(b1);
            return true;
        }
        if (checkEnemyFigure(enemySuffix, b1)) {
            if (b1.getName().substring(0, b1.getName().length() - 1).equals("KING")) {
                if (!ChessField.isStep()) {
                    color = b1.getBackground();
                    b1.setBackground(Color.RED);
                    king = b1;
                    ChessField.setStep(!ChessField.isStep());
                    field.playSound(Sounds.CONGRATS.getUrl());
                }
            }

            buttonList.add(b1);
        }
        return false;
    }

    private void step(JButton copy) {
        name = actionButton.getName();
        if (name.substring(0, name.length() - 1).equals("KING")) {
            winner(name.charAt(name.length() - 1));
        }
        actionButton.setIcon(copy.getIcon());
        actionButton.setName(copy.getName());
        b1.setName(Figures.SPACE.name());
        b1.setIcon(null);
    }

    private boolean checkSpaces() {
        return b1.getName().equals(Figures.SPACE.name());
    }

    private boolean checkEnemyFigure(char suffix, JButton button) {
        String name = button.getName();
        return name.charAt(name.length() - 1) == suffix;
    }

    private void winner(char suffix) {
        clearLists();
        field.removeAll();
        field.setLayout(null);
        field.setSize(800, 800);
        b1 = new JButton("Чёрные победили");
        b1.addActionListener(new NewGameAction(field));
        Figures figure = Figures.VICTORYB;
        if (suffix == 'B') {
            figure = Figures.VICTORYW;
            b1.setText("Белые победили");
        }
        b1.setFont(field.getFont().deriveFont(Font.BOLD, 45));
        b1.setSize(b1.getPreferredSize());
        b1.setLocation(200, 400);
        field.add(b1);
        b1.setLayout(new FlowLayout());
        field.createWinnerBanner(figure.getUrl(), 150, 500);
        field.createWinnerBanner(Figures.valueOf(
                "VICTORYL" + suffix).getUrl(), b1.getSize().width + 200,300);
        field.createWinnerBanner(Figures.valueOf(
                "VICTORYR" + suffix).getUrl(), b1.getX() - 200,300);
        field.playSound(Sounds.VICTORY.getUrl());

    }

    private void clearLists() {
        buttonList.clear();
        buttonListCopy.clear();
    }

}
