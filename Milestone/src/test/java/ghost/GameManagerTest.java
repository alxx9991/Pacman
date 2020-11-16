package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    @Test 
    public void constructorTest() {
        App testApp = new App();
        GameManager gameManager = new GameManager(testApp);
        assertNotNull(gameManager);
    }
    
    @Test
    public void setUpTest() {
        App testApp = new App();
        GameManager gameManager = new GameManager(testApp);
        gameManager.setUp();
        assert(gameManager.debug == false);
        assert(gameManager.objects.size() != 0);
        assert(gameManager.ghosts.size() != 0);
        assertNotNull(gameManager.player);
    }

    @Test
    public void tickTest() {
        App testApp = new App();
        GameManager gameManager = new GameManager(testApp);
        gameManager.setUp();
        gameManager.tick();
        assertFalse(gameManager.gameEnded);
        gameManager.player.setLives(0);
        gameManager.tick();
        assertTrue(gameManager.gameEnded);
        gameManager.tick();
        gameManager.gameEndedCount = 599;
        gameManager.tick();
        assertFalse(gameManager.gameEnded);
        assert(gameManager.gameEndedCount == 0);
    }
}
