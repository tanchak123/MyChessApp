package action;

import field.ChessFieldCreationImpl;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ChooseFigureAction implements ActionListener {
    ChessFieldCreationImpl field;
    JButton copy;

    public ChooseFigureAction(ChessFieldCreationImpl field, JButton copy) {
        this.field = field;
        this.copy = copy;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton actionButton = (JButton) e.getSource();
        for (Component component : field.getComponents()) {
            if (component.isVisible()) {
                component.repaint();
                component.setVisible(false);
                component.revalidate();
            } else {
                component.setVisible(true);
            }
        }
        copy.setName(actionButton.getName());
        copy.setIcon(actionButton.getIcon());
    }
}
