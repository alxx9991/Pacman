package ghost;

import org.junit.jupiter.api.Test;

import java.io.File;

import ghost.Ghost.Mode;
import ghost.Movable.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class WakaTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");
    
    @Test
    public void wakaIntersectionMoveTestStandstill() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);

        //Move left from standstill
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Move right from standstill
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Right);

        //Move up from standstill and hit wall
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Still);

        //Move down from standstill and hit wall
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Still);

        //Relocate player
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(10);
        gameManager.player.setX(16);
        gameManager.player.setY(160);

        //Move left from standstill and hit wall
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Still);

        //Move right from standstill and hit wall
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Still);

        //Move up from standstill
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Move down from standstill
        gameManager.player.setDirection(Direction.Still);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.tick();
        assert(gameManager.player.getDirection() == Direction.Down);
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go right1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);

        //Go right2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Right from down but not at intersection
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(273);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go right3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go right4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);

        //Go left1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Go left2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Go left2 from going down, not at intersection
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(273);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go left3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);
        
        //Go left4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Go up1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go up2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go up3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go up3 from going right but not at intersection
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(273);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go up4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go up4 from going left but not at intersection
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(223);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Go down1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go down2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go down3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go down3 from going right but not at intersection
        gameManager.player.setGridX(3);
        gameManager.player.setGridY(4);
        gameManager.player.setX(48);
        gameManager.player.setY(65);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);


        //Go down3 from going right but wall below
        gameManager.player.setGridX(3);
        gameManager.player.setGridY(4);
        gameManager.player.setX(48);
        gameManager.player.setY(64);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go down4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go down4 from going left but not at intersection
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(273);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Null1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = null;
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Null2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = null;
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Null3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = null;
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Null4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(336);
        gameManager.player.setY(272);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = null;
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);
    }

    @Test
    public void nonIntersectionMoveTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);
        
        //Go right1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go right2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go left1 from going up
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Up);

        //Go left2 from going down
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Down);

        //Go up3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go up4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);

        //Go down3 from going right
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Right);
        
        //Go down4 from going left
        gameManager.player.setGridX(21);
        gameManager.player.setGridY(17);
        gameManager.player.setX(340);
        gameManager.player.setY(275);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Left);
    }

    @Test
    public void topLeftCorner() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);
        
        //Test attempt left from going up in corner
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(4);
        gameManager.player.setX(16);
        gameManager.player.setY(64);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);

        //Test attempt up from going left in corner
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(4);
        gameManager.player.setX(16);
        gameManager.player.setY(64);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);
    }

    @Test
    public void topRightCorner() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);
        
        //Test attempt going right from up in corner
        gameManager.player.setGridX(26);
        gameManager.player.setGridY(4);
        gameManager.player.setX(416);
        gameManager.player.setY(64);
        gameManager.player.setDirection(Direction.Up);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);
        // Test attempt going up from right in corner
        gameManager.player.setGridX(26);
        gameManager.player.setGridY(4);
        gameManager.player.setX(416);
        gameManager.player.setY(64);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Up;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);
    }

    @Test
    public void bottomRightCornerCollisionTest() {
        //Hit wall in bottom right corner test
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);
        
        gameManager.player.setGridX(26);
        gameManager.player.setGridY(32);
        gameManager.player.setX(416);
        gameManager.player.setY(512);
        gameManager.player.setDirection(Direction.Right);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);

        gameManager.player.setGridX(26);
        gameManager.player.setGridY(32);
        gameManager.player.setX(416);
        gameManager.player.setY(512);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Right;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);
    }

    @Test
    public void bottomLeftCornerCollisionTest() {
        //Hit wall in bottom left corner collision test
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        assertNotNull(gameManager.player);
        
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(32);
        gameManager.player.setX(16);
        gameManager.player.setY(512);
        gameManager.player.setDirection(Direction.Down);
        gameManager.nextMove = Direction.Left;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);

        gameManager.player.setGridX(1);
        gameManager.player.setGridY(32);
        gameManager.player.setX(16);
        gameManager.player.setY(512);
        gameManager.player.setDirection(Direction.Left);
        gameManager.nextMove = Direction.Down;
        assertNotNull(gameManager.nextMove);
        gameManager.player.selectDirection();
        gameManager.player.move();
        assert(gameManager.player.getDirection() == Direction.Still);
    }

    @Test
    public void testResetIfDead() { // Test resetting waka when he dies
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        gameManager.player.setAlive(false);
        gameManager.tick();
        assert(gameManager.player.isAlive());
    }

    @Test
    public void testSetSpriteOpen() {
        //Test setting sprite open and closed in various directions
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        gameManager.player.setSpriteOpen();

        gameManager.player.setDirection(Direction.Left);
        gameManager.player.setSpriteOpen();
        assertNull(gameManager.player.getSprite());
        gameManager.player.setDirection(Direction.Right);
        gameManager.player.setSpriteOpen();
        assertNull(gameManager.player.getSprite());
        gameManager.player.setDirection(Direction.Up);
        gameManager.player.setSpriteOpen();
        assertNull(gameManager.player.getSprite());
        gameManager.player.setDirection(Direction.Down);
        gameManager.player.setSpriteOpen();

        assertNull(gameManager.player.getSprite());
    }
    
    @Test
    public void testDetermineSpriteOpen() { //Test determining sprite open/closed at various points in the open/close cycle
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.player.determineSpriteOpen());
        gameManager.player.setFrameCycle(9);
        assertFalse(gameManager.player.determineSpriteOpen());
        gameManager.player.setFrameCycle(20);
        assert(gameManager.player.determineSpriteOpen());
        gameManager.player.setFrameCycle(24);
        assertFalse(gameManager.player.determineSpriteOpen());
        gameManager.player.setFrameCycle(31);
        gameManager.player.determineSpriteOpen();
        assert(gameManager.player.getFrameCycle() == 0);
    }
    
    @Test
    public void testCollisions() { //Test collisions with ghosts
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        //Top right collision
        gameManager.player.setX(190);
        gameManager.player.setY(205);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);
        
        //Top left collision
        gameManager.player.setAlive(true);
        gameManager.player.setX(160);
        gameManager.player.setY(205);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);

        //Botton left collision
        gameManager.player.setAlive(true);
        gameManager.player.setX(160);
        gameManager.player.setY(240);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);
        
        //Botton right collision
        gameManager.player.setAlive(true);
        gameManager.player.setX(190);
        gameManager.player.setY(240);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);

        for(Ghost ghost: gameManager.ghosts) {
            ghost.setMode(Mode.Frightened);
        }
        
        //Test frightened collisions
        gameManager.player.setX(170);
        gameManager.player.setY(223);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);

        gameManager.player.setX(170);
        gameManager.player.setY(223);
        gameManager.player.setCollisionBorders();
        gameManager.player.checkGhostCollision();
        assert(gameManager.player.isAlive() == false);
    }
    
    @Test
    public void drawTest() {
        //Test draw function - should not throw exception
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        gameManager.player.draw();
    }
}
