package ghost;

import processing.core.PImage;

public class Fruit extends GameObject {
    private boolean alive;

    public Fruit(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY);
        this.alive = true;
    }

    public void tick() {
        if (getApp().player.getGridX() == getGridX() && getApp().player.getGridY() == getGridY()) {
            this.alive = false;
        }
    }

    @Override
    public void draw() {
        if (this.alive) {
            getApp().image(getSprite(), getX(), getY());
        }
    }
    
    //Count fruit left on the map
    public static boolean fruitLeft(App app) {
        int count = 0;
        for (Fruit fruit : app.fruits) {
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
