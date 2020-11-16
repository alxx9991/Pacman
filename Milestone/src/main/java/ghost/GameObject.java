package ghost;
import processing.core.PImage;

public abstract class GameObject {
    // Pixel coordinates
    private int x;
    private int y;

    // Grid coordinates
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

    public abstract void draw();
    public abstract void tick();

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

    public GameManager getGm() {
        return this.gm;
    }

    // Setters
    public void setX(int x) {
        if (x > 448 || x < 0) {
            throw new OutOfMapException();
        } else {
            this.x = x;
        }
    }

    public void setY(int y) {
        if (y > 576 || x < 0) {
            throw new OutOfMapException();
        } else {
            this.y = y;
        }
    }

    public void setGridX(int gridX) {
        if (this.gridX > 28 || this.gridX < 0) {
            throw new OutOfMapException();
        }
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        if (this.gridY > 36 || this.gridY < 0) {
            throw new OutOfMapException();
        }
        this.gridY = gridY;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
}

class OutOfMapException extends RuntimeException {
    private static final long serialVersionUID = -1331806908066895592L;

    public OutOfMapException() {
        super("Character out of map bounds");
    }
}
