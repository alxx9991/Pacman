package ghost;

import processing.core.PImage;

public class Waka extends Movable {
    private int frameCycle;
    private PImage closedSprite;
    boolean alive;
    private long lives;

    public Waka(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY);
        this.frameCycle = 0;
        this.closedSprite = getApp().closedImage;
        setDirection("still");
        this.alive = true;
        this.lives = getApp().lives;
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 22);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 21);
    }

    public void tick() {
        selectDirection();
        move();
        setCollisionBorders();
        checkGhostCollision();
        resetIfDead();
    }
    
    //Reset waka and ghost locations if dead, and remove a life
    public void resetIfDead() {
        if (this.alive == false) {
            this.lives -= 1;
            setX(getStartX());
            setY(getStartY());
            setDirection("still");
            setGridX((int) Math.floor(getX() / 16));
            setGridY((int) Math.floor(getY() / 16));
            setCollisionBorders();
            setXVel(0);
            setYVel(0);
            this.alive = true;
            

            for (Ghost ghost : getApp().ghosts) {
                ghost.setAlive(true);
                ghost.setX(ghost.getStartX());
                ghost.setY(ghost.getStartY());
                ghost.setGridX((int) Math.floor(ghost.getX() / 16));
                ghost.setGridY((int) Math.floor(ghost.getY() / 16));
                ghost.setCollisionBorders();
                ghost.setDirection("still");
                ghost.setXVel(0);
                ghost.setYVel(0);

            }

        }
    }
    
    //Set collision boundaries
    public void setCollisionBorders() {
        setBorderTop(getY() + 2);
        setBorderBot(getY() + 22);
        setBorderLeft(getX() + 2);
        setBorderRight(getX() + 21);
    }
    
    //Choose direction 
    public void selectDirection() {
        //If player is still, select direction based on choice
        if (getDirection().equals("still")) {
            if (getApp().nextMove == null) {
                return;
            }
            if (getApp().nextMove.equals("left")) {
                if (!wallOnLeft()) {
                    setDirection("left");
                } else {
                    getApp().nextMove = null;
                }
            } else if (getApp().nextMove.equals("right")) {
                if (!wallOnRight()) {
                    setDirection("right");
                } else {
                    getApp().nextMove = null;
                }
            } else if (getApp().nextMove.equals("down")) {
                if (!wallBelow()) {
                    setDirection("down");
                } else {
                    getApp().nextMove = null;
                }
            } else if (getApp().nextMove.equals("up")) {
                if (!wallAbove()) {
                    setDirection("up");
                } else {
                    getApp().nextMove = null;
                }
            }
            return;
        }

        //If player is currently moving, select direction based on available choices and desired direction
        if (getApp().nextMove == null) {
            checkWallCollision();
        } else if (getDirection().equals("right")) {
            if (getApp().nextMove.equals("left")) {
                checkWallCollision();
                setDirection("left");
                getApp().nextMove = null;

            } else if (getApp().nextMove.equals("up")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallAbove() == false) {
                        setDirection("up");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("down")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallBelow() == false) {
                        setDirection("down");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("right")) {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals("left")) {
            if (getApp().nextMove.equals("right")) {
                checkWallCollision();
                setDirection("right");
                getApp().nextMove = null;

            } else if (getApp().nextMove.equals("up")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallAbove() == false) {
                        setDirection("up");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("down")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallBelow() == false) {
                        setDirection("down");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("left")) {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals("up")) {
            if (getApp().nextMove.equals("down")) {
                checkWallCollision();
                setDirection("down");
                getApp().nextMove = null;
            } else if (getApp().nextMove.equals("left")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnLeft() == false) {
                        setDirection("left");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("right")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnRight() == false) {
                        setDirection("right");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("up")) {
                checkWallCollision();
                ;
            }
        } else if (getDirection().equals("down")) {
            if (getApp().nextMove.equals("up")) {
                checkWallCollision();
                setDirection("up");
                getApp().nextMove = null;
            } else if (getApp().nextMove.equals("left")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnLeft() == false) {
                        setDirection("left");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("right")) {
                checkWallCollision();
                if (getX() % 16 == 0 && getY() % 16 == 0) {
                    if (wallOnRight() == false) {
                        setDirection("right");
                        getApp().nextMove = null;
                    }
                }
            } else if (getApp().nextMove.equals("down")) {
                checkWallCollision();
                ;
            }
        }
    }
    
    //Use frame cycle to determine if the sprite should be open or closed, and set the sprite as open if it should be open
    public boolean determineSpriteOpen() {
        boolean open = false;
        if (this.frameCycle >= 0 && this.frameCycle <= 7) {
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
    
    //Choose open sprite based on direction of travel
    public void setSpriteOpen() {
        if (getDirection().equals("left")) {
            setSprite(getApp().faceLeftImage);
        } else if (getDirection().equals("right")) {
            setSprite(getApp().faceRightImage);
        } else if (getDirection().equals("down")) {
            setSprite(getApp().faceDownImage);
        } else if (getDirection().equals("up")) {
            setSprite(getApp().faceUpImage);
        }
    }

    @Override
    public void draw() {
        if (this.determineSpriteOpen()) {
            getApp().image(getSprite(), getX() - 4, getY() - 5);
        } else {
            getApp().image(closedSprite, getX() - 4, getY() - 5);
        }

        // Display lives
        int lifeX = 8;
        int lifeY = 543;
        for (int i = 0; i < lives; i++) {
            getApp().image(getApp().faceRightImage, lifeX, lifeY);
            lifeX += 30;
        }
    }

    public void checkWallCollision() {
        if (getDirection().equals("right")) {
            if (collideWallOnRight()) {
                setXVel(0);
                setYVel(0);
                setDirection("still");
                getApp().nextMove = null;
            }
        } else if (getDirection().equals("left")) {
            if (collideWallOnLeft()) {
                setXVel(0);
                setYVel(0);
                setDirection("still");
                getApp().nextMove = null;
            }
        } else if (getDirection().equals("up")) {
            if (collideWallAbove()) {
                setXVel(0);
                setYVel(0);
                setDirection("still");
                getApp().nextMove = null;
            }
        } else if (getDirection().equals("down")) {
            if (collideWallBelow()) {
                setXVel(0);
                setYVel(0);
                setDirection("still");
                getApp().nextMove = null;
            }
        }
    }
    
    //Check for collision with ghost based on player and ghost collision boundaries, and set player/ghost alive boolean based on mode
    public void checkGhostCollision() {
        for (Ghost ghost : getApp().ghosts) {
            boolean collided = false;
            if (this.getBorderRight() >= ghost.getBorderLeft() && this.getBorderRight() <= ghost.getBorderRight() && this.getBorderBot() >= ghost.getBorderTop() && this.getBorderBot() <= ghost.getBorderBot()) {
                collided = true;
            } else if (this.getBorderLeft() <= ghost.getBorderRight() && this.getBorderLeft() >= ghost.getBorderLeft() && this.getBorderBot() >= ghost.getBorderTop() && this.getBorderBot() <= ghost.getBorderBot()) {
                collided = true;
            } else if (this.getBorderLeft() <= ghost.getBorderRight() && this.getBorderLeft() >= ghost.getBorderLeft() && this.getBorderTop() <= ghost.getBorderBot() && this.getBorderTop() >= ghost.getBorderTop()) {
                collided = true;
            } else if (this.getBorderRight() >= ghost.getBorderLeft() && this.getBorderRight() <= ghost.getBorderRight() && this.getBorderTop() <= ghost.getBorderBot() && this.getBorderTop() >= ghost.getBorderTop()) {
                collided = true;
            }
            if (collided) {
                if (ghost.getMode() == "frightened") {
                    ghost.setAlive(false);
                } else {
                    this.alive = false;
                }
            }
        }
    }

    //Restart player when game restarts
    public void playerRestart() {
        setX(getStartX());
        setY(getStartY());
        setDirection("still");
        setGridX((int) Math.floor(getX() / 16));
        setGridY((int) Math.floor(getY() / 16));
        setCollisionBorders();
        setXVel(0);
        setYVel(0);
        setAlive(true);
        setLives(getApp().lives);
    }

    public void setAlive(boolean b) {
        this.alive = b;
    }

    public long getLives() {
        return this.lives;
    }

    public void setLives(long lives) {
        this.lives = lives;
    }
}
