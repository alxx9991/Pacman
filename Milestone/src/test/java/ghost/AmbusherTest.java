package ghost;

import org.junit.jupiter.api.Test;

import ghost.Ghost.Mode;
import ghost.Movable.Direction;


import java.io.File;

public class AmbusherTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");

    @Test
    public void generateVectorsTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost ambusher = null;
        
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Chase);
            if (ghost instanceof Ambusher) {
                ambusher = ghost;
            }
        }
        
        //Generate vectors when player going right
        gameManager.player.setDirection(Direction.Right);
        assert(ambusher.generateVectors(1, 1)[0] == -351);
        assert(ambusher.generateVectors(1000, 1000)[0] == 32);
        //Generate vectors when player going left
        gameManager.player.setDirection(Direction.Left);
        assert(ambusher.generateVectors(1, 1)[0] == -416);
        assert(ambusher.generateVectors(1000, 1000)[0] == 520);
        //Generate vectors when player going down
        gameManager.player.setDirection(Direction.Down);
        assert(ambusher.generateVectors(1, 1)[1] == -79);
        assert(ambusher.generateVectors(1000, 1000)[1] == 432);
        //Generate vectors when player going up
        gameManager.player.setDirection(Direction.Up);
        assert(ambusher.generateVectors(1, 1)[1] == -144);
        assert(ambusher.generateVectors(1000, 1000)[1] == 792);
        //Generate vectors when player is still
        gameManager.player.setDirection(Direction.Still);
        assert(ambusher.generateVectors(1, 1)[0] == -415);
        assert(ambusher.generateVectors(1000, 1000)[0] == 584);

    }
}
