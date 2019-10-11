import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
/**
 * The Game Tetris
 * 
 * @author Aaron Lo
 * @version 3-6-19
 */
public class Tetris implements ArrowListener
{
    /**
     * The main tester
     * @param args 
     */
    public static void main(String[] args)
    {
        new Tetris();
    }
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    private int rowsNumber;
    private int colsNumber;
    private int score;
    private int level;
    private int rowsCleared;
    private boolean gameOn;
    private Tetrad secondaryTetrad;
    private boolean startCheat;
    private Clip clip;
    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        gameOn = true;
        rowsNumber = 20;
        colsNumber = 10;
        score = 0;
        level = 1;
        rowsCleared = 0;
        grid = new MyBoundedGrid<Block>(rowsNumber, 20);
        display = new BlockDisplay(grid);
        display.setName("Tetris");
        activeTetrad = new Tetrad(grid);
        secondaryTetrad = new Tetrad(grid, 1);
        display.setArrowListener(this); 
        display.showBlocks();
        try
        {
            AudioInputStream audioInputStream = 
                AudioSystem.getAudioInputStream(new File("music.snd"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream); 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
        catch(UnsupportedAudioFileException e)
        {
            //
        }
        catch(IOException e)
        {
            //
        }
        catch(Exception e)
        {
            //
        }
        play();
        //Show Game Over
        endGame();
    }

    /**
     * Ends the game
     */
    public void endGame()
    {
        try
        {
            for(int row = 0; row < 7; row++)
            {
                for(int col = 10; col < 20; col++)
                {
                    grid.remove(new Location(row, col));
                    Thread.sleep(10);
                    display.showBlocks();
                }
            }
            for(int row = 0; row < grid.getNumRows(); row++)
            {
                for(int col = 0; col < 10; col++)
                {
                    grid.remove(new Location(row, col));
                    Thread.sleep(30);
                    display.showBlocks();
                }
            }
        }
        catch(InterruptedException e)
        {
            //

        }
    }

    /**
     * Gets if the game should continue
     * @return true if it is otherwise false
     */
    public boolean isGameOn()
    {
        return gameOn;
    }

    /**
     * This is called when the up arrow is pressed, rotates the block 90 degrees clockwise
     */
    public void upPressed()
    {
        if(display.getPlay() && activeTetrad.rotate())
            display.showBlocks();
    }

    /**
     * This is called when z is pressed, rotates the block 90 degrees counter clockwise
     */
    public void zPressed()
    {
        if(display.getPlay() && activeTetrad.rotateLeft())
            display.showBlocks();
    }

    /**
     * This is called when the left arrow is pressed, moves the block left
     */
    public void leftPressed()
    {
        if(display.getPlay() && activeTetrad.translate(0, -1))
            display.showBlocks();
    }

    /**
     * This is called when the right arrow is pressed, moves the block right
     */
    public void rightPressed()
    {
        if(display.getPlay() && activeTetrad.translate(0, 1))
            display.showBlocks();
    }

    /**
     * This is called when the down arrow is pressed, moves the block down
     */
    public void downPressed()
    {
        if(display.getPlay() && activeTetrad.translate(1, 0))
            display.showBlocks();
    }

    /**
     * Checks if the row is filled
     * @precondition 0 <= row < number of rows
     * @return true if the row is completed; false otherwise
     * @param row the row being checked
     */
    private boolean isCompletedRow(int row)
    {
        for(int i = 0; i < colsNumber; i++)
        {
            if(grid.get(new Location(row, i)) == null)
                return false;
        }
        return true;
    }

    /**
     * Clears the row
     * @precondition 0 <= row < number of rows
     * @postcondition Everyblock in the given row has been 
     *                  removed , and every block above the row has been
     *                  moved down one row
     * @param row to remove the row
     */
    private void clearRow(int row)
    {
        try
        {
            for(int i = 0; i < colsNumber; i++)
            {
                grid.remove(new Location(row, i));
                Thread.sleep(25);
                display.showBlocks();
            }
            for(int i = row - 1; i >= 0; i--)
            {
                for(int j = 0; j < colsNumber; j++)
                {
                    Block block = grid.get(new Location(i, j));
                    if(block != null)
                    {
                        Location locationcv = block.getLocation();
                        block.moveTo(new Location(locationcv.getRow() + 1, locationcv.getCol()));
                    }
                }
            }
        }
        catch(InterruptedException e)
        {
            //
        }
    }

    /**
     * The space is pressed, drops the block all the way down
     */
    public void spacePressed()
    {
        while(display.getPlay() && activeTetrad.translate(1, 0))
        {
        }
    }

    /**
     * All completed rows have been cleared
     * @postcondition all completed rows have been cleared
     */
    private void clearCompletedRows()
    {
        int index = 0;
        for(int i = 0; i < rowsNumber; i++)
        {
            if(isCompletedRow(i))
            {
                index++;
                clearRow(i);
                rowsCleared ++;
            }
        }
        if(index == 1)
            score += 40 * level;
        else if(index == 2)
            score += 100 * level;
        else if(index == 3)
            score += 300 * level;
        else if(index == 4)
            score += 1200 * level;
    }

    /**
     * Plays the game
     */
    public void play()
    {
        while(gameOn)
        {
            try
            {
                display.setStuff(level, score);
                Thread.sleep((1000)/(level));

                if(display.getPlay() && !activeTetrad.translate(1, 0))
                {
                    if(rowsCleared >= 10)
                    {
                        level++;
                        rowsCleared = 0;
                    }

                    clearCompletedRows();
                    activeTetrad = secondaryTetrad;
                    secondaryTetrad.moveToMiddle();
                    if(display.isCheat())
                        secondaryTetrad = new Tetrad(grid, 10);
                    else
                        secondaryTetrad = new Tetrad(grid, 1);

                    if(activeTetrad.isGameOver())
                        gameOn = false;
                }
                display.showBlocks();
            }
            catch(InterruptedException e)
            {
                //
            }
        }
    }

}