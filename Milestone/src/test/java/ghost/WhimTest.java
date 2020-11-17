package ghost;

import org.junit.jupiter.api.Test;

import ghost.Ghost.Mode;
import ghost.Movable.Direction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class WhimTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");

    @Test
    public void generateVectorsTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost whim = null;
        Ghost whim2 = null;
        Ghost chaser = null;
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Chase);
            if (ghost instanceof Whim) {
                ((Whim)ghost).setChaser();
                if (((Whim)ghost).hasChaser()) {
                    whim = ghost;
                } else {
                    whim2 = ghost;
                }
            } else if (ghost instanceof Chaser) {
                chaser = ghost;
            }
        }
        gameManager.player.setDirection(Direction.Right);
        assert(whim.generateVectors(1, 1)[0] == -622);
        assert(whim.generateVectors(1000, 1000)[0] == 176);

        gameManager.player.setDirection(Direction.Left);
        assert(whim.generateVectors(1, 1)[0] == -272);
        assert(whim.generateVectors(1000, 1000)[0] == 1248);

        gameManager.player.setDirection(Direction.Down);
        assert(whim.generateVectors(1, 1)[1] == -286);
        assert(whim.generateVectors(1000, 1000)[1] == 400);

        gameManager.player.setDirection(Direction.Up);
        assert(whim.generateVectors(1, 1)[1] == -176);
        assert(whim.generateVectors(1000, 1000)[1] == 1584);

        gameManager.player.setDirection(Direction.Still);
        assert(whim.generateVectors(1, 1)[0] == -686);
        assert(whim.generateVectors(1000, 1000)[0] == 1312);

        assert(whim2.generateVectors(1, 1)[0] == -175);

        chaser.setAlive(false);
        assert(whim.generateVectors(1, 1)[0] == -271);


    }
}