package ghost;

import processing.core.PImage;
/**
 * A type of ghost which targets the grid space 4 units ahead of the direction Waka is travelling in. Its corner is the top right corner.
 */
public class Ambusher extends Ghost {
    public Ambusher(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 448, 0);
    }

    /**
     * Generates the vector for which the ambusher attacks. Adds 64 pixel units to
     * the vector in the direction of Waka's travel if it is in chase mode.
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

        if (getMode().equals(Mode.Chase)) {
            // Add 64 (4 grid spaces) in the player's direction of travel
            if (getGm().player.getDirection() == Direction.Right) {
                vector[0] += 64;
                if (getX() + vector[0] > 448) {
                    vector[0] = 448 - getX();
                }
            } else if (getGm().player.getDirection() == Direction.Left) {
                vector[0] -= 64;
                if (getX() + vector[0] < 0) {
                    vector[0] = -getX();
                }
            } else if (getGm().player.getDirection() == Direction.Up) {
                vector[1] -= 64;
                if (getY() + vector[1] < 0) {
                    vector[1] = -getY();
                }
            } else if ((getGm().player.getDirection() == Direction.Down)) {
                vector[1] += 64;
                if (getY() + vector[1] > 576) {
                    vector[1] = 576 - getY();
                }
            }
        }

        return vector;
    }
}
