package action;

import field.ChessFieldCreationImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameAction implements ActionListener {
    private final ChessFieldCreationImpl field;

    public NewGameAction(ChessFieldCreationImpl field) {
        this.field = field;
    }

    @Override
        public void actionPerformed(ActionEvent e) {
        field.clearField();
        field.gameField();
    }
}
