package field;

import figures.Figures;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public interface ChessFieldCreation extends Field {

    void createBottom(Figures figure, int x, int y, ActionListener actionListener);

    ImageIcon createIcon(Figures figure);

    String switcher(int i);

    JButton toCopy(JButton target);

    JButton formCopy(JButton target, JButton copy);
}
