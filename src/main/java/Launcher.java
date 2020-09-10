import field.ChessFieldCreationImpl;
import javax.swing.JFrame;

public class Launcher {

    private JFrame window;

    public Launcher() {
        this.window = new JFrame("Chess");
        window.setSize(815, 940);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new ChessFieldCreationImpl());
        window.setVisible(true);
    }
}
