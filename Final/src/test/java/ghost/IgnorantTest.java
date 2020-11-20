package ghost;

import org.junit.jupiter.api.Test;

import ghost.Ghost.Mode;

import java.io.File;

public class IgnorantTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");

    @Test
    public void generateVectorsTest() { //Test chaser vector generation
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        Ghost ignorant = null;
        
        map.generateObjects();
        for (Ghost ghost : gameManager.ghosts) {
            ghost.setMode(Mode.Chase);
            if (ghost instanceof Ignorant) {
                ignorant = ghost;
            }
        }
        ignorant.setX(64);
        ignorant.setY(64);
        ignorant.setMode(Mode.Chase);
        assert(ignorant.generateVectors(1, 1)[0] == -64);
        ignorant.setMode(Mode.Scatter);
        assert(ignorant.generateVectors(1, 1)[0] == -63);
    }
}
