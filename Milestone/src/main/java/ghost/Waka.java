package ghost;

import ghost.Ghost.Mode;
import processing.core.PImage;

/**
 * The main player of the game. Attempts to eat all the fruit without colliding
 * with ghosts. Moves at the same speed as ghosts. Has a number of lives set by
 * the config file. If all the lives are lost, the game is over.
 */
public class Waka extends Movable {
    private int frameCycle;
    private final PImage closedSprite;
    boolean alive;
    private long lives;

    public Waka(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY);
        this.frameCycle = 0;
        this.closedSprite = getGm().app.closedImage;
        setDirection(Direction.Still);
        this.alive = true;
        this.lives = getGm().lives;
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 22);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 21);
    }

    /**
     * Logic performed in each frame: Selects a direction based on the player input
     * and waka position and direction. Moves in said direction. Update collision
     * borders, then check if there is a ghost collision. If the player has been
     * killed by a ghost, call the <code>resetIfDead()</code> method which resets
     * the player and ghosts back to starting position.
     */
    public void tick() {
        selectDirection();
        move();
        setCollisionBorders();
        checkGhostCollision();
        resetIfDead();
    }

    /**
     * Reset waka and ghost locations if dead, and remove a life
     */
    public void resetIfDead() {
        if (this.alive == false) {
            this.lives -= 1;
            setX(getStartX());
            setY(getStartY());
            setDirection(Direction.Still);
            setGridX((int) Math.floor(getX() / 16));
            setGridY((int) Math.floor(getY() / 16));
            setCollisionBorders();
            setXVel(0);
            setYVel(0);
            this.alive = true;

            for (Ghost ghost : getGm().ghosts) {
                ghost.setAlive(true);
                ghost.setX(ghost.getStartX());
                ghost.setY(ghost.getStartY());
                ghost.setGridX((int) Math.floor(ghost.getX() / 16));
                ghost.setGridY((int) Math.floor(ghost.getY() / 16));
                ghost.setCollisionBorders();
                ghost.setDirection(Direction.Still);
                ghost.setXVel(0);
                ghost.setYVel(0);

            }

        }
    }

    /**
     * Set collision boundaries
     */
    public void setCollisionBorders() {
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 22);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 21);
    }

    /**
     * Choose direction based on the player's input, and the
     */
    public void selectDirection() {
        // If player is still, select direction based on choice
        if (getDirection().equals(Direction.Still)) {
            if (getGm().nextMove == null) {
                return;
            }
            if (getGm().nextMove.equals(Direction.Left)) {
                if (!wallOnLeft()) {
                    setDirection(Direction.Left);
                } else {
                    getGm().nextMove = null;
                }
            } else if (getGm().nextMove.equals(Direction.Right)) {
                if (!wallOnRight()) {
                    setDirection(Direction.Right);
                } else {
                    getGm().nextMove = null;
                }
            } else if (getGm().nextMove.equals(Direction.Down)) {
                if (!wallBelow()) {
                    setDirection(Direction.Down);
                } else {
                    getGm().nextMove = null;
                }
            } else {
                if (!wallAbove()) {
                    setDirection(Direction.Up);
                } else {
                    getGm().nextMove = null;
                }
            }
            return;
        }

        // If player is currently moving, select direction based on available choices
        // and desired direction
        if (getGm().nextMove == null) {
            checkWallCollision();
        } else if (getDirection().equals(Direction.Right)) {
            if (getGm().nextMove.equals(Direction.Left)) {
                checkWallCollision();
                setDirection(Direction.Left);
                getGm().nextMove = null;

            } else if (getGm().nextMove.equals(Direction.Up)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallAbove() == false) {
                        setDirection(Direction.Up);
                        getGm().nextMove = null;
                    }
                }
            } else if (getGm().nextMove.equals(Direction.Down)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallBelow() == false) {
                        setDirection(Direction.Down);
                        getGm().nextMove = null;
                    }
                }
            } else {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals(Direction.Left)) {
            if (getGm().nextMove.equals(Direction.Right)) {
                checkWallCollision();
                setDirection(Direction.Right);
                getGm().nextMove = null;

            } else if (getGm().nextMove.equals(Direction.Up)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallAbove() == false) {
                        setDirection(Direction.Up);
                        getGm().nextMove = null;
                    }
                }
            } else if (getGm().nextMove.equals(Direction.Down)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallBelow() == false) {
                        setDirection(Direction.Down);
                        getGm().nextMove = null;
                    }
                }
            } else {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals(Direction.Up)) {
            if (getGm().nextMove.equals(Direction.Down)) {
                checkWallCollision();
                setDirection(Direction.Down);
                getGm().nextMove = null;
            } else if (getGm().nextMove.equals(Direction.Left)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnLeft() == false) {
                        setDirection(Direction.Left);
                        getGm().nextMove = null;
                    }
                }
            } else if (getGm().nextMove.equals(Direction.Right)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnRight() == false) {
                        setDirection(Direction.Right);
                        getGm().nextMove = null;
                    }
                }
            } else {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals(Direction.Down)) {
            if (getGm().nextMove.equals(Direction.Up)) {
                checkWallCollision();
                setDirection(Direction.Up);
                getGm().nextMove = null;
            } else if (getGm().nextMove.equals(Direction.Left)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnLeft() == false) {
                        setDirection(Direction.Left);
                        getGm().nextMove = null;
                    }
                }
            } else if (getGm().nextMove.equals(Direction.Right)) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnRight() == false) {
                        setDirection(Direction.Right);
                        getGm().nextMove = null;
                    }
                }
            } else {
                checkWallCollision();
                ;
            }
        }
    }

    /**
     * Use frame cycle to determine if the sprite should be open or closed, and set
     * the sprite as open if it should be open
     * 
     * @return True if sprite should be open, closed if sprite should be closed
     */
    public boolean determineSpriteOpen() {
        boolean open = false;
        if (this.frameCycle <= 7) {
            setSpriteOpen();
            open = true;
        } else if (this.frameCycle >= 16 && this.frameCycle <= 23) {
            setSpriteOpen();
            open = true;
        }

        if (this.frameCycle == 31) {
            this.frameCycle = 0;
        } else {
            this.frameCycle++;
        }

        return open;
    }

    /**
     * Changes the open sprite to face different directions depending on waka's
     * direction of travel
     */
    public void setSpriteOpen() {
        if (getDirection().equals(Direction.Left)) {
            setSprite(getGm().app.faceLeftImage);
        } else if (getDirection().equals(Direction.Right)) {
            setSprite(getGm().app.faceRightImage);
        } else if (getDirection().equals(Direction.Down)) {
            setSprite(getGm().app.faceDownImage);
        } else if (getDirection().equals(Direction.Up)) {
            setSprite(getGm().app.faceUpImage);
        }
    }

    /**
     * Draw either the closed or open sprite depending on whether
     * <code>this.determineSpriteOpen</code> returns true or not. Also draws the
     * lives waka has depending on his lives remaining.
     */
    public void draw() {
        if (getSprite() != null) {
            if (this.determineSpriteOpen()) {
                getGm().app.image(getSprite(), getX() - 4, getY() - 5);
            } else {
                getGm().app.image(closedSprite, getX() - 4, getY() - 5);
            }
        }

        // Display lives
        int lifeX = 8;
        int lifeY = 543;
        for (int i = 0; i < lives; i++) {
            if (getGm().app.faceRightImage != null) {
                getGm().app.image(getGm().app.faceRightImage, lifeX, lifeY);
            }
            lifeX += 30;
        }
    }

    /**
     * Check if the player is colliding into a wall. Used when the player has not
     * provided valid input at a junction.
     */
    public void checkWallCollision() {
        if (getDirection().equals(Direction.Right)) {
            if (collideWallOnRight()) {
                setXVel(0);
                setYVel(0);
                setDirection(Direction.Still);
                getGm().nextMove = null;
            }
        } else if (getDirection().equals(Direction.Left)) {
            if (collideWallOnLeft()) {
                setXVel(0);
                setYVel(0);
                setDirection(Direction.Still);
                getGm().nextMove = null;
            }
        } else if (getDirection().equals(Direction.Up)) {
            if (collideWallAbove()) {
                setXVel(0);
                setYVel(0);
                setDirection(Direction.Still);
                getGm().nextMove = null;
            }
        } else if (getDirection().equals(Direction.Down)) {
            if (collideWallBelow()) {
                setXVel(0);
                setYVel(0);
                setDirection(Direction.Still);
                getGm().nextMove = null;
            }
        }
    }

    /**
     * Check for collision with ghost based on player and ghost collision
     * boundaries, and set player/ghost alive boolean based on mode
     */
    public void checkGhostCollision() {
        for (Ghost ghost : getGm().ghosts) {
            if (ghost.isAlive()) {
                boolean collided = false;
                if (this.getBorderRight() >= ghost.getBorderLeft() && this.getBorderRight() <= ghost.getBorderRight()
                        && this.getBorderBot() >= ghost.getBorderTop() && this.getBorderBot() <= ghost.getBorderBot()) {
                    collided = true;
                } else if (this.getBorderLeft() <= ghost.getBorderRight()
                        && this.getBorderLeft() >= ghost.getBorderLeft() && this.getBorderBot() >= ghost.getBorderTop()
                        && this.getBorderBot() <= ghost.getBorderBot()) {
                    collided = true;
                } else if (this.getBorderLeft() <= ghost.getBorderRight()
                        && this.getBorderLeft() >= ghost.getBorderLeft() && this.getBorderTop() <= ghost.getBorderBot()
                        && this.getBorderTop() >= ghost.getBorderTop()) {
                    collided = true;
                } else if (this.getBorderRight() >= ghost.getBorderLeft()
                        && this.getBorderRight() <= ghost.getBorderRight()
                        && this.getBorderTop() <= ghost.getBorderBot() && this.getBorderTop() >= ghost.getBorderTop()) {
                    collided = true;
                }
                if (collided) {
                    if (ghost.getMode() == Mode.Frightened) {
                        ghost.setAlive(false);
                    } else {
                        this.alive = false;
                    }
                }
            }
        }
    }

    /**
     * Restart player when game restarts
     */
    public void playerRestart() {
        setX(getStartX());
        setY(getStartY());
        setDirection(Direction.Still);
        setGridX((int) Math.floor(getX() / 16));
        setGridY((int) Math.floor(getY() / 16));
        setCollisionBorders();
        setXVel(0);
        setYVel(0);
        setAlive(true);
        setLives(getGm().lives);
    }

    /**
     * Sets the alive status of waka
     * 
     * @param b Alive boolean.
     */
    public void setAlive(boolean b) {
        this.alive = b;
    }

    /**
     * Returns the alive status of waka
     * 
     * @return True if waka is alive, false if waka is dead.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Returns the number of lives waka has
     * 
     * @return The number of lives waka has
     */
    public long getLives() {
        return this.lives;
    }

    /**
     * Sets number of lives waka has
     * 
     * @param lives The number of lives waka has.
     */
    public void setLives(long lives) {
        this.lives = lives;
    }

    /**
     * Gets the frame cycle waka is on, used for calculating the sprite open/closed
     * position.
     * 
     * @return The frame cycle waka is on.
     */
    public int getFrameCycle() {
        return this.frameCycle;
    }

    /**
     * Sets the frame cycle waka is on, used for calculating the sprite open/closed
     * position.
     * 
     * @param frameCycle The frame cycle waka is on.
     */
    public void setFrameCycle(int frameCycle) {
        this.frameCycle = frameCycle;
    }
}
