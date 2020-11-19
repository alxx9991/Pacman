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

    /**
     * <code>enum</code> containing the possible Ghost modes.
     */
    public enum Mode {
        Scatter, Chase, Frightened
    }

    /**
     * Draws the ghost with either the normal sprite or the frightened sprite,
     * depending on the ghost's mode.
     */
    public void draw() {
        if (alive) {
            if (this.mode != Mode.Frightened) {
                getGm().app.image(getSprite(), getX() - 5, getY() - 6);
            } else { // If frightened, draw frightened image
                getGm().app.image(getGm().app.frightenedImage, getX() - 5, getY() - 6);
            }
        }
    }

    /**
     * Runs the ghost's logic. If the ghost is frightened, run the frightened timer.
     * If the ghost is alive, it should be drawn, and if debug mode is on, a line
     * from the ghost to the player should also be drawn. If the ghost is at an
     * intersection, it will recalculate the best direction to travel in to reach
     * its target. It then moves in the said direction, and updates its collision
     * borders.
     */
    public void tick() {
        checkIfFrightened();
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

    /**
     * Sets the collision borders for the ghost depending on the current x and y
     * coordinates.
     */
    public void setCollisionBorders() {
        // Set collision borders
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 23);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 25);
    }

    /**
     * Draws lines to target location from the ghost
     */
    public void drawTargetLine() {
        int[] vector = null;
        if (this.mode == Mode.Chase) {
            vector = generateVectors(getGm().player.getX(), getGm().player.getY());
        } else if (this.mode == Mode.Scatter) {
            vector = generateVectors(this.corner[0], this.corner[1]);
        }
        if (this.mode != Mode.Frightened && getGm().app.g != null) {
            getGm().app.g.line(this.getX(), this.getY(), vector[0] + this.getX(), vector[1] + this.getY());
            getGm().app.g.stroke(126);
        }
    }

    /**
     * Sets the direction of travel based on generation of next move. Passes in the
     * player or the corner coordinates to get this set direction. See functions
     * <code>generateNextMove(int, int)</code> for more information.
     */
    public void selectDirection() {
        if (this.mode == Mode.Frightened) {
            setDirection(generateNextMove(0, 0));
            return;
        }
        // If ghost is still, initialise travel direction
        if (getDirection() == Direction.Still) {
            if (this.mode == Mode.Chase) {
                setDirection(generateNextMove(getGm().player.getGridX() * 16, getGm().player.getGridY() * 16));
            } else {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            }
        }
        // Check if location appropriate for change of direction (intersection)
        if (canChangeDirection()) {
            if (this.mode.equals(Mode.Scatter)) {
                setDirection(generateNextMove(this.corner[0], this.corner[1]));
            } else {
                setDirection(generateNextMove(getGm().player.getGridX() * 16, getGm().player.getGridY() * 16));
            }
        }
    }

    /**
     * Selects mode based on timer and mode lengths configuration. Runs only if the
     * ghost is not in frightened mode.
     */
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

    /**
     * Generates next move given a list of preferences for direction of travel.
     * Please consult the <code>generatePreferences(Direction, int[])</code>
     * function for more information on how preferences are generated.
     * 
     * @param targetx x coordinate of target
     * @param targety y coordinate of target
     * @return Returns the next direction the ghost will move in.
     */
    public Direction generateNextMove(int targetx, int targety) {
        int[] vector = generateVectors(targetx, targety);
        Direction preferredMove = null;
        ArrayList<Direction> preferenceList = generatePreferences(getDirection(), vector);
        for (Direction preference : preferenceList) {
            if (canGoDirection(preference)) {
                preferredMove = preference;
                break;// Go through preferences in order, execute first one which is valid
            }
        }
        return preferredMove; // Default use last preference
    }

    /**
     * Check that ghost is at an intersection, and completely within a grid, so it
     * can change direction.
     * 
     * @return True if the ghost is allowed to change direction, false if not.
     */
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

    /**
     * Determine if can go in a particular direction based on wall locations
     * 
     * @param direction The direction the ghost intends to travel in
     * @return True if the ghost can travel in the direction provided, false if not
     */
    public boolean canGoDirection(Direction direction) {
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
            // throw new RuntimeException();
            if (wallAbove()) {
                return false;
            } else {
                return true;
            }
        } else {
            if (wallBelow()) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Generate the vector to the target location, individual to each ghost type.
     * 
     * @param targetx x coordinate of target
     * @param targety y coordinate of target
     * @return array of two integers, with the first being the horizontal component
     *         of the vector and the second being the vertical component
     */
    public abstract int[] generateVectors(int targetx, int targety);

    /**
     * Generate list of moves based on intended vector in preferred order. Takes
     * into account the ghost's direction and a vector. The vector is generated by
     * the <code>generateVectors</code> function which is unique to each ghost type.
     * If the ghost is in frightened mode, the preference list is shuffled to
     * randomise the movement.
     * 
     * @param Direction The ghost's current direction of travel.
     * @param vector    A two integer array which has a vertical and horizontal
     *                  component aimed at the target direction.
     * @return An array list of directions, with the first index corresponding to
     *         the most preferred direction and the last being the least. The last
     *         vector is always the current direction of travel.
     */
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
            } else { // want to go down and right
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
            } else { // want to go up and right
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
            } else { // want to go up and left
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
            } else { // want to go down and left
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

    /**
     * Timer for frightened mode, if the ghost is in frightened mode. Once the timer
     * exprires, the original mode is restored, and the frightened counter is reset
     * back to zero.
     */
    public void checkIfFrightened() {
        if (this.mode == Mode.Frightened) {
            this.frightenedCount++;
            if (this.frightenedCount == frightenedLength) {
                this.mode = this.savedMode;
                this.frightenedCount = 0;
            }
        }
    }

    /**
     * Restart ghost when the game is restarting. Restores the start coordinate and
     * state, resets the mode counters and mode length pointer.
     */
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

    /**
     * Returns the current mode of the ghost
     * 
     * @return The current mode of the ghost
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Sets the current mode of the ghost
     * 
     * @param mode The mode of the ghost
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets the home corner coordinates of the ghost.
     * 
     * @return The home corner coordinates of the ghost;
     */
    public int[] getCorner() {
        return this.corner;
    }

    /**
     * Returns the alive state of the ghost
     * 
     * @return True if alive, false if dead.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets the alive state of the ghost
     * 
     * @param alive True if the ghost should be alive, false if it should be dead.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Sets the length of the current mode cycle of the ghost
     * 
     * @param cycleLength Length of the current mode cycle
     */
    public void setCycleLength(long cycleLength) {
        this.cycleLength = cycleLength;
    }

    /**
     * Sets the current frame count in a particular mode for a ghost, used in
     * standard scatter and chase modes.
     * 
     * @param frameCount The current frame count for a ghost not in frightened mode.
     */
    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    /**
     * Gets the current frame count in a particular mode for a ghost, used in
     * standard scatter and chase modes.
     * 
     * @return The current frame count for a ghost not in frightened mode.
     */
    public int getFrameCount() {
        return this.frameCount;
    }

    /**
     * Sets the pointer index of the ghost for the array of mode lengths
     * 
     * @param index The pointer index of the ghost for the array of mode lengths
     */
    public void setCycleIndex(int index) {
        this.cycleIndex = index;
    }

    /**
     * Gets the pointer index of the ghost for the array of mode lengths
     * 
     * @return The pointer index of the ghost for the array of mode lengths
     */
    public int getCycleIndex() {
        return this.cycleIndex;
    }

    //Frightened mode getters and setters
    /**
     * Sets the length in frames the ghost is frightened for.
     * 
     * @param length The length of time the ghost is frightened for in frames.
     */
    public void setFrightenedLength(long length) {
        this.frightenedLength = length;
    }

    /**
     * Gets the frightened timer counter number
     * 
     * @return frightenedCount frightened timer number
     */
    public long getFrightenedCount() {
        return this.frightenedCount;
    }

    /**
     * Sets the frightened timer counter number
     * 
     * @param frightenedCount frightened timer number
     */
    public void setFrightenedCount(long frightenedCount) {
        this.frightenedCount = frightenedCount;
    }


    /**
     * Gets the saved mode of the ghost before frightened mode.
     * 
     * @return The saved mode of the ghost before frightened mode.
     */
    public Mode getSavedMode() {
        return this.savedMode;
    }

    /**
     * Sets the saved mode of the ghost before frightened mode.
     * 
     * @param savedMode The saved mode of the ghost before frightened mode.
     */
    public void setSavedMode(Mode savedMode) {
        this.savedMode = savedMode;
    }
}
