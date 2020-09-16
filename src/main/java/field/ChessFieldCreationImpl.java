package field;

import action.Action;
import action.Draw;
import action.NewGameAction;
import figures.Figures;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sounds.Sounds;

public class ChessFieldCreationImpl extends JPanel implements ChessFieldCreation {
    private JButton b1;
    private final action.Action handler = new Action(this);

    public ChessFieldCreationImpl() {
        gameField();
    }

    public void gameField() {
        setLayout(null);
        b1 = new JButton("New Game");
        b1.setFont(ChessField.getFont());
        b1.addActionListener(new NewGameAction(this));
        b1.setBounds(0, ChessField.getMax() + 100,
                (ChessField.getMax() + 100) / 2, ChessField.getHeight());
        b1.setBackground(Color.pink);
        add(b1);
        b1 = new JButton("Draw");
        b1.setFont(ChessField.getFont());
        b1.addActionListener(new Draw(this));
        b1.setBounds((ChessField.getMax() + 100) / 2, ChessField.getMax() + 100,
                (ChessField.getMax() + 100) / 2, ChessField.getHeight());
        b1.setBackground(Color.pink);
        add(b1);
        int zero = 0;
        while (zero < ChessField.getMax() + 100) {
            Figures figure = Figures.SPACE;
            if (zero == 100) {
                figure = Figures.PAWNB;
            } else if (zero == 600) {
                figure = Figures.PAWNW;
            }
            for (int i = 0; i < 8; i++) {
                if (zero == ChessField.getMin()) {
                    figure = Figures.valueOf(switcher(i) + "B");
                } else if (zero == ChessField.getMax()) {
                    figure = Figures.valueOf(switcher(i) + "W");
                }
                createBottom(figure, i * 100, zero, handler);
            }
            zero += 100;
            ChessField.setBlack(!ChessField.isBlack());
        }
    }

    public void createBottom(Figures figure, int x, int y, ActionListener actionListener) {
        b1 = new JButton();
        if (figure.getUrl().length() > 1) {
            b1.setIcon(createIcon(figure));
        } else {
            b1.setText("");
        }
        b1.setName(figure.name());
        b1.setBorderPainted(true);
        b1.setDoubleBuffered(false);
        b1.setFocusPainted(false);
        b1.setFont(ChessField.getFont());
        if (ChessField.isBlack()) {
            b1.setBackground(Color.getHSBColor(52, 33, 4));
        } else {
            b1.setBackground(Color.WHITE);
        }
        ChessField.setBlack(!ChessField.isBlack());
        b1.addActionListener(actionListener);
        add(b1);
        b1.setBounds(x, y, 100, ChessField.getHeight());
    }

    public ImageIcon createIcon(Figures figure) {
        ImageIcon imageIcon = null;
        try {
            BufferedImage buttonIcon = ImageIO.read((getClass().getResource(figure.getUrl())));
            imageIcon = new ImageIcon(buttonIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

    public String switcher(int i) {
        switch (i) {
            case 1:
            case 6:
                return "KNIGHT";
            case 2:
            case 5:
                return "OFFICER";
            case 3:
                return "KING";
            case 0:
            case 7:
                return "CASTLE";
            case 4:
                return "QUEEN";
            default:
                return "SPACE";
        }
    }

    public JButton toCopy(JButton target) {
        JButton copy = new JButton();
        copy.setBackground(target.getBackground());
        copy.setIcon(target.getIcon());
        copy.setName(target.getName());
        copy.setBounds(target.getBounds());
        return copy;
    }

    public JButton formCopy(JButton target, JButton copy) {
        target.setBackground(copy.getBackground());
        target.setIcon(copy.getIcon());
        target.setName(copy.getName());
        return target;
    }

    public void clearField() {
        ChessField.setBlack(false);
        ChessField.setStep(false);
        ChessField.setTurnW(true);
        Clip clip = ChessField.getMusic();
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        this.removeAll();
        revalidate();
        repaint();
    }

    public void createWinnerBanner(String name, int x, int y) {
        JLabel imagine = null;
        try {
            imagine = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource(name))));
            imagine.setSize(imagine.getPreferredSize());
            imagine.setLocation(x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(imagine);
    }

    public void playSound(String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            getClass().getResource(url));
                    clip.open(inputStream);
                    clip.flush();
                    clip.start();
                    if (url.equals(Sounds.VICTORY.getUrl())) {
                        ChessField.setMusic(clip);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
