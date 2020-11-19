package ghost;

import processing.core.PImage;

public class Ignorant extends Ghost {
    public Ignorant(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 0, 576);
    }

    /**
     * Generates the vector for which the ignorant attacks. Directly targets waka if
     * the distance to waka is less than 8 grid spaces, and it is in chase mode.
     * Else, it targets its corner.
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

        // Calculate distance to waka
        double distance = Math.pow(Math.pow((double) vector[0] / 16.0, 2.0) + Math.pow((double) vector[1] / 16.0, 2.0),
                0.5);

        // If in chase mode and distance is less than 8, target corner
        if (getMode().equals(Mode.Chase) && distance <= 8) {
            vector[0] = getCorner()[0] - getX();
            vector[1] = getCorner()[1] - getY();
        }
        return vector;
    }

}
