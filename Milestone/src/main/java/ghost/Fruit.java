package ghost;

import processing.core.PImage;

/**
 * A fruit is an object which must be collected by Waka. When all the fruit on
 * the map is collected, the player wins the game.
 */
public class Fruit extends GameObject {
    private boolean alive;

    public Fruit(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.alive = true;
    }

    /**
     * Check if a player is in the same grid as a fruit. If so, set the alive status
     * to false.
     */
    public void tick() {
        if (getGm().player.getGridX() == getGridX() && getGm().player.getGridY() == getGridY()) {
            this.alive = false;
        }
    }

    /**
     * If the fruit is alive, draw it onto the screen.
     */
    public void draw() {
        if (this.alive) {
            getGm().app.image(getSprite(), getX(), getY());
        }
    }

    /**
     * Count fruit left on the map. If there is one or more left, return true, else
     * return false.
     * 
     * @param gm The game manager where the fruit objects are stored.
     * @return True if there are no fruit left, false if otherwise.
     */
    public static boolean fruitLeft(GameManager gm) {
        int count = 0;
        for (Fruit fruit : gm.fruits) {
            if (fruit.alive == true) {
                count += 1;
            }
        }
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns if the fruit is alive or not
     * 
     * @return True if the fruit is alive, false if not.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets the fruit alive or dead status
     * 
     * @param b If b is true then the fruit is alive, if b is false the fruit is
     *          dead.
     */
    public void setAlive(boolean b) {
        this.alive = b;
    }

    /**
     * Restarts the fruit at the end of a game. Sets its alive status to true.
     */
    public void restartFruit() {
        this.alive = true;
    }

}
