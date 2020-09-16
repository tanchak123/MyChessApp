package action;

import field.ChessField;
import field.ChessFieldCreationImpl;
import figures.Figures;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Draw implements ActionListener {
    private final ChessFieldCreationImpl field;

    public Draw(ChessFieldCreationImpl field) {
        this.field = field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        field.clearField();
        JButton button = new JButton("DRAW");
        button.setFont(ChessField.getFont());
        button.setBackground(Color.pink);
        button.addActionListener(new NewGameAction(field));
        button.setBounds(250, 200, 300, 100);
        field.add(button);
        field.createWinnerBanner(Figures.DRAW.getUrl(), 200, 300);
    }
}
