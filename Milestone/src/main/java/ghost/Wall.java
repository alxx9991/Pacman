package ghost;

/**
 * A wall object which prevents Waka and ghosts from passing through. There are
 * different types of walls which have different sprites, such as vertical
 * walls, top left corner walls and so on.
 */
public class Wall extends GameObject {

    public Wall(int x, int y, char type, GameManager gm, int gridX, int gridY) {
        super(x, y, null, gm, gridX, gridY);
        // Choose sprite based on type of wall
        switch (type) {
            case '1':
                setSprite(gm.app.horizontalImage);
                break;
            case '2':
                setSprite(gm.app.verticalImage);
                break;
            case '3':
                setSprite(gm.app.upLeftImage);
                break;
            case '4':
                setSprite(gm.app.upRightImage);
                break;
            case '5':
                setSprite(gm.app.downLeftImage);
                break;
            case '6':
                setSprite(gm.app.downRightImage);
                break;
            default:
                setSprite(null);
                break;
        }
    }

    public void draw() {
        getGm().app.image(getSprite(), getX(), getY());
    }

    public void tick() {
        ;
    }
}
