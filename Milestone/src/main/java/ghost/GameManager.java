package ghost;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import ghost.Movable.Direction;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class GameManager {
    protected final App app;
    // Game state
    protected boolean debug;
    protected boolean gameEnded;
    protected int gameEndedCount;
    protected Direction nextMove;

    // Config options
    protected long speed;
    protected long lives;
    protected String mapFileName;
    protected ArrayList<Long> modeLengths;
    protected long frightenedLength;

    // Map Elements
    protected final ArrayList<GameObject> objects;
    protected final ArrayList<Wall> wallList;
    protected final ArrayList<Ghost> ghosts;
    protected final ArrayList<Fruit> fruits;
    protected Waka player;
    protected ArrayList<char[]> grid;

    public GameManager(App app) {
        this.app = app;
        objects = new ArrayList<GameObject>();
        wallList = new ArrayList<Wall>();
        modeLengths = new ArrayList<Long>();
        ghosts = new ArrayList<Ghost>();
        fruits = new ArrayList<Fruit>();
        debug = false;
        gameEnded = false;
        gameEndedCount = 0;
    }

    public void setUp() {
        this.readConfig();
        File file = new File(this.mapFileName);
        Map map = new Map(file, this);
        map.generateObjects();
        for (Ghost ghost : ghosts) {
            if (ghost instanceof Whim) {
                ((Whim) ghost).setChaser();
            }
        }
    }

    public void tick() {
        //Game manager
        if (gameEnded == false) { // Game running
            player.tick();
            for (Ghost ghost : ghosts) {
                ghost.tick();
            }
            for (Fruit fruit : fruits) {
                fruit.tick();
            }
            for (GameObject o : objects) {
                if (o.getSprite() != null) {
                    o.draw();
                }
            }
            if (player.getLives() == 0 || Fruit.fruitLeft(this) == false) {
                gameEnded = true;
            }
        } else { // In between games
            gameEndedCount++;
            if (app.font != null) {
                if (player.getLives() == 0) {
                    app.textFont(app.font, 20);
                    app.fill(255);
                    app.text("GAME OVER", 132, 240);
                } else if (Fruit.fruitLeft(this) == false) {
                    app.textFont(app.font, 20);
                    app.fill(255);
                    app.text("YOU WIN", 152, 240);
                }
            } 
            if (gameEndedCount == 600) {
                restartGame();
            }
        }
    }

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
