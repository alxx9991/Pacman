package ghost;

import ghost.Ghost.Mode;
import processing.core.PImage;

public class SuperFruit extends Fruit {
    private boolean activated;

    public SuperFruit(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.activated = false;
    }
    
    /**
     * If alive, draw the fruit. If the super fruit is dead and has not been activated before, it will set each ghost to frightened mode, and save the current state of the ghost into its saved mode field. Then it will have been activated, and cannot be activated again.
     */

    public void draw() {
        if (isAlive()) {
            getGm().app.image(getSprite(), getX() - 7, getY() - 7, 30, 30);
        } else {
            if (!this.activated) { //Ensure that the same fruit cannot activate twice
                for (Ghost ghost : getGm().ghosts) {
                    if (ghost.getMode().equals(Mode.Frightened)) {
                        ghost.setFrightenedCount(0);
                    } else {
                        ghost.setSavedMode(ghost.getMode());
                        ghost.setMode(Mode.Frightened);
                    }
                }
                this.activated = true;
            }
        }
    }
    /**
     * Sets the alive status of the fruit back to true, and sets its activated state back to false.
     */
    public void restartFruit() { //Restart fruit when game restarts
        setAlive(true);
        this.activated = false;
    }
    /**
     * Returns if the super fruit has been previously activated or not
     * @return True if the super fruit has been activated before, false if not.
     */
    public boolean isActivated() {
        return this.activated;
    }
    /**
     * Sets if the super fruit has been previously activated or not
     * @param b True if the super fruit has been activated before, false if not.
     */
    public void setActivated(boolean b) {
        this.activated = b;
    }
}
