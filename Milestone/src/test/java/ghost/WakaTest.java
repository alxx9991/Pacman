package ghost;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import ghost.Movable.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class WakaTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");
    
    @Test
    public void wakaMoveTest() {
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
        
        
    }
}
