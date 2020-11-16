package ghost;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Ghost extends Movable {
    // Scatter/Chase mode timer
    private Mode mode;
    private int frameCount;
    private int cycleIndex;
    private long cycleLength;
    private long frightenedCount;
    private long frightenedLength;
    private Mode savedMode;
    private boolean alive;

    // Corner target coordinate
    private final int[] corner;

    public Ghost(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY, int cornerX, int cornerY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.alive = true;

        // Modes
        this.mode = Mode.Scatter;
        this.frameCount = 0;
        this.cycleIndex = 0;
        this.cycleLength = getGm().modeLengths.get(cycleIndex) * 60;
        this.frameCount = 0;
        this.frightenedLength = getGm().frightenedLength * 60;

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

public enum Mode {
    Scatter,
    Chase,
    Frightened
}
    public void draw() {
        if (alive) {
            if (this.mode != Mode.Frightened) { 
                getGm().app.image(getSprite(), getX() - 5, getY() - 6);
            } else { //If frightened, draw frightened image
                getGm().app.image(getGm().app.frightenedImage, getX() - 5, getY() - 6);
            }
        }
    }

    public void tick() {
        checkIfFrightened();
        checkIfAlive();
        selectMode();
        if (alive) {
            if (getGm().debug) {
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
        if (this.mode == Mode.Chase) {
            int[] vector = generateVectors(getGm().player.getX(), getGm().player.getY());
            getGm().app.g.line(vector[0] + this.getX(), vector[1] + this.getY(), this.getX(), this.getY());
            getGm().app.g.stroke(126);
        } else if (this.mode == Mode.Scatter) {
            int[] vector = generateVectors(this.corner[0], this.corner[1]);
            getGm().app.g.line(this.getX(), this.getY(), vector[0] + this.getX(), vector[1] + this.getY());
            getGm().app.g.stroke(126);
        } else {
            return;
        }
    }
    
    //Sets the direction of travel based on generation of next move
    public void selectDirection() {
        if (this.mode == Mode.Frightened) {
            setDirection(generateNextMove(0, 0));
            return;
        }
        //If ghost is still, initialise travel direction
        if (getDirection() == Direction.Still) {
            if (this.mode == Mode.Chase) {
                setDirection(generateNextMove(getGm().player.getGridX() * 16, getGm().player.getGridY() * 16));
            } else if (this.mode == Mode.Scatter) {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            }
        }
        //Check if location appropriate for change of direction (intersection)
        if (canChangeDirection()) {
            if (this.mode.equals(Mode.Scatter)) {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            } else if (this.mode.equals(Mode.Chase)) {
                setDirection(generateNextMove(getGm().player.getGridX() * 16, getGm().player.getGridY() * 16));
            }
            if (getDirection() == null) {
                setDirection(Direction.Still);
            }
        }
    }
    
    //Selects mode based on timer and mode lengths configuration
    public void selectMode() {
        if (this.mode != Mode.Frightened) {
            if (this.frameCount < this.cycleLength) {
                this.frameCount++;
            } else {
                this.frameCount = 0;
                if (this.cycleIndex < getGm().modeLengths.size() - 1) {
                    this.cycleIndex++;
                    this.cycleLength = getGm().modeLengths.get(this.cycleIndex) * 60;
                    if (this.cycleIndex % 2 == 1) {
                        this.mode = Mode.Chase;
                    } else {
                        this.mode = Mode.Scatter;
                    }
                } else {
                    this.cycleIndex = 0;
                    this.cycleLength = getGm().modeLengths.get(this.cycleIndex) * 60;
                    this.mode = Mode.Chase;
                }
            }
        }
    }
    
    //Generates next move given a list of preferences for direction of travel
    public Direction generateNextMove(int targetx, int targety) {
        int[] vector = generateVectors(targetx, targety);
        ArrayList<Direction> preferenceList = generatePreferences(getDirection(), vector);
        for (Direction preference : preferenceList) {
            if (canGoDirection(preference)) {
                return preference; // Go through preferences in order, execute first one which is valid
            }
        }
        return preferenceList.get(preferenceList.size() - 1); //Default use last preference
    }
    
    //Check that ghost is at an intersection, and completely within a grid, so it can change direction
    public boolean canChangeDirection() {
        if (this.getX() % 16 == 0 && this.getY() % 16 == 0) {
            if (getDirection() == Direction.Up || getDirection() == Direction.Down) {
                if (!this.wallOnLeft() || !this.wallOnRight() || collideWallAbove() || collideWallBelow()) {
                    return true;
                } else {
                    return false;
                }
            } else if (getDirection() == Direction.Left || getDirection() == Direction.Right) {
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
    
    public boolean canGoDirection(Direction direction) {
        // Determine if can go in a particular direction based on wall locations
        if (direction == Direction.Right) {
            if (wallOnRight()) {
                return false;
            }
            return true;
        } else if (direction == Direction.Left) {
            if (wallOnLeft()) {
                return false;
            } else {
                return true;
            }
        } else if (direction == Direction.Up) {
            if (wallAbove()) {
                return false;
            } else {
                return true;
            }
        } else if (direction == Direction.Down) {
            if (wallBelow()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    public abstract int[] generateVectors(int targetx, int targety);
    
    
    //Generate list of moves based on intended vector in preferred order
    public ArrayList<Direction> generatePreferences(Direction direction, int[] vector) {
        ArrayList<Direction> preferenceList = new ArrayList<Direction>();
        int vectorx = vector[0];
        int vectory = vector[1];

        if (vectorx >= 0 && vectory >= 0) {
            if (vectorx >= vectory) { // want to go right and down
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Left);
            } else if (vectorx <= vectory) { // want to go down and right
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Up);
            }
        } else if (vectorx >= 0 && vectory <= 0) {
            if (vectorx >= -vectory) { // want to go right and up
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Left);
            } else if (vectorx <= -vectory) { // want to go up and right
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Down);
            }
        } else if (vectorx <= 0 && vectory <= 0) {
            if (-vectorx >= -vectory) { // want to go left and up
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Right);
            } else if (-vectorx <= -vectory) { // want to go up and left
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Down);
            }
        } else {
            if (-vectorx >= vectory) { // want to go left and down
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Up);
                preferenceList.add(Direction.Right);
            } else if (-vectorx <= vectory) { // want to go down and left
                preferenceList.add(Direction.Down);
                preferenceList.add(Direction.Left);
                preferenceList.add(Direction.Right);
                preferenceList.add(Direction.Up);
            }
        }

        if (this.mode.equals(Mode.Frightened)) {
            // Randomise preference list if in frightened mode
            Collections.shuffle(preferenceList);
        } 

        // change opposite direction of movement as last preference
        if (direction == Direction.Left) {
            preferenceList.remove(Direction.Right);
            preferenceList.add(Direction.Right);
        } else if (direction == Direction.Right) {
            preferenceList.remove(Direction.Left);
            preferenceList.add(Direction.Left);
        } else if (direction == Direction.Up) {
            preferenceList.remove(Direction.Down);
            preferenceList.add(Direction.Down);
        } else {
            preferenceList.remove(Direction.Up);
            preferenceList.add(Direction.Up);
        }
        
        return preferenceList;
    }
    //Timer for frightened mode
    public void checkIfFrightened() {
        if (this.mode == Mode.Frightened) {
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
        setDirection(Direction.Still);
        setXVel(0);
        setYVel(0);

        // Restart modes
        setMode(Mode.Scatter);
        frightenedCount = 0;
        this.frameCount = 0;
        this.cycleIndex = 0;
        this.cycleLength = getGm().modeLengths.get(cycleIndex) * 60;
        this.frameCount = 0;
        this.frightenedLength = getGm().frightenedLength * 60;

    }
    
    //Getters and setters
    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getSavedMode() {
        return this.savedMode;
    }

    public void setSavedMode(Mode savedMode) {
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
