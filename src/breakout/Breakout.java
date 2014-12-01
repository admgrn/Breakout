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
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740, 480);
        frame.setFocusable(false);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        
        cards = new JPanel(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());
 
        Ball ball = new Ball(20);
        Paddle paddle = new Paddle(100, 15, 50);
 
        BreakoutPanel breakoutPanel = new BreakoutPanel(ball, paddle);
        ScorePanel scorePanel = new ScorePanel();
        
        MainMenu mainMenu = new MainMenu();
        GameOver gameOver = new GameOver();
        PauseMenu pauseMenu = new PauseMenu();
 
        GameManager manager = new GameManager(ball, paddle, breakoutPanel, scorePanel);
        manager.startGame();
 
        breakoutPanel.setManager(manager);
        breakoutPanel.setFocusable(true);
        
        mainPanel.add(breakoutPanel, BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.EAST);
        
        cards.add(mainMenu, "Main Menu");
        cards.add(mainPanel, "Breakout");
        cards.add(gameOver, "Game Over");
        cards.add(pauseMenu, "Pause Menu");
 
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
    }
    
    public static void changeCard(String name) {
        card.show(cards, name);
    }
}