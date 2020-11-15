package ghost;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;

public class Ghost extends Movable {
    // Scatter/Chase mode timer
    private String mode;
    private int frameCount;
    private int cycleIndex;
    private long cycleLength;
    private long frightenedCount;
    private long frightenedLength;
    private String savedMode;
    private boolean alive;

    // Corner target coordinate
    private int[] corner;

    public Ghost(int x, int y, PImage sprite, App app, int gridX, int gridY, int cornerX, int cornerY) {
        super(x, y, sprite, app, gridX, gridY);
        this.alive = true;

        // Modes
        this.mode = "scatter";
        this.frameCount = 0;
        this.cycleIndex = 0;
        this.cycleLength = getApp().modeLengths.get(cycleIndex) * 60;
        this.frameCount = 0;
        this.frightenedLength = getApp().frightenedLength * 60;

        // Collision
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 23);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 25);

        // Set corner
        this.corner = new int[2];
        this.corner[0] = cornerX;
        this.corner[1] = cornerY;

    }

    @Override
    public void draw() {
        if (this.mode != "frightened") { 
            getApp().image(getSprite(), getX() - 5, getY() - 6);
        } else { //If frightened, draw frightened image
            getApp().image(getApp().frightenedImage, getX() - 5, getY() - 6);
        }
    }

    public void tick() {
        checkIfFrightened();
        checkIfAlive();
        selectMode();
        if (alive) {
            if (getApp().debug) {
                drawTargetLine();
            }
            if (canChangeDirection()) {
                selectDirection();
            }
            move();
            setCollisionBorders();
        }
    }
    
    public void setCollisionBorders() {
        // Set collision borders
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 23);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 25);
    }
    
    //Draws lines to target location
    public void drawTargetLine() {
        if (this.mode == "chase") {
            int[] vector = generateVectors(getApp().player.getX(), getApp().player.getY());
            getApp().g.line(vector[0] + this.getX(), vector[1] + this.getY(), this.getX(), this.getY());
            getApp().g.stroke(126);
        } else if (this.mode == "scatter") {
            int[] vector = generateVectors(this.corner[0], this.corner[1]);
            getApp().g.line(this.getX(), this.getY(), vector[0] + this.getX(), vector[1] + this.getY());
            getApp().g.stroke(126);
        } else {
            return;
        }
    }
    
    //Sets the direction of travel based on generation of next move
    public void selectDirection() {
        if (this.mode == "frightened") {
            setDirection(generateNextMove(0, 0));
            return;
        }
        //If ghost is still, initialise travel direction
        if (getDirection() == "still") {
            if (this.mode == "chase") {
                setDirection(generateNextMove(getApp().player.getGridX() * 16, getApp().player.getGridY() * 16));
            } else if (this.mode == "scatter") {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            }
        }
        //Check if location appropriate for change of direction (intersection)
        if (canChangeDirection()) {
            if (this.mode.equals("scatter")) {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            } else if (this.mode.equals("chase")) {
                setDirection(generateNextMove(getApp().player.getGridX() * 16, getApp().player.getGridY() * 16));
            }
            if (getDirection() == null) {
                setDirection("still");
            }
        }
    }
    
    //Selects mode based on timer and mode lengths configuration
    public void selectMode() {
        if (this.mode != "frightened") {
            if (this.frameCount < this.cycleLength) {
                this.frameCount++;
            } else {
                this.frameCount = 0;
                if (this.cycleIndex < getApp().modeLengths.size() - 1) {
                    this.cycleIndex++;
                    this.cycleLength = getApp().modeLengths.get(this.cycleIndex) * 60;
                    if (this.cycleIndex % 2 == 1) {
                        this.mode = "chase";
                    } else {
                        this.mode = "scatter";
                    }
                } else {
                    this.cycleIndex = 0;
                    this.cycleLength = getApp().modeLengths.get(this.cycleIndex) * 60;
                    this.mode = "chase";
                }
            }
        }
    }
    
    //Generates next move given a list of preferences for direction of travel
    public String generateNextMove(int targetx, int targety) {
        int[] vector = generateVectors(targetx, targety);
        ArrayList<String> preferenceList = generatePreferences(getDirection(), vector);
        for (String preference : preferenceList) {
            if (canGoDirection(preference)) {
                return preference; // Go through preferences in order, execute first one which is valid
            }
        }
        return preferenceList.get(preferenceList.size() - 1); //Default use last preference
    }
    
    //Check that ghost is at an intersection, and completely within a grid, so it can change direction
    public boolean canChangeDirection() {
        if (this.getX() % 16 == 0 && this.getY() % 16 == 0) {
            if (getDirection() == "up" || getDirection() == "down") {
                if (!this.wallOnLeft() || !this.wallOnRight() || collideWallAbove() || collideWallBelow()) {
                    return true;
                } else {
                    return false;
                }
            } else if (getDirection() == "left" || getDirection() == "right") {
                if (!this.wallBelow() || !this.wallAbove() || collideWallOnLeft() || collideWallOnRight()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    public boolean canGoDirection(String direction) {
        // Determine if can go in a particular direction based on wall locations
        if (direction == "right") {
            if (wallOnRight()) {
                return false;
            }
            return true;
        } else if (direction == "left") {
            if (wallOnLeft()) {
                return false;
            } else {
                return true;
            }
        } else if (direction == "up") {
            if (wallAbove()) {
                return false;
            } else {
                return true;
            }
        } else if (direction == "down") {
            if (wallBelow()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    //Default generate vector to travel to location
    public int[] generateVectors(int targetx, int targety) {
        int[] vector = new int[2];
        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();
        return vector;
    }
    
    //Generate list of moves based on intended vector in preferred order
    public ArrayList<String> generatePreferences(String direction, int[] vector) {
        ArrayList<String> preferenceList = new ArrayList<String>();
        int vectorx = vector[0];
        int vectory = vector[1];

        if (vectorx >= 0 && vectory >= 0) {
            if (vectorx >= vectory) { // want to go right and down
                preferenceList.add("right");
                preferenceList.add("down");
                preferenceList.add("up");
                preferenceList.add("left");
            } else if (vectorx <= vectory) { // want to go down and right
                preferenceList.add("down");
                preferenceList.add("right");
                preferenceList.add("left");
                preferenceList.add("up");
            }
        } else if (vectorx >= 0 && vectory <= 0) {
            if (vectorx >= -vectory) { // want to go right and up
                preferenceList.add("right");
                preferenceList.add("up");
                preferenceList.add("down");
                preferenceList.add("left");
            } else if (vectorx <= -vectory) { // want to go up and right
                preferenceList.add("up");
                preferenceList.add("right");
                preferenceList.add("left");
                preferenceList.add("down");
            }
        } else if (vectorx <= 0 && vectory <= 0) {
            if (-vectorx >= -vectory) { // want to go left and up
                preferenceList.add("left");
                preferenceList.add("up");
                preferenceList.add("down");
                preferenceList.add("right");
            } else if (-vectorx <= -vectory) { // want to go up and left
                preferenceList.add("up");
                preferenceList.add("left");
                preferenceList.add("right");
                preferenceList.add("down");
            }
        } else {
            if (-vectorx >= vectory) { // want to go left and down
                preferenceList.add("left");
                preferenceList.add("down");
                preferenceList.add("up");
                preferenceList.add("right");
            } else if (-vectorx <= vectory) { // want to go down and left
                preferenceList.add("down");
                preferenceList.add("left");
                preferenceList.add("right");
                preferenceList.add("up");
            }
        }

        if (this.mode.equals("frightened")) {
            // Randomise preference list if in frightened mode
            Collections.shuffle(preferenceList);
        } 

        // change opposite direction of movement as last preference
        if (direction == "left") {
            preferenceList.remove("right");
            preferenceList.add("right");
        } else if (direction == "right") {
            preferenceList.remove("left");
            preferenceList.add("left");
        } else if (direction == "up") {
            preferenceList.remove("down");
            preferenceList.add("down");
        } else {
            preferenceList.remove("up");
            preferenceList.add("up");
        }
        
        return preferenceList;
    }
    //Timer for frightened mode
    public void checkIfFrightened() {
        if (this.mode == "frightened") {
            this.frightenedCount++;
            if (this.frightenedCount == frightenedLength) {
                this.mode = this.savedMode;
                this.frightenedCount = 0;
            }
        }
    }
    //If dead, move ghost location off map
    public void checkIfAlive() {
        if (!this.alive) {
            setX(-1000);
            setY(-1000);
            setCollisionBorders();
        }
    }

    //Restart ghost from start of game
    public void restartGhost() {
        setAlive(true);

        // Restart coordinates
        setX(getStartX());
        setY(getStartY());
        setGridX((int) Math.floor(getX() / 16));
        setGridY((int) Math.floor(getY() / 16));
        setCollisionBorders();

        // Restart direction
        setDirection("still");
        setXVel(0);
        setYVel(0);

        // Restart modes
        setMode("scatter");
        frightenedCount = 0;
        this.frameCount = 0;
        this.cycleIndex = 0;
        this.cycleLength = getApp().modeLengths.get(cycleIndex) * 60;
        this.frameCount = 0;
        this.frightenedLength = getApp().frightenedLength * 60;

    }
    
    //Getters and setters
    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSavedMode() {
        return this.savedMode;
    }

    public void setSavedMode(String savedMode) {
        this.savedMode = savedMode;
    }

    public int[] getCorner() {
        return this.corner;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setFrightenedCount(long frightenedCount) {
        this.frightenedCount = frightenedCount;
    }
}
