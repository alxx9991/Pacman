package ghost;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import ghost.Movable.Direction;

/**
 * Subclass of PApplet used to run the game with processing.
 */
public class App extends PApplet {
    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;

    // Game manager
    /**
     * Game engine: handles the game logic.
     */
    protected GameManager gameManager;

    // Wall Images
    protected PImage downLeftImage;
    protected PImage downRightImage;
    protected PImage upLeftImage;
    protected PImage upRightImage;
    protected PImage horizontalImage;
    protected PImage verticalImage;

    // Fruit Image
    protected PImage fruitImage;

    // Waka Images
    protected PImage faceLeftImage;
    protected PImage faceRightImage;
    protected PImage faceUpImage;
    protected PImage faceDownImage;
    protected PImage closedImage;

    // Ghost images
    protected PImage ambusherImage;
    protected PImage chaserImage;
    protected PImage ignorantImage;
    protected PImage whimImage;
    protected PImage frightenedImage;

    // Font
    protected PFont endScreenFont;

    public App() {
        this.gameManager = new GameManager(this);
    }

    /**
     * Load all the images and fonts in the game, and calls the
     * <code>gameManager.setUp()</code> function.
     */
    public void setup() {
        frameRate(60);

        // Load font
        this.endScreenFont = createFont("PressStart2P-Regular.ttf", 10);

        // Load walls
        this.downLeftImage = this.loadImage("downLeft.png");
        this.downRightImage = this.loadImage("downRight.png");
        this.upLeftImage = this.loadImage("upLeft.png");
        this.upRightImage = this.loadImage("upRight.png");
        this.verticalImage = this.loadImage("vertical.png");
        this.horizontalImage = this.loadImage("horizontal.png");

        // Load fruit
        this.fruitImage = this.loadImage("fruit.png");

        // Load waka
        this.faceDownImage = this.loadImage("playerDown.png");
        this.faceUpImage = this.loadImage("playerUp.png");
        this.faceLeftImage = this.loadImage("playerLeft.png");
        this.faceRightImage = this.loadImage("playerRight.png");
        this.closedImage = this.loadImage("playerClosed.png");

        // Load ghost
        this.chaserImage = this.loadImage("chaser.png");
        this.ambusherImage = this.loadImage("ambusher.png");
        this.whimImage = this.loadImage("whim.png");
        this.ignorantImage = this.loadImage("ignorant.png");
        this.frightenedImage = this.loadImage("frightened.png");

        // Set up game with game manager
        gameManager.setUp();

    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Called on every frame of the game. Calls the <code>gameManager.tick()</code>
     * function which runs all the logic for one frame and draws all the objects in
     * one frame.
     */
    public void draw() {
        background(0, 0, 0);
        gameManager.tick();
    }

    /**
     * Register key presses by the player and sends it to the
     * <code>GameManager</code> object.
     */
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                this.gameManager.nextMove = Direction.Up;
            } else if (keyCode == DOWN) {
                this.gameManager.nextMove = Direction.Down;
            } else if (keyCode == LEFT) {
                this.gameManager.nextMove = Direction.Left;
            } else if (keyCode == RIGHT) {
                this.gameManager.nextMove = Direction.Right;
            }
        } else {
            if (key == ' ') {
                gameManager.debug = !gameManager.debug;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
}
