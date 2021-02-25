package ghost;

import processing.core.PImage;

/**
 * A type of ghost which directly targets and chases Waka. Its corner is the top
 * left corner.
 */
public class Blinky extends Ghost {
    private boolean hasWhim;

    public Blinky(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 0, 0);
        this.hasWhim = false;
    }

    /**
     * Generates the vector for which the chaser attacks. Directly targets waka.
     * 
     * @param targetx The x coordinate of the target location
     * @param targety The y coordinagte of the target location
     * @return An array of two integers which is the intended direction of travel
     *         for this ghost type.
     */
    public int[] generateVectors(int targetx, int targety) {
        int[] vector = new int[2];
        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();
        return vector;
    }

    /**
     * Set the status of if the chaser has a paired whim.
     * 
     * @param hasWhim Boolean of if the chaser has a paired whim.
     */
    public void setHasWhim(boolean hasWhim) {
        this.hasWhim = hasWhim;
    }

    /**
     * Gets the status of if the chaser has a paired whim.
     * 
     * @return True if the chaser is paired with a whim, false if it is not.
     */
    public boolean hasWhim() {
        return this.hasWhim;
    }
}
