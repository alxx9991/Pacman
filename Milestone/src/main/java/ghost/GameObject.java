package ghost;

import processing.core.PImage;

/**
 * An abstract class which is the superclass that all objects in the game extend
 * from. All game objects have a pixel coordinate, a grid coordinate, a sprite
 * and a game manager. They also must have a draw function to be drawn on the
 * screen, and a tick function to process logic.
 */
public abstract class GameObject {
    private int x;
    private int y;

    private int gridX;
    private int gridY;

    private PImage sprite;
    private final GameManager gm;

    public GameObject(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.gm = gm;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    /**
     * Abstract method which forces each subclass to be able to draw themselves.
     */
    public abstract void draw();

    /**
     * Abstract method which forces each subclass to be able to perform logic for
     * each game frame.
     */
    public abstract void tick();

    /**
     * Returns the x coordinate of the game object.
     * 
     * @return The x coordinate of the game object.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of the game object.
     * 
     * @return The y coordinate of the game object.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the x grid coordinate of the game object.
     * 
     * @return The x grid coordinate of the game object.
     */
    public int getGridX() {
        return this.gridX;
    }

    /**
     * Returns the y grid coordinate of the game object.
     * 
     * @return The y grid coordinate of the game object.
     */
    public int getGridY() {
        return this.gridY;
    }

    /**
     * Returns the sprite of the game object.
     * 
     * @return The sprit3e of the game object.
     */
    public PImage getSprite() {
        return this.sprite;
    }

    /**
     * Returns the game manager of the game object.
     * 
     * @return The game manager of the game object.
     */
    public GameManager getGm() {
        return this.gm;
    }

    /**
     * Sets the x coordinate of the game object.
     * 
     * @param x x coordinate of the game object.
     */
    public void setX(int x) {
        if (x > 448 || x < 0) {
            throw new OutOfMapException();
        } else {
            this.x = x;
        }
    }

    /**
     * Sets the y coordinate of the game object.
     * 
     * @param y y coordinate of the game object.
     */
    public void setY(int y) {
        if (y > 576 || y < 0) {
            throw new OutOfMapException();
        } else {
            this.y = y;
        }
    }

    /**
     * Sets the x grid coordinate of the game object.
     * 
     * @param gridX x grid coordinate of the game object.
     */
    public void setGridX(int gridX) {
        if (gridX > 28 || gridX < 0) {
            throw new OutOfMapException();
        }
        this.gridX = gridX;
    }

    /**
     * Sets the y grid coordinate of the game object.
     * 
     * @param gridY y grid coordinate of the game object.
     */
    public void setGridY(int gridY) {
        if (gridY > 36 || gridY < 0) {
            throw new OutOfMapException();
        }
        this.gridY = gridY;
    }

    /**
     * Sets the sprite of the game object.
     * 
     * @param sprite sprite of the game object.
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
}

/**
 * Exception thrown if a function attempts to set a game object location off the
 * map.
 */
class OutOfMapException extends RuntimeException {
    private static final long serialVersionUID = -1331806908066895592L;

    public OutOfMapException() {
        super("Character out of map bounds");
    }
}
