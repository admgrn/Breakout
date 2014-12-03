package breakout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JFrame;

public class MainMenu extends JPanel implements MouseListener {
    private final Font titleFont = new Font(Font.DIALOG, Font.BOLD, 70);
    private final JLabel breakout = new JLabel("Breakout");
    private final JButton newGame = new JButton("New Game");
    private final JButton loadGame = new JButton("Load Game");
    private final JButton options = new JButton("Options");
    private final JButton quitGame = new JButton("Quit Game");
    private final JButton levelEditor = new JButton("Level Editor");
    private final BreakoutPanel panel;
    private final GameManager game;
    
    public MainMenu(BreakoutPanel panel, GameManager game) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        this.panel = panel;
        this.game = game;
        
        this.setBackground(Color.LIGHT_GRAY);
        breakout.setFont(titleFont);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        this.add(breakout, constraints);
        
        constraints.gridy = 2;
        this.add(Box.createVerticalStrut(30), constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        this.add(newGame, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        this.add(loadGame, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 5;
        this.add(options, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        this.add(levelEditor, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 7;
        this.add(quitGame, constraints);
        
        newGame.addMouseListener(this);
        loadGame.addMouseListener(this);
        options.addMouseListener(this);
        levelEditor.addMouseListener(this);
        quitGame.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        JButton clicked = (JButton)e.getSource();
        switch(clicked.getText()) {
            case "New Game":
                game.newGame();
                game.startGame();
                Breakout.changeCard(Breakout.BREAKOUT);
                break;
            case "Load Game":
                game.load();
                
                Breakout.changeCard(Breakout.BREAKOUT);
                
                panel.revalidate();
                panel.repaint();
                
                break;
            case "Options":
                Breakout.changeCard(Breakout.OPTIONS);
                break;
            case "Level Editor":
                Breakout.changeCard(Breakout.LEVEL_EDITOR);
                break;
            case "Quit Game":
                Container frame = clicked.getParent();
                do {
                    frame = frame.getParent();
                }
                while (!(frame instanceof JFrame));
                ((JFrame)frame).dispose();
                System.exit(0);
                break;
            default:
                break;
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
