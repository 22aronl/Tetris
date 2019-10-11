import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 * The tetrad class is an array of blocks that form up into a 
 *          shape in tetris: i, j, l, t, z, s, or o;
 * 
 * @author Aaron Lo
 * @version 3-6-19
 */
public class Tetrad
{
    // instance variables - replace the example below with your own
    private Block[] self;
    private MyBoundedGrid<Block> grid;
    private String shape;
    private Color color;
    private String colorString;
    private int middle;
    private boolean gameOver;
    private Semaphore lock;

    /**
     * Constructor for objects of class Tetrad
     * @param grid2 the grid
     */
    public Tetrad(MyBoundedGrid<Block> grid2)
    {
        self = new Block[4];
        grid = grid2;
        gameOver = false;
        int i = (int) (Math.random()*7);
        Location[] locs = new Location[4];
        int mid = 10/2;
        lock = new Semaphore(1, true);
        if(i == 0)
        {
            shape = "i";
            color = Color.RED;
            for(int k = 0; k < 4; k++)
            {
                locs[k] = new Location(k, mid);
            }
            middle = 1;
            colorString = "red";
        }
        else if(i == 1)
        {
            shape = "t";
            color = Color.GRAY;
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k, mid);
            }

            locs[2] = new Location(0, mid+1);
            locs[3] = new Location(0, mid-1);
            middle = 0;
            colorString = "gray";
        }
        else if(i == 2)
        {
            color = Color.CYAN;
            shape = "o";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k, mid);
                locs[k+2] = new Location(k, mid+1);
            }
            middle = 1;
            colorString = "cyan";
        }
        else if(i == 3)
        {
            color = Color.YELLOW;
            shape = "l";
            for(int k = 0; k < 3; k++)
            {
                locs[k] = new Location(k, mid);
            }
            locs[3] = new Location(2, mid+1);
            middle = 1;
            colorString = "yellow";
        }
        else if(i == 4)
        {
            color = Color.MAGENTA;
            shape = "j";
            for(int k = 0; k < 3; k++)
            {
                locs[k] = new Location(k, mid);
            }
            locs[3] = new Location(2, mid-1);
            middle = 1;
            colorString = "magenta";
        }
        else if(i == 5)
        {
            color = Color.BLUE;
            shape = "s";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k, mid);
            }
            locs[2] = new Location(1, mid-1);
            locs[3] = new Location(0, mid+1);
            middle = 1;
            colorString = "blue";
        }
        else if(i == 6)
        {
            color = Color.GREEN;
            shape = "z";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k, mid);
            }
            locs[2] = new Location(0, mid-1);
            locs[3] = new Location(1, mid+1);
            middle = 1;
            colorString = "green";
        }

        if(!areEmpty(grid, locs))
        {
            gameOver = true;
        }

        addToLocation(grid, locs);
    }

    /**
     * Constructor for objects of class Tetrad
     * @param grid2 the grid
     * @param dfs int 
     */
    public Tetrad(MyBoundedGrid<Block> grid2, int dfs)
    {
        self = new Block[4];
        grid = grid2;
        gameOver = false;
        int i = (int) (Math.random()*7);
        Location[] locs = new Location[4];
        int mid = 14;
        if(dfs == 10)
            i = 0;
        lock = new Semaphore(1, true);
        if(i == 0)
        {
            shape = "i";
            color = Color.RED;
            for(int k = 0; k < 4; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }
            middle = 1;
            colorString = "red";
        }
        else if(i == 1)
        {
            shape = "t";
            color = Color.GRAY;
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }

            locs[2] = new Location(0 + 2, mid+1);
            locs[3] = new Location(0 + 2, mid-1);
            middle = 0;
            colorString = "gray";
        }
        else if(i == 2)
        {
            color = Color.CYAN;
            shape = "o";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k + 2, mid);
                locs[k+2] = new Location(k + 2, mid+1);
            }
            middle = 1;
            colorString = "cyan";
        }
        else if(i == 3)
        {
            color = Color.YELLOW;
            shape = "l";
            for(int k = 0; k < 3; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }
            locs[3] = new Location(2 + 2, mid+1);
            middle = 1;
            colorString = "yellow";
        }
        else if(i == 4)
        {
            color = Color.MAGENTA;
            shape = "j";
            for(int k = 0; k < 3; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }
            locs[3] = new Location(2 + 2, mid-1);
            middle = 1;
            colorString = "magenta";
        }
        else if(i == 5)
        {
            color = Color.BLUE;
            shape = "s";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }
            locs[2] = new Location(1 + 2, mid-1);
            locs[3] = new Location(0 + 2, mid+1);
            middle = 1;
            colorString = "blue";
        }
        else if(i == 6)
        {
            color = Color.GREEN;
            shape = "z";
            for(int k = 0; k < 2; k++)
            {
                locs[k] = new Location(k + 2, mid);
            }
            locs[2] = new Location(0 + 2, mid-1);
            locs[3] = new Location(1 + 2, mid+1);
            middle = 1;
            colorString = "green";
        }

        if(!areEmpty(grid, locs))
        {
            gameOver = true;
        }

        addToLocation(grid, locs);
    }

    /**
     * This adds the tetrad to the locations
     * @precondition blocks are not in any grid, locs.length = 4
     * @postcondition the locations of blocks match locs, and blocks have been
     *          put in the grid
     * @param grid2 the grid
     * @param locs the location of the blocks
     */
    private void addToLocation(MyBoundedGrid<Block> grid2, Location[] locs)
    {
        for(int i = 0; i < 4; i++)
        {
            Block block = new Block();
            block.setColor(color);
            block.setColorString(colorString);
            block.putSelfInGrid(grid2, locs[i]);
            self[i] = block;
        }
    }

    /**
     * Removes the tetrads
     * @precondition Block are in the grid
     * @postcondition Returns old locations of block ; blocks have been removed from grid
     * @return array of Locations that have been removed from the grid
     */
    private Location[] removeBlocks()
    {
        Location[] loca = new Location[4];
        int index = 0;
        for(int i = 0; i < self.length; i++)
        {
            loca[i] = self[i].getLocation();
            grid.remove(loca[i]);
        }
        return loca;
    }

    /**
     * Checks if the game is Over
     * @return true if it is; otherwise false
     */
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Returns true if the locations are empty
     * @return true if the locations are empty, otherwise false
     * @param grid2 the grid being worked on
     * @param locs an array of locations
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid2, Location[] locs)
    {
        for(int i = 0 ; i < locs.length; i++)
        {
            if(grid2.get(locs[i])!=null)
                return false;
        }
        return true;    
    }

    /**
     * This moves the block to the middle
     */
    public void moveToMiddle()
    {
        Location[] loc = new Location[4];
        for(int i = 0; i < self.length; i++)
        {
            int o = self[i].getLocation().getRow() - 2;
            int j = self[i].getLocation().getCol() - 9;
            loc[i] = new Location(o, j);

        }

        if(!areEmpty(grid, loc))
        {
            gameOver = true;
            removeBlocks();
            addToLocation(grid, loc);
        }
        else
        {
            translate(-2, -9);
        }
    }

    /**
     * Attempts to move the block down and to the right
     * @param deltaRow how far dow it moves
     * @param deltaCol how far to the right it moves
     * @postcondition attempts to move the tetrad deltaRow down 
     *          and deltaCol the right
     * @return true if the move is successfull; otherwise false
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        try
        {
            lock.acquire();
            Location[] loc = new Location[4];
            for(int i = 0; i < self.length; i++)
            {
                int o = self[i].getLocation().getRow() + deltaRow;
                int j = self[i].getLocation().getCol() + deltaCol;
                loc[i] = new Location(o, j);
                if(!(o < grid.getNumRows() && o >= 0 && j < 10 && j >= 0))
                    return false;
            }
            Location[] loca = removeBlocks();
            if(!areEmpty(grid, loc))
            {
                addToLocation(grid, loca);
                return false;
            }
            addToLocation(grid, loc);
            return true;
        }
        catch(InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }
    
    /**
     * Attemps to rotate the block 90 degrees clockwise about its center
     * @postcondtion the tetrad is rotated 90 if the areas are empty
     * @return true if it can rotate; otherwise false
     */
    public boolean rotate()
    {
        if(shape.equals("o"))
            return false;
        try
        {
            lock.acquire();
            Location[] loc = new Location[4];
            int c = self[middle].getLocation().getRow();
            int d = self[middle].getLocation().getCol();
            for(int i = 0; i < self.length; i++)
            {
                int j = c + d - self[i].getLocation().getRow();
                int o = c - d + self[i].getLocation().getCol();
                loc[i] = new Location(o, j);
                if(!(o < grid.getNumRows() && o >= 0 && j < 10 && j >= 0))
                    return false;
            }
            Location[] loca = removeBlocks();
            
            if(!areEmpty(grid, loc))
            {
                addToLocation(grid, loca);
                return false;
            }
            addToLocation(grid, loc);
            return true;
        }
        catch(InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }

    /**
     * Attemps to rotate the block 90 degrees counterclockwise about its center
     * @postcondtion the tetrad is rotated 90 if the areas are empty
     * @return true if it can rotate; otherwise false
     */
    public boolean rotateLeft()
    {
        if(shape.equals("o"))
            return false;
        try
        {
            lock.acquire();
            Location[] loc = new Location[4];
            int c = self[middle].getLocation().getRow();
            int d = self[middle].getLocation().getCol();
            for(int i = 0; i < self.length; i++)
            {
                int j = d - c + self[i].getLocation().getRow();
                int o = c + d - self[i].getLocation().getCol();
                loc[i] = new Location(o, j);
                if(!(o < grid.getNumRows() && o >= 0 && j < 10 && j >= 0))
                    return false;
            }
            Location[] loca = removeBlocks();
            if(!areEmpty(grid, loc))
            {
                addToLocation(grid, loca);
                return false;
            }
            addToLocation(grid, loc);
            return true;
        }
        catch(InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }
}