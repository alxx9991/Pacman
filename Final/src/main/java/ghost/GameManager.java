package ghost;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import ghost.Movable.Direction;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * The game engine which processes all the logic of the game.
 */
public class GameManager {
    /**
     * The <code>App</code> object running the game.
     */
    protected final App app;
    // Game state
    /**
     * Boolean that toggles debug mode
     */
    protected boolean debug;
    /**
     * Boolean that is true if the game is ended, false if the game is underway.
     */
    protected boolean gameEnded;
    /**
     * The counter for the 10 second timer between games.
     */
    protected int gameEndedCount;
    /**
     * The next move inputted by the player.
     */
    protected Direction nextMove;

    // Config options
    /**
     * The speed of the game. 1 or 2 depending on the config file.
     */
    protected long speed;
    /**
     * The number of lives waka has, specified by the config file.
     */
    protected long lives;
    /**
     * The name of the text file containing the map
     */
    protected String mapFileName;
    /**
     * The array of mode lengths specifying the duration of alternating between
     * scatter and chase mode.
     */
    protected ArrayList<Long> modeLengths;
    /**
     * The length that ghosts are frightened for when the superfruit is collected.
     */
    protected long frightenedLength;

    // Map Elements
    /**
     * An array list of all the objects in the game.
     */
    protected final ArrayList<GameObject> objects;
    /**
     * A list of all the ghosts in the game.
     */
    protected final ArrayList<Ghost> ghosts;
    /**
     * A list of all the fruit in the game.
     */
    protected final ArrayList<Fruit> fruits;
    /**
     * The player object of the game
     */
    protected Pacman player;
    /**
     * A grid of characters that represent the map/
     */
    protected ArrayList<char[]> grid;

    public GameManager(App app) {
        this.app = app;
        objects = new ArrayList<GameObject>();
        modeLengths = new ArrayList<Long>();
        ghosts = new ArrayList<Ghost>();
        fruits = new ArrayList<Fruit>();
        debug = false;
        gameEnded = false;
        gameEndedCount = 0;
    }

    /**
     * Reads configuration file, reads map file and generates all the game objects.
     */
    public void setUp() {
        this.readConfig();
        File file = new File(this.mapFileName);
        Map map = new Map(file, this);
        map.generateObjects();
        for (Ghost ghost : ghosts) {
            if (ghost instanceof Inky) {
                ((Inky) ghost).setChaser();
            }
        }
    }

    /**
     * Runs all the logic in the game. When the game is in progress, it calls the
     * <code>tick()</code> functions of all the game objects and draws all the game
     * objects. If game is won or lost, it displays the win/lose screen and counts
     * 10 seconds before restarting the game.
     */
    public void tick() {
        // Game manager
        if (gameEnded == false) { // Game running
            for (GameObject o : objects) {
                o.tick();
                if (o.getSprite() != null) {
                    o.draw();
                }
            }
            if (player.getLives() == 0 || Fruit.fruitLeft(this) == false) {
                gameEnded = true;
            }
        } else { // In between games
            gameEndedCount++;
            if (app.endScreenFont != null) {
                if (player.getLives() == 0) {
                    app.textFont(app.endScreenFont, 20);
                    app.fill(255);
                    app.text("GAME OVER", 132, 240);
                } else if (Fruit.fruitLeft(this) == false) {
                    app.textFont(app.endScreenFont, 20);
                    app.fill(255);
                    app.text("YOU WIN", 152, 240);
                }
            }
            if (gameEndedCount == 600) {
                restartGame();
            }
        }
    }

    /**
     * Uses <code>JSONParser</code> to parse through the JSON config file, and sets
     * all the variables that the config file changes.
     */
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

    /**
     * Called when the 10 second timer between games expires. Calls restart function
     * of player, ghosts and fruit. Resets the game ended timer and sets
     * <code>gameEnded</code> variable to false.
     */
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

}
