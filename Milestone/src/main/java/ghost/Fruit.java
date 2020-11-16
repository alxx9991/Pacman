package ghost;

import processing.core.PImage;

public class Fruit extends GameObject {
    private boolean alive;

    public Fruit(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.alive = true;
    }

    public void tick() {
        if (getGm().player.getGridX() == getGridX() && getGm().player.getGridY() == getGridY()) {
            this.alive = false;
        }
    }

    public void draw() {
        if (this.alive) {
            getGm().app.image(getSprite(), getX(), getY());
        }
    }
    
    //Count fruit left on the map
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

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean b) {
        this.alive = b;
    }

    public void restartFruit() {
        this.alive = true;
    }

}
