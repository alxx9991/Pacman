package ghost;

import org.junit.jupiter.api.Test;

import ghost.Ghost.Mode;
import ghost.Movable.Direction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class GhostTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");

    @Test
    public void selectDirectionTest() { //Test selecting direction
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Frightened);
            ghost.selectDirection();
            assert (ghost.getMode() == Mode.Frightened);
            assert (ghost.getDirection() != Direction.Still);
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }

        //Select direction when still
        assertNotNull(chaser);
        assert(chaser.getCorner()[0] == 0 && chaser.getCorner()[1] == 0);
        chaser.setDirection(Direction.Still);
        chaser.setMode(Mode.Chase);
        chaser.selectDirection();
        assert (chaser.getDirection() == Direction.Left);
        //Select direction in scatter mode
        chaser.setDirection(Direction.Down);
        chaser.setMode(Mode.Scatter);
        chaser.selectDirection();
        assert (chaser.getDirection() == Direction.Left);
    }

    @Test
    public void selectModeTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }
        //Test selecting mode when frightened
        chaser.setMode(Mode.Frightened);
        chaser.selectMode();
        assert (chaser.getMode() == Mode.Frightened);
        
        //Test ending scatter mode when timer expires
        chaser.setMode(Mode.Scatter);
        chaser.setCycleLength(100);
        chaser.setFrameCount(100);
        chaser.selectMode();
        assert (chaser.getFrameCount() == 0);
        assert (chaser.getMode() == Mode.Chase);
        
        //Test ending chase mode when timer expires
        chaser.setCycleLength(100);
        chaser.setFrameCount(100);
        chaser.selectMode();
        assert (chaser.getFrameCount() == 0);
        assert (chaser.getMode() == Mode.Scatter);
        
        //Test reset to start of mode lengths array
        chaser.setCycleLength(100);
        chaser.setFrameCount(100);
        chaser.setCycleIndex(gameManager.modeLengths.size());
        chaser.selectMode();
        assert (chaser.getCycleIndex() == 0);
        assert (chaser.getFrameCount() == 0);
        assert (chaser.getMode() == Mode.Chase);
    }

    @Test
    public void canChangeDirectionTest() { 
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }

        //Test can change directions from top left
        chaser.setGridX(1);
        chaser.setGridY(4);
        chaser.setX(16);
        chaser.setY(64);
        chaser.setDirection(Direction.Up);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Down);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Right);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Left);
        assert (chaser.canChangeDirection());
        
        //Test can change directions from bottom right
        chaser.setGridX(26);
        chaser.setGridY(32);
        chaser.setX(416);
        chaser.setY(512);
        chaser.setDirection(Direction.Up);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Down);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Right);
        assert (chaser.canChangeDirection());

        chaser.setDirection(Direction.Left);
        assert (chaser.canChangeDirection());
        
        //Test can change directions when not at intersection top left
        chaser.setGridX(1);
        chaser.setGridY(5);
        chaser.setX(16);
        chaser.setY(80);
        chaser.setDirection(Direction.Up);
        assertFalse(chaser.canChangeDirection());

        chaser.setDirection(Direction.Down);
        assertFalse(chaser.canChangeDirection());
        
        //Test can change directions when not at intersection bottom right
        chaser.setGridX(25);
        chaser.setGridY(32);
        chaser.setX(400);
        chaser.setY(512);

        chaser.setDirection(Direction.Right);
        assertFalse(chaser.canChangeDirection());

        chaser.setDirection(Direction.Left);
        assertFalse(chaser.canChangeDirection());
    }

    @Test
    public void canGoDirectionTest() { //Test allowed to go in certain direction
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }
        chaser.setGridX(1);
        chaser.setGridY(4);
        chaser.setX(16);
        chaser.setY(64);
        assertFalse(chaser.canGoDirection(Direction.Up));
        chaser.setGridY(5);
        chaser.setY(80);
        assert (chaser.canGoDirection(Direction.Up));
    }

    @Test
    public void generatePreferencesTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }
        //Test generating preferences with different attack vectors
        int[] vector = { 2, 1 };
        assert (chaser.generatePreferences(Direction.Right, vector).get(0) == Direction.Right);
        int[] vector2 = { -2, -1 };
        assert (chaser.generatePreferences(Direction.Up, vector2).get(0) == Direction.Left);
        int[] vector3 = { 2, -1 };
        assert (chaser.generatePreferences(Direction.Down, vector3).get(3) == Direction.Up);
        int[] vector4 = { -2, 1 };
        assert (chaser.generatePreferences(Direction.Left, vector4).get(3) == Direction.Right);
    }

    @Test
    public void checkIfFrightenedTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }
        //Test setting frightened mode and saving frightened mode
        chaser.setMode(Mode.Frightened);
        chaser.setSavedMode(Mode.Chase);
        chaser.checkIfFrightened();
        assert(chaser.getFrightenedCount() == 1);

        //Test resetting frightened mode
        chaser.setFrightenedCount(2);
        chaser.setFrightenedLength(3);
        assert(chaser.getSavedMode() == Mode.Chase);
        chaser.checkIfFrightened();
        assert(chaser.getFrightenedCount() == 0);
        

    }

    @Test
    public void drawLineTest() { //Test draw target line - should not throw exception when Papplet.g is null
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Chase);
            ghost.tick();
        }
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Scatter);
            ghost.drawTargetLine();
        }
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Frightened);
            ghost.drawTargetLine();
        }
    }

    @Test
    public void deadDrawTest() { //Test draw line when dead. Should not draw line.
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setAlive(false);
            ghost.tick();
            ghost.draw();
        }
    }
}
