package ghost;

import ghost.Ghost.Mode;
import processing.core.PImage;

public class SuperFruit extends Fruit {
    private boolean activated;

    public SuperFruit(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.activated = false;
    }
    
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
    
    public void restartFruit() { //Restart fruit when game restarts
        setAlive(true);
        this.activated = false;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void setActivated(boolean b) {
        this.activated = b;
    }
}
