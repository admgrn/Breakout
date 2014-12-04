package breakout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JRootPane;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;

public class Breakout {
    
    private static CardLayout card;
    private static JPanel cards;
    public static final String MAIN_MENU = "Main Menu";
    public static final String BREAKOUT = "Breakout";
    public static final String GAME_OVER = "Game Over";
    public static final String OPTIONS = "Options";
    public static final String PAUSE_MENU = "Pause Menu";
    public static final String LEVEL_EDITOR = "Level Editor";
    
    public static void main(String[] args) {
        JPanel mainPanel;
        JFrame frame;

        frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(740, 480);
        frame.setFocusable(false);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        cards = new JPanel(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());

        Ball ball = new Ball(frame);
        Paddle paddle = new Paddle();

        final BreakoutPanel breakoutPanel = new BreakoutPanel(ball, paddle);
        ScorePanel scorePanel = new ScorePanel();

        GameManager manager = new GameManager(ball, paddle, breakoutPanel, scorePanel);

        MainMenu mainMenu = new MainMenu(breakoutPanel, manager);
        GameOver gameOver = new GameOver();
        final PauseMenu pauseMenu = new PauseMenu(breakoutPanel, manager);
        Options optionsMenu = new Options(manager, frame);

        manager.startGame();

        breakoutPanel.setManager(manager);
        breakoutPanel.setFocusable(true);

        mainPanel.add(breakoutPanel, BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.EAST);

        final LevelEditor editor = new LevelEditor(frame);

        cards.add(mainMenu, Breakout.MAIN_MENU);
        cards.add(mainPanel, Breakout.BREAKOUT);
        cards.add(gameOver, Breakout.GAME_OVER);
        cards.add(pauseMenu, Breakout.PAUSE_MENU);
        cards.add(editor, Breakout.LEVEL_EDITOR);
        cards.add(optionsMenu, Breakout.OPTIONS);

        frame.add(cards);
        card = (CardLayout) (cards.getLayout());
        frame.setVisible(true);

        JRootPane test = frame.getRootPane();
        test.setBackground(new Color(0, 0, 0, 60));
        test.setVisible(true);

        mainPanel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                breakoutPanel.requestFocusInWindow();
            }
        });

        editor.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                editor.requestFocusInWindow();
            }
        });

        pauseMenu.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                pauseMenu.requestFocusInWindow();
            }
        });

        pauseMenu.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    breakoutPanel.resume();
                    Breakout.changeCard(Breakout.BREAKOUT);
                }
            }
        });
    }

    public static void changeCard(String name) {
        card.show(cards, name);
    }
}