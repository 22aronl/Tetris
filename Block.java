import java.awt.Color;
/**
 * class BLock encapsulates a Block abstraction which can be placed into a Gridworld style grid
 * You are expected to comment this class according to the style guide.
 * @author Aaron Lo
 * @version 3-1-19
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    private String colorString;
    /**
     * constructs a blue block, because blue is the greatest color ever!
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * This gets the color of the block
     * @return color of the block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * This sets the color of the block
     * @param newColor the color block will be set to
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * This gets the grid with blocks
     * @return the grid of blocks
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * This gets the Location of the block
     * @return the location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * This removes itself from the grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        location = null;
        grid = null;
    }

    /**
     * This puts it self into the grid at location
     * @param gr the grid
     * @param loc the location where the block goes
     * 
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        location = loc;
        if(gr.get(loc) !=null)
        {
            Block h = gr.remove(loc);
            h.removeSelfFromGrid();
        }
        grid.put(loc, this);

    }
    
    /**
     * Gets the filename
     * @return the file name of the block
     */
    public String getFileName()
    {
        return "blocks/" + colorString+"block.png";
    }

    /**
     * This moves it self to the new location
     * @param newLocation the location to move to
     */
    public void moveTo(Location newLocation)
    {
        if(location.equals(newLocation))
            return;
        if(grid.get(newLocation) !=null)
        {
            Block h = grid.remove(newLocation);
            h.removeSelfFromGrid();
        }
        grid.remove(location);
        grid.put(newLocation, this);
        location = newLocation;
    }
    
    /**
     * Sets the colorString
     * @param string the string
     */
    public void setColorString(String string)
    {
        colorString = string;
    }

    /**
     * returns a string with the location and color of this block
     * @return the string version of the Block
     */
    @Override
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}