package ghost;

public class Wall extends GameObject {

    public Wall(int x, int y, char type, App app, int gridX, int gridY) {
        super(x, y, null, app, gridX, gridY);
        //Choose sprite based on type of wall
        switch (type) {
            case '1':
                setSprite(app.horizontalImage);
                break;
            case '2':
                setSprite(app.verticalImage);
                break;
            case '3':
                setSprite(app.upLeftImage);
                break;
            case '4':
                setSprite(app.upRightImage);
                break;
            case '5':
                setSprite(app.downLeftImage);
                break;
            case '6':
                setSprite(app.downRightImage);
                break;
            default:
                setSprite(null);
                break;
        }
    }
}
