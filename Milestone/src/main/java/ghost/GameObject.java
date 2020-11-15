package ghost;

import processing.core.PImage;

public class GameObject {
    // Pixel coordinates
    private int x;
    private int y;

    // Grid coordinates
    private int gridX;
    private int gridY;

    private PImage sprite;
    private App app;

    public GameObject(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.app = app;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public void draw() {
        app.image(sprite, x, y);
    }

    // Getters
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getGridX() {
        return this.gridX;
    }

    public int getGridY() {
        return this.gridY;
    }

    public PImage getSprite() {
        return this.sprite;
    }

    public App getApp() {
        return this.app;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

}
