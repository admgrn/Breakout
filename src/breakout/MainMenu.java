package breakout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {

    private final BreakoutPanel panel;
    private final GameManager game;
    
    public MainMenu(BreakoutPanel panel, GameManager game) {
        super();

        Font titleFont = new Font(Font.DIALOG, Font.BOLD, 70);
        JLabel breakout = new JLabel("Breakout");
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Load Game");
        JButton options = new JButton("Options");
        JButton quitGame = new JButton("Quit Game");
        JButton levelEditor = new JButton("Level Editor");

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
        
        newGame.addActionListener(this);
        loadGame.addActionListener(this);
        options.addActionListener(this);
        levelEditor.addActionListener(this);
        quitGame.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        switch(clicked.getText()) {
            case "New Game":
                game.newGame();
                game.startGame();
                Breakout.changeCard(Breakout.BREAKOUT);
                break;
            case "Load Game":
                try {
                    game.load();
                    Breakout.changeCard(Breakout.BREAKOUT);
                    panel.revalidate();
                    panel.repaint();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error opening save file.");
                }
                break;
            case "Options":
                Options.setReturnTo(Breakout.MAIN_MENU);
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
}