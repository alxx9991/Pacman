package ghost;

import processing.core.PImage;

public class SuperFruit extends Fruit {
    private boolean activated;

    public SuperFruit(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY);
        this.activated = false;
    }

    @Override
    public void draw() {
        if (isAlive()) {
            getApp().image(getSprite(), getX() - 7, getY() - 7, 30, 30);
        } else {
            if (!this.activated) { //Ensure that the same fruit cannot activate twice
                for (Ghost ghost : getApp().ghosts) {
                    if (ghost.getMode().equals("frightened")) {
                        ghost.setFrightenedCount(0);
                    } else {
                        ghost.setSavedMode(ghost.getMode());
                        ghost.setMode("frightened");
                    }
                }
                this.activated = true;
            }
        }
    }

    @Override public void restartFruit() { //Restart fruit when game restarts
        setAlive(true);
        this.activated = false;
    }
}
