package breakout;
 
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
 
public class Breakout {
    
    private static CardLayout card;
    private static JPanel cards;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740, 480);
        frame.setFocusable(false);
        frame.setLayout(new BorderLayout());
        
        cards = new JPanel(new CardLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
 
        Ball ball = new Ball(20);
        Paddle paddle = new Paddle(100, 15, 50);
 
        BreakoutPanel breakoutPanel = new BreakoutPanel(ball, paddle);
        ScorePanel scorePanel = new ScorePanel();
        
        GameOver gameOver = new GameOver();
        //gameOver.addMouseListener(gameOver);
        
        PauseMenu pauseMenu = new PauseMenu();
        //pauseMenu.addMouseListener(pauseMenu);
 
        GameManager manager = new GameManager(ball, paddle, breakoutPanel, scorePanel);
        manager.startGame();
 
        breakoutPanel.setManager(manager);
        breakoutPanel.setFocusable(true);
        
        mainPanel.add(breakoutPanel, BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.EAST);
        cards.add(mainPanel, "Main");
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
 
        breakoutPanel.requestFocusInWindow();
    }
    
    public static void changeCard(String name) {
        card.show(cards, name);
    }
}