package ghost;

import processing.core.PImage;

public abstract class Movable extends GameObject {
    // Movement characteristics
    private Direction direction;
    private long xVel;
    private long yVel;
    private long speed;

    // Collision borders
    private int borderTop;
    private int borderBot;
    private int borderLeft;
    private int borderRight;

    // Starting position in pixels
    private final int startX;
    private final int startY;

    public Movable(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.direction = Direction.Still;
        this.xVel = 0;
        this.yVel = 0;
        this.speed = gm.speed;
        this.startX = x;
        this.startY = y;
    }

    /**
     * <code>enum</code> containing all the possible directions that a movable
     * object could be travelling in.
     */
    public enum Direction {
        Left, Right, Up, Down, Still
    }

    /**
     * Takes into account a players direction, and changes their x and y velocity to
     * make them move in a particular direction. Changes their x and y velocity
     * using their speed which is read in the config file.
     */
    public void move() {
        // Set velocity
        if (this.direction == Direction.Left) {
            this.xVel = -this.speed;
            this.yVel = 0;
        } else if (this.direction == Direction.Right) {
            this.xVel = this.speed;
            this.yVel = 0;
        } else if (this.direction == Direction.Down) {
            this.xVel = 0;
            this.yVel = this.speed;
        } else if (this.direction == Direction.Up) {
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

    /**
     * Check for a wall on players right
     * 
     * @return True if there is a wall on the movable object's right, false if there is not.
     */
    public boolean wallOnRight() {
        if (getGm().grid.get(getGridY())[getGridX() + 1] == '1' || getGm().grid.get(getGridY())[getGridX() + 1] == '2'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '3'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '4'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '5'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check for a wall on players left
     * 
     * @return True if there is a wall on the movable object's left, false if there is not.
     */
    public boolean wallOnLeft() {
        if (getGridX() == 0) {
            return true;
        }
        if (getGm().grid.get(getGridY())[getGridX() - 1] == '1' || getGm().grid.get(getGridY())[getGridX() - 1] == '2'
                || getGm().grid.get(getGridY())[getGridX() - 1] == '3'
                || getGm().grid.get(getGridY())[getGridX() - 1] == '4'
                || getGm().grid.get(getGridY())[getGridX() - 1] == '5'
                || getGm().grid.get(getGridY())[getGridX() - 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check for a wall above player
     * 
     * @return True if there is a wall above the player, false if there is not.
     */
    public boolean wallAbove() {
        if (getGridY() == 0) {
            return true;
        }
        if (getGm().grid.get(getGridY() - 1)[getGridX()] == '1' || getGm().grid.get(getGridY() - 1)[getGridX()] == '2'
                || getGm().grid.get(getGridY() - 1)[getGridX()] == '3'
                || getGm().grid.get(getGridY() - 1)[getGridX()] == '4'
                || getGm().grid.get(getGridY() - 1)[getGridX()] == '5'
                || getGm().grid.get(getGridY() - 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check for a wall below the player
     * 
     * @return True if there is a wall above the player, false if there is not.
     */
    public boolean wallBelow() {
        if (getGm().grid.get(getGridY() + 1)[getGridX()] == '1' || getGm().grid.get(getGridY() + 1)[getGridX()] == '2'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '3'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '4'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '5'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a player will collide with a wall on the right
     * 
     * @return True if player will collide with a wall on the right, false if player
     *         will not.
     */
    public boolean collideWallOnRight() {
        if (getGm().grid.get(getGridY())[getGridX() + 1] == '1' || getGm().grid.get(getGridY())[getGridX() + 1] == '2'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '3'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '4'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '5'
                || getGm().grid.get(getGridY())[getGridX() + 1] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a player will collide with a wall above
     * 
     * @return True if player will collide with a wall above, false if player will
     *         not.
     */
    public boolean collideWallAbove() {
        if (getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '1'
                || getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '2'
                || getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '3'
                || getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '4'
                || getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '5'
                || getGm().grid.get((int) Math.floor((getY() - 1) / 16))[getGridX()] == '6') {
            return true;
        } else
            return false;
    }

    /**
     * Check if a player will collide with a wall on the left
     * 
     * @return True if player will collide with a wall on the left, false if player
     *         will not.
     */
    public boolean collideWallOnLeft() {
        if (getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '1'
                || getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '2'
                || getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '3'
                || getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '4'
                || getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '5'
                || getGm().grid.get(getGridY())[(int) Math.floor((getX() - 1) / 16)] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a player will collide with a wall below
     * 
     * @return True if player will collide with a wall below, false if player will
     *         not.
     */
    public boolean collideWallBelow() {
        if (getGm().grid.get(getGridY() + 1)[getGridX()] == '1' || getGm().grid.get(getGridY() + 1)[getGridX()] == '2'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '3'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '4'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '5'
                || getGm().grid.get(getGridY() + 1)[getGridX()] == '6') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the movable object's current direction
     * 
     * @return The movable object's current direction.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Returns the movable object's horizontal velocity
     * 
     * @return The movable object's horizontal velocity.
     */
    public long getXVel() {
        return this.xVel;
    }

    /**
     * Returns the movable object's vertical velocity
     * 
     * @return The movable object's vertical velocity.
     */
    public long getYVel() {
        return this.yVel;
    }

    /**
     * Returns the movable object's speed as specified by the config file.
     * 
     * @return The movable object's speed.
     */
    public long getSpeed() {
        return this.speed;
    }

    /**
     * Returns the movable object's top collision border
     * 
     * @return The movable object's top collision border.
     */
    public int getBorderTop() {
        return this.borderTop;
    }

    /**
     * Returns the movable object's bottom collision border
     * 
     * @return The movable object's bottom collision border.
     */
    public int getBorderBot() {
        return this.borderBot;
    }

    /**
     * Returns the movable object's left collision border
     * 
     * @return The movable object's left collision border.
     */
    public int getBorderLeft() {
        return this.borderLeft;
    }

    /**
     * Returns the movable object's right collision border
     * 
     * @return The movable object's right collision border.
     */
    public int getBorderRight() {
        return this.borderRight;
    }
    /**
     * Returns the movable object's starting x coordinate
     * 
     * @return The movable object's starting x coordinate.
     */
    public int getStartX() {
        return this.startX;
    }
    /**
     * Returns the movable object's starting y coordinate
     * 
     * @return The movable object's starting y coordinate.
     */
    public int getStartY() {
        return this.startY;
    }

    /**
     * Sets the movable object's current direction.
     * @param direction Direction of travel.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    /**
     * Sets the movable object's current horizontal velocity.
     * @param xVel Horizontal velocity.
     */
    public void setXVel(long xVel) {
        this.xVel = xVel;
    }
    /**
     * Sets the movable object's current vertical velocity.
     * @param yVel Vertical velocity.
     */
    public void setYVel(long yVel) {
        this.yVel = yVel;
    }
    /**
     * Sets the movable object's top border.
     * @param borderTop Movable object's top border.
     */
    public void setBorderTop(int borderTop) {
        this.borderTop = borderTop;
    }
    /**
     * Sets the movable object's bot border.
     * @param borderBot Movable object's bot border.
     */
    public void setBorderBot(int borderBot) {
        this.borderBot = borderBot;
    }
    /**
     * Sets the movable object's left border.
     * @param borderLeft Movable object's left border.
     */
    public void setBorderLeft(int borderLeft) {
        this.borderLeft = borderLeft;
    }
    /**
     * Sets the movable object's right border.
     * @param borderRight Movable object's right border.
     */
    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
    }

}
