package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
public class SuperFruitTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");

    @Test
    public void drawTest() {
        gameManager.readConfig();
        Map map = new Map(file, gameManager);
        map.generateObjects();
        SuperFruit superfruit = null;
        for (Fruit fruit: gameManager.fruits) {
            if (fruit instanceof SuperFruit) {
                superfruit = (SuperFruit)fruit;
            }
        }
        superfruit.setAlive(false);
        superfruit.draw();
        assert(superfruit.isActivated());
        superfruit.draw();
        superfruit.setActivated(false);
        superfruit.draw();
        for (Ghost ghost: gameManager.ghosts) {
            assert(ghost.getFrightenedCount() == 0);
        }
    }
}
