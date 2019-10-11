import java.util.*;
/**
 * My bounded Grid creates a 2-D array that contains blocks
 * 
 * @author Aaron Lo
 * @version 3-1-19
 * @param <E> the class that myBounded Grid contains
 */
public class MyBoundedGrid<E>
{
    // instance variables - replace the example below with your own
    private Object[][] list;

    /**
     * Constructor for objects of class MyBoundedGrid and a 2-D array
     * @param row how big the array is
     * @param col how big the array in the array is
     */
    public MyBoundedGrid(int row, int col)
    {
        // initialise instance variables
        list = new Object[row][col];
    }
    /**
     * Gets how many rows are ing the array
     * @return the number of rows
     */
    public int getNumRows()
    {
        return list.length;
    }
    /**
     * Gets how many columns are in  the array
     * @return the number of cols
     */
    public int getNumCols()
    {
        return list[0].length;
    }
    /**
     * Checks if the location is valid
     * @param loc this checks if the location is valid
     * @return true if the location is valid; otherwise false
     */
    public boolean isValid(Location loc)
    {
        if(loc.getRow() < 0 || loc.getCol() < 0)
            return false;
        return (list.length - loc.getRow() > 0) && (list[0].length - loc.getCol() > 0);
    }
    /**
     * Puts Object into the location. If the location is occupied, then it returns 
     *      what was orrignallay in that spot
     * @param loc the location
     * @param obj the object being placed
     * @return null if loc is empty, otherwise, what was in the loc
     */
    public E put(Location loc, E obj)
    {
        if(list[loc.getRow()][loc.getCol()] == null)
        {
            list[loc.getRow()][loc.getCol()] = obj;
            return null;
        }
        E objs = (E)list[loc.getRow()][loc.getCol()];
        list[loc.getRow()][loc.getCol()] = obj;
        return objs;
    }
    /**
     * This removes what ever is in the location
     * @param loc the location
     * @return what was in the location; otherwise null
     */
    public E remove(Location loc)
    {
        E obj = (E)list[loc.getRow()][loc.getCol()];
        list[loc.getRow()][loc.getCol()] = null;
        return obj;
    }
    /**
     * Gets what is at location
     * @param loc the location
     * @return what is at the location
     */
    public E get(Location loc)
    {
        return (E)list[loc.getRow()][loc.getCol()];
    }
    /**
     * This return the occupied locations of locations that are occupied
     * @return an arraylist of Locations of things that are occupied
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> r = new ArrayList<Location>();
        for(int i = 0; i < list.length; i++)
        {
            for(int j = 0; j < list[i].length; j++)
            {
                if(list[i][j] !=null)
                {
                    r.add(new Location(i, j));
                }
            }
        }
        return r;
    }
}
