/**
 * DO NOT MODIFY THIS CODE
 * There are no user serviceable components.  Any changes will void your warranty.
 * @author Aaron Lo
 * @version 3-17-19
 */
public interface ArrowListener
{
    /**
     * Called when up is pressed
     */
    void upPressed();

    /**
     * Called when down is pressed
     */
    void downPressed();

    /**
     * Called when left is pressed
     */
    void leftPressed();

    /**
     * Called when right is pressed
     */
    void rightPressed();

    /**
     * Called when space is pressed
     */
    void spacePressed();

    /**
     * Called when z is pressed
     */
    void zPressed();

    /**
     * Called checking if game is over
     * @return true if game is over; otherwise false
     */
    boolean isGameOn();
}