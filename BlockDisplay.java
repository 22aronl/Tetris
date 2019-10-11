import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.io.*;
import java.util.*;
/**
 * @author Anu Datar
 * 
 * Changed block size and added a split panel display for next block and Score
 * 
 * @author Ryan Adolf
 * @version 1.0
 * 
 * Fixed the lag issue with block rendering 
 * Removed the JPanel
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color BORDER = Color.BLACK;
    private static final int BLOCKSIZE = 30;
    private static final int OUTLINE = BLOCKSIZE * 2;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;
    private boolean playOn;
    private int cheat;
    private boolean cheatCode;
    private int level2;
    private int score2;
    // Constructs a new display for displaying the given board
    /**
     * Construcs the display
     * @param board the board
     */
    public BlockDisplay(MyBoundedGrid<Block> board)
    {
        this.board = board;
        playOn = true;
        cheatCode = false;
        cheat = 0;
        level2 = 1;
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * board.getNumCols() + OUTLINE *2,
                BLOCKSIZE * board.getNumRows() + OUTLINE * 2));

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Draws the block
     * @param g the grpahics
     * @param tetrad the tetrad
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void drawBlock(Graphics g, Block tetrad, int y, int x)
    {
        String fileName = tetrad.getFileName();
        if (!new File(fileName).exists())
            throw new IllegalArgumentException("bad file name:  " + fileName);
        Image image = new ImageIcon(fileName).getImage();
        g.drawImage(image, y, x, BLOCKSIZE, BLOCKSIZE, null);
    }

    /**
     * Draws the borders
     * @param g the grpahics
     * @param tetrad the tetrad
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void drawBorders(Graphics g, Block tetrad, int x, int y)
    {
        String fileName = "Blocks/greygraygrey3.png";
        if (!new File(fileName).exists())
            throw new IllegalArgumentException("bad file name:  " + fileName);
        Image image = new ImageIcon(fileName).getImage();
        g.drawImage(image, y, x, BLOCKSIZE, BLOCKSIZE, null);
    }

    /**
     * Paints the components
     * @param g the graphics
     */
    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(0, 0, BLOCKSIZE * board.getNumCols() + OUTLINE, BLOCKSIZE * board.getNumRows());
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(17, 16 ,16));
        for(int row = 0; row < board.getNumRows() + 2; row++)
        {
            Block square = new Block();
            drawBorders(g, square, row *BLOCKSIZE, 0);
            Block square2 = new Block();
            drawBorders(g, square2, row *BLOCKSIZE, OUTLINE /2 + 10 * BLOCKSIZE);
            Block square3 = new Block();
            drawBorders(g, square3, row *BLOCKSIZE, OUTLINE /2 + 18 * BLOCKSIZE);
        }
        for(int col = 0; col < 18 + 1; col ++)
        {
            Block square = new Block();
            drawBorders(g, square, 0, col * BLOCKSIZE);
            Block square2 = new Block();
            drawBorders(g, square2, OUTLINE/2 + board.getNumRows() * BLOCKSIZE, col * BLOCKSIZE);
        }
        for(int row = 0; row < board.getNumRows() + 1; row++)
        {

            g2.drawLine(OUTLINE/2, OUTLINE/2 + BLOCKSIZE * row, 
                10 * BLOCKSIZE+ OUTLINE/2 , OUTLINE/2 + BLOCKSIZE * row);
        }
        for(int col = 0; col < 10+1; col++)
        {
            g2.drawLine(col * BLOCKSIZE + OUTLINE/2, OUTLINE/2 ,
                col * BLOCKSIZE+ OUTLINE/2 , OUTLINE/2 + BLOCKSIZE * board.getNumRows());
        }
        for(int row = 8; row < 16; row++)
        {
            for(int col = 11; col < 19; col ++)
            {
                Block square2 = new Block();
                drawBorders(g, square2, OUTLINE/2 + row * BLOCKSIZE, col * BLOCKSIZE);
            }
        }
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    drawBlock(g, square, col * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2);

                //g.fillRect(col * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                //  BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);

            }
        g.setFont(new Font("Arial", 20, 25));
        g.setColor(Color.GRAY);
        g.drawString("Level: " + level2,13*BLOCKSIZE + OUTLINE /2 - BLOCKSIZE/4 , 
                    (board.getNumRows() - 1)* BLOCKSIZE + OUTLINE/2 - BLOCKSIZE/3);
        g.drawString("Score: " + score2,13*BLOCKSIZE + OUTLINE /2 - BLOCKSIZE/4,
                    (board.getNumRows() - 2)* BLOCKSIZE + OUTLINE/2 - BLOCKSIZE/3);
    }
    //Redraws the board to include the pieces and border colors.
    /**
     * Shows the blocks
     */
    public void showBlocks()
    {
        repaint();
    }

    // Sets the title of the window.
    /**
     * Sets the title
     * @param title the title string
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Key Typed
     * @param e stst
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Key released
     * @param e ladjf
     */
    public void keyReleased(KeyEvent e)
    {
    }

    /**
     * The key pressed
     * @param e the key even
     */
    public void keyPressed(KeyEvent e)
    {
        if(!listener.isGameOn())
        {
            new Tetris();
        }
        
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_C && cheat == 0)
            cheat++;
        else if(code == KeyEvent.VK_H && cheat == 1)
            cheat ++;
        else if(code == KeyEvent.VK_E && cheat == 2)
        {
            cheat++;
        }
        else if(code == KeyEvent.VK_A && cheat == 3)
            cheat ++;
        else if(code == KeyEvent.VK_T && cheat == 4)
            cheat ++;
        else if(code == KeyEvent.VK_S && cheat == 5)
        {
            cheatCode = !cheatCode;
            System.out.println("CHEAT");
        }
        else
            cheat = 0;

        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
        else if(code == KeyEvent.VK_P)
            playOn = !playOn;
        else if(code == KeyEvent.VK_Z)
            listener.zPressed();

    }
    
    /**
     * Sets the stuff in display
     * @param level the level being set
     * @param score the score being set
     */
    public void setStuff(int level, int score)
    {
        level2 = level;
        score2 = score;
    }

    /**
     * Gest if should Continue
     * @return true if can; otherwise false
     */
    public boolean getPlay()
    {
        return playOn;
    }

    /**
     * Gets if cheat is on
     * @return true if it is; otherwise false
     */
    public boolean isCheat()
    {
        return cheatCode;
    }

    /**
     * Sets the arrowlistner
     * @param listener2 the listner
     */
    public void setArrowListener(ArrowListener listener2)
    {
        this.listener = listener2;
    }
}
