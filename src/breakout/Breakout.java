package breakout;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
 
public class Breakout {
    
    private static CardLayout card;
    private static JPanel cards;
    private static JPanel mainPanel;
    public static final String MAIN_MENU = "Main Menu";
    public static final String BREAKOUT = "Breakout";
    public static final String GAME_OVER = "Game Over";
    public static final String OPTIONS = "Options";
    public static final String PAUSE_MENU = "Pause Menu";
    public static final String LEVEL_EDITOR = "Level Editor";
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740, 480);
        frame.setFocusable(false);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        
        cards = new JPanel(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());
 
        Ball ball = new Ball();
        Paddle paddle = new Paddle();
 
        final BreakoutPanel breakoutPanel = new BreakoutPanel(ball, paddle);
        ScorePanel scorePanel = new ScorePanel();

        GameManager manager = new GameManager(ball, paddle, breakoutPanel, scorePanel);
        
        MainMenu mainMenu = new MainMenu(manager);
        GameOver gameOver = new GameOver();
        PauseMenu pauseMenu = new PauseMenu();

        manager.startGame();
 
        breakoutPanel.setManager(manager);
        breakoutPanel.setFocusable(true);
        
        mainPanel.add(breakoutPanel, BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.EAST);

        final LevelEditor editor = new LevelEditor();
        
        cards.add(mainMenu, Breakout.MAIN_MENU);
        cards.add(mainPanel, Breakout.BREAKOUT);
        cards.add(gameOver, Breakout.GAME_OVER);
        cards.add(pauseMenu, Breakout.PAUSE_MENU);
        cards.add(editor, Breakout.LEVEL_EDITOR);

        //frame.add(breakoutPanel, BorderLayout.CENTER);
        //frame.add(scorePanel, BorderLayout.EAST);
        frame.add(cards);
        card = (CardLayout)(cards.getLayout());
        frame.setVisible(true);
       
        JRootPane test = frame.getRootPane();
        test.setBackground(new Color(0,0,0,60));
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
                    BreakoutPanel.resume();
                    Breakout.changeCard(Breakout.BREAKOUT);
                }
            }
        });
    }
    
    public static void changeCard(String name) {
        card.show(cards, name);
    }
}