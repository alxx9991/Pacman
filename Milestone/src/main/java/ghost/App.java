package ghost;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class App extends PApplet {
    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;

    // Game state
    protected boolean debug;
    protected boolean gameEnded;
    protected int gameEndedCount;
    protected String nextMove;

    // Config options
    protected long speed;
    protected long lives;
    protected String mapFileName;
    protected ArrayList<Long> modeLengths;
    protected long frightenedLength;

    // Map Elements
    protected ArrayList<GameObject> objects;
    protected ArrayList<Wall> wallList;
    protected ArrayList<Ghost> ghosts;
    protected ArrayList<Fruit> fruits;
    protected int numberOfFruit;
    protected Waka player;
    protected ArrayList<char[]> grid;

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
    protected PFont font;

    public App() {
        objects = new ArrayList<GameObject>();
        wallList = new ArrayList<Wall>();
        modeLengths = new ArrayList<Long>();
        ghosts = new ArrayList<Ghost>();
        fruits = new ArrayList<Fruit>();
        debug = false;
        gameEnded = false;
        gameEndedCount = 0;
    }

    public void setup() {
        frameRate(60);

        // Load config variables
        this.readConfig();

        // Load font
        this.font = createFont("PressStart2P-Regular.ttf", 10);

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

        // Load map and objects
        File file = new File(this.mapFileName);
        Map map = new Map(file, this);
        map.generateObjects();

        for (Ghost ghost : ghosts) {
            if (ghost instanceof Whim) {
                ((Whim) ghost).setChaser();
            }
        }
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() {
        background(0, 0, 0);
        if (gameEnded == false) { // Game running
            player.tick();
            for (Ghost ghost : ghosts) {
                ghost.tick();
            }
            for (Fruit fruit : fruits) {
                fruit.tick();
            }
            for (GameObject o : objects) {
                o.draw();
            }
            if (player.getLives() == 0 || Fruit.fruitLeft(this) == false) {
                gameEnded = true;
            }
        } else { // In between games
            gameEndedCount++;
            if (player.getLives() == 0) {
                textFont(font, 20);
                fill(255);
                text("GAME OVER", 132, 240);
            } else if (Fruit.fruitLeft(this) == false) {
                textFont(font, 20);
                fill(255);
                text("YOU WIN", 152, 240);
            }
            if (gameEndedCount == 600) {
                restartGame();
            }
        }
    }

    // Read config
    public void readConfig() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject configFile = (JSONObject) parser.parse(new FileReader("config.json"));
            this.speed = (long) configFile.get("speed");
            this.lives = (long) configFile.get("lives");
            this.frightenedLength = (long) configFile.get("frightenedLength");
            this.mapFileName = (String) configFile.get("map");
            // JSON Array
            JSONArray modeLengths = (JSONArray) configFile.get("modeLengths");
            Iterator<?> modeLengthsIterator = modeLengths.iterator();
            while (modeLengthsIterator.hasNext()) {
                long length = (Long) (Object) modeLengthsIterator.next();
                this.modeLengths.add(length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Restart game
    public void restartGame() {
        // Restart player
        player.playerRestart();
        // Restart ghosts
        for (Ghost ghost : ghosts) {
            ghost.restartGhost();
        }
        // Restart fruits
        for (Fruit fruit : fruits) {
            fruit.restartFruit();
        }
        // Reset end game count
        gameEndedCount = 0;
        gameEnded = false;
    }

    // Register key presses
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                this.nextMove = "up";
            } else if (keyCode == DOWN) {
                this.nextMove = "down";
            } else if (keyCode == LEFT) {
                this.nextMove = "left";
            } else if (keyCode == RIGHT) {
                this.nextMove = "right";
            }
        } else {
            if (key == ' ') {
                debug = !debug;
            }
        }
    }
    
    // Main
    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
}
