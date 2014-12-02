package breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.LinkedList;

public class LevelEditor extends JPanel implements MouseMotionListener, MouseListener {
    private CardLayout card = new CardLayout();

    private Ball ball = new Ball();
    private GridLayout gridLayout = new GridLayout(3, 5, 5, 5);
    private JPanel grid = new JPanel(gridLayout);
    private Paddle paddle = new Paddle();
    private boolean isDraggingBall = false;

    private Point startVector;
    private Point endVector;

    private double vectMagnitude = 40d;

    private JLayeredPane layers = new JLayeredPane();
    private LevelEditStartMenu editStart = new LevelEditStartMenu(this);
    private LevelEditMenu editMenu = new LevelEditMenu(this);

    Level editBlocks = new Level(3, 5, new Position(0, 0), new Transform(0, 0));

    private boolean newLevel = false;
    private int levelIndex = 0;

    public LevelEditor() {
        super();
        setLayout(new CardLayout());

        grid.setBackground(new Color(224, 224, 224));
        ball.setPosition(new Position(200, 200));

        startVector = new Point(200 + ball.getDiameter() / 2, 200 + ball.getDiameter() / 2);
        endVector = new Point();
        updateLine(new Point(220, 220), vectMagnitude);

        layers.add(grid, new Integer(20));
        layers.add(ball, new Integer(50));
        layers.add(paddle, new Integer(20));
        layers.add(editMenu, new Integer(100));

        add(editStart, "Level Edit Start");
        add(layers, "Main");
        card = (CardLayout)getLayout();

        layers.addMouseMotionListener(this);
        layers.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        revalidate();
        this.layers.setBounds(0, 0, getWidth(), getHeight());
        this.ball.setBounds(0, 0, getWidth() - 100, getHeight());
        this.grid.setBounds(0, 0, getWidth() - 100, getHeight() / 3);
        this.paddle.setBounds(0, getHeight() - paddle.getHeight(), getWidth() - 100, paddle.getHeight());
        this.editMenu.setBounds(paddle.getWidth(), 0, 100, getHeight());

        g.drawLine(startVector.x, startVector.y, endVector.x, endVector.y);
    }

    public void showCard(String name) {
        card.show(this, name);
    }

    public void setLevelToEdit(int index) {
        newLevel = false;
        levelIndex = index;

        // Clear any old levels
        while (editBlocks.getBlocks().size() > 0) {
            grid.remove(editBlocks.popBlock());
        }

        Level level = LevelPackage.getCurrentLevels(false).elementAt(index);

        gridLayout.setRows(level.getSize().getWidth());
        gridLayout.setColumns(level.getSize().getHeight());

        editBlocks.setSize(new Size(level.getSize()));

        LinkedList<BlockAbstract> blocks = level.getBlocks();

        for (BlockAbstract block : blocks) {
            if (block instanceof BlockWeak)
                editBlocks.addBlock(((BlockWeak) block).setEditible(true));
            else
                editBlocks.addBlock(block);

            grid.add(editBlocks.getBlocks().getLast());
        }

        ball.setPosition(level.getStart());
        startVector.x = level.getStart().getX() + ball.getDiameter() / 2;
        startVector.y = level.getStart().getY() + ball.getDiameter() / 2;

        updateLine(new Point(startVector.x + level.getStartTransform().x,
                   startVector.y + level.getStartTransform().y), vectMagnitude);

        editMenu.setRowsColumns(level.getSize().getWidth(), level.getSize().getHeight());

        revalidate();
        repaint();
    }

    public void updateRowsColumns(int rows, int columns) {
        int currentSize = editBlocks.getBlocks().size();
        int difference = currentSize - (rows * columns);

        editBlocks.setSize(new Size(rows, columns));

        if (difference > 0) {
            for (int i = 0; i < difference; ++i)
                grid.remove(editBlocks.popBlock());
        } else if (difference < 0) {
            difference = difference * -1;
            for (int i = 0; i < difference; ++i) {
                editBlocks.addBlock(new BlockWeak(false).setEditible(true));
                grid.add(editBlocks.getBlocks().getLast());
            }
        }

        gridLayout.setRows(rows);
        gridLayout.setColumns(columns);
        grid.setLayout(gridLayout);

        grid.validate();
        grid.repaint();
    }

    public void saveLevel(boolean save) {
        updateLine(endVector, 4.5);

        Level level = new Level(gridLayout.getRows(), gridLayout.getColumns(), ball.getPosition(),
                                new Transform(endVector.x - startVector.x, endVector.y - startVector.y));

        for (BlockAbstract block : editBlocks.getBlocks()) {
            if (block instanceof BlockWeak) {
                ((BlockWeak) block).setEditible(false);
            }
            level.addBlock(block);
        }

        if (!save)
            return;

        LevelPackage.getCurrentLevels(false).setElementAt(level, levelIndex);
    }

    private void updateLine(Point mousePosition, double mag) {
        double vectx = (double) mousePosition.x - startVector.x;
        double vecty = (double) mousePosition.y - startVector.y;

        double vectN = Math.sqrt(Math.pow(vectx, 2d) + Math.pow(vecty, 2d));
        if (vectN != 0) {
            double ux = vectx / vectN;
            double uy = vecty / vectN;

            endVector.x = (int) (startVector.x + ux * mag);
            endVector.y = (int) (startVector.y + uy * mag);
        }
    }

    public void mousePressed(MouseEvent e) {
        Point mousePosition = e.getPoint();
        Interval ballInterval = this.ball.getInterval();

        if (mousePosition.x >= ballInterval.getX1() && mousePosition.x <= ballInterval.getX2() &&
                mousePosition.y >= ballInterval.getY1() && mousePosition.y <= ballInterval.getY2()) {
            isDraggingBall = true;
        } else {
            if (mousePosition.y > grid.getHeight() && mousePosition.x < grid.getWidth()) {
                updateLine(mousePosition, vectMagnitude);
                repaint();
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        Point mousePosition = e.getPoint();

        if (isDraggingBall) {
            int x = mousePosition.x - (ball.getDiameter() / 2);
            int y = mousePosition.y - (ball.getDiameter() / 2);

            if (x < 0)
                x = 0;

            if (x > ball.getWidth() - this.ball.getDiameter())
                x = ball.getWidth() - this.ball.getDiameter();

            if (y < this.grid.getHeight())
                y = this.grid.getHeight();

            if (y > ball.getHeight() - this.paddle.getHeight() - this.ball.getDiameter())
                y = ball.getHeight() - this.paddle.getHeight() - this.ball.getDiameter();

            Position position = new Position(x, y);

            this.endVector.x += (x + this.ball.getDiameter() / 2) - this.startVector.x;
            this.endVector.y += (y + this.ball.getDiameter() / 2) - this.startVector.y;

            this.startVector.x = x + this.ball.getDiameter() / 2;
            this.startVector.y = y + this.ball.getDiameter() / 2;

            this.ball.setPosition(position);
        } else {
            if (mousePosition.y > grid.getHeight() && mousePosition.x < grid.getWidth())
                updateLine(mousePosition, vectMagnitude);
        }

        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        isDraggingBall = false;
    }

    public void mouseMoved(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}
}