package ghost;

import org.junit.jupiter.api.Test;

import ghost.Movable.Direction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class MovableTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMapTwo.txt");

    @Test
    public void wallOnLeftAndRightTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        assert(gameManager.grid.size() > 0);
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(0);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(2);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(3);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(4);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(5);
        assert(gameManager.player.wallOnRight());
        assert(gameManager.player.wallOnLeft());
        gameManager.player.setGridX(0);
        assert(gameManager.player.wallOnLeft());
    }

    @Test
    public void wallAboveAndBelowTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();

        gameManager.player.setGridX(3);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridX(4);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridX(5);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridX(6);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridX(7);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridX(8);
        gameManager.player.setGridY(1);
        assert(gameManager.player.wallAbove());
        assert(gameManager.player.wallBelow());
        gameManager.player.setGridY(0);
        assert(gameManager.player.wallAbove());
    }
    @Test
    public void collideWallTestLeftAndRight() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(0);
        gameManager.player.setX(16);
        gameManager.player.setY(0);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(1);
        gameManager.player.setX(16);
        gameManager.player.setY(16);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(2);
        gameManager.player.setX(16);
        gameManager.player.setY(32);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(3);
        gameManager.player.setX(16);
        gameManager.player.setY(48);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(4);
        gameManager.player.setX(16);
        gameManager.player.setY(64);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
        gameManager.player.setGridX(1);
        gameManager.player.setGridY(5);
        gameManager.player.setX(16);
        gameManager.player.setY(80);
        assert(gameManager.player.collideWallOnRight());
        assert(gameManager.player.collideWallOnLeft());
    }

    @Test
    public void collideWallTestAboveAndBelow() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();

        gameManager.player.setGridX(3);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
        gameManager.player.setGridX(4);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
        gameManager.player.setGridX(5);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
        gameManager.player.setGridX(6);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
        gameManager.player.setGridX(7);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
        gameManager.player.setGridX(8);
        gameManager.player.setGridY(1);
        gameManager.player.setY(16);
        gameManager.player.setX(gameManager.player.getGridX() * 16);
        assert(gameManager.player.collideWallAbove());
        assert(gameManager.player.collideWallBelow());
    }
    
    @Test
    public void getVelocityTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        
        gameManager.player.setDirection(Direction.Right);
        gameManager.player.tick();
        assert(gameManager.player.getXVel() == gameManager.player.getSpeed());
        assert(gameManager.player.getYVel() == 0);
    }
    
}
