package ghost;

import processing.core.PImage;

public class Movable extends GameObject {
    // Movement characteristics
    private String direction;
    private long xVel;
    private long yVel;
    private long speed;

    // Collision borders
    private int borderTop;
    private int borderBot;
    private int borderLeft;
    private int borderRight;

    // Starting position in pixels
    private int startX;
    private int startY;

    public Movable(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY);
        this.direction = "still";
        this.xVel = 0;
        this.yVel = 0;
        this.speed = app.speed;
        this.startX = x;
        this.startY = y;
    }

    public void move() {
        // Set velocity
        if (this.direction.equals("left")) {
            this.xVel = -this.speed;
            this.yVel = 0;
        } else if (this.direction.equals("right")) {
            this.xVel = this.speed;
            this.yVel = 0;
        } else if (this.direction.equals("down")) {
            this.xVel = 0;
            this.yVel = this.speed;
        } else if (this.direction.equals("up")) {
            this.xVel = 0;
            this.yVel = -this.speed;
        } else {
            this.xVel = 0;
            this.yVel = 0;
        }

        // Move pixel coordinate
        setX((int) (getX() + xVel));
        setY((int) (getY() + yVel));

        // Set grid coordinates
        setGridX((int) Math.floor(getX() / 16));
        setGridY((int) Math.floor(getY() / 16));

    }

    // Checks walls around player at any location
    public boolean wallOnRight() {
        if (getApp().grid.get(getGridY())[getGridX() + 1] == '1' || getApp().grid.get(getGridY())[getGridX() + 1] == '2'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '3'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '4'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '5'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    public boolean wallOnLeft() {
        if (getGridX() == 0) {
            return true;
        }
        if (getApp().grid.get(getGridY())[getGridX() - 1] == '1' || getApp().grid.get(getGridY())[getGridX() - 1] == '2'
                || getApp().grid.get(getGridY())[getGridX() - 1] == '3'
                || getApp().grid.get(getGridY())[getGridX() - 1] == '4'
                || getApp().grid.get(getGridY())[getGridX() - 1] == '5'
                || getApp().grid.get(getGridY())[getGridX() - 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    public boolean wallAbove() {
        if (getGridY() == 0) {
            return true;
        }
        if (getApp().grid.get(getGridY() - 1)[getGridX()] == '1' || getApp().grid.get(getGridY() - 1)[getGridX()] == '2'
                || getApp().grid.get(getGridY() - 1)[getGridX()] == '3'
                || getApp().grid.get(getGridY() - 1)[getGridX()] == '4'
                || getApp().grid.get(getGridY() - 1)[getGridX()] == '5'
                || getApp().grid.get(getGridY() - 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    public boolean wallBelow() {
        if (getApp().grid.get(getGridY() + 1)[getGridX()] == '1' || getApp().grid.get(getGridY() + 1)[getGridX()] == '2'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '3'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '4'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '5'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    // Checks used when moving towards a wall
    public boolean collideWallOnRight() {
        if (getApp().grid.get(getGridY())[getGridX() + 1] == '1' || getApp().grid.get(getGridY())[getGridX() + 1] == '2'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '3'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '4'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '5'
                || getApp().grid.get(getGridY())[getGridX() + 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    public boolean collideWallAbove() {
        if (getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '1'
                || getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '2'
                || getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '3'
                || getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '4'
                || getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '5'
                || getApp().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '6') {
            return true;
        } else
            return false;
    }

    public boolean collideWallOnLeft() {
        if (getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '1'
                || getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '2'
                || getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '3'
                || getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '4'
                || getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '5'
                || getApp().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '6') {
            return true;
        } else {
            return false;
        }
    }

    public boolean collideWallBelow() {
        if (getApp().grid.get(getGridY() + 1)[getGridX()] == '1' || getApp().grid.get(getGridY() + 1)[getGridX()] == '2'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '3'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '4'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '5'
                || getApp().grid.get(getGridY() + 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    // Getters
    public String getDirection() {
        return this.direction;
    }

    public long getXVel() {
        return this.xVel;
    }

    public long getYVel() {
        return this.yVel;
    }

    public long getSpeed() {
        return this.speed;
    }

    public int getBorderTop() {
        return this.borderTop;
    }

    public int getBorderBot() {
        return this.borderBot;
    }

    public int getBorderLeft() {
        return this.borderLeft;
    }

    public int getBorderRight() {
        return this.borderRight;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    // Setters
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setXVel(long xVel) {
        this.xVel = xVel;
    }

    public void setYVel(long yVel) {
        this.yVel = yVel;
    }

    public void setBorderTop(int borderTop) {
        this.borderTop = borderTop;
    }

    public void setBorderBot(int borderBot) {
        this.borderBot = borderBot;
    }

    public void setBorderLeft(int borderLeft) {
        this.borderLeft = borderLeft;
    }

    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
    }

}
