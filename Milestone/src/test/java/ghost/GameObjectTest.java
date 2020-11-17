package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
public class GameObjectTest {
    App testApp = new App();
    GameManager gameManager = new GameManager(testApp);
    File file = new File("./src/test/java/ghost/testMap.txt");
    
    @Test
    public void outOfBoundsErrorsTest() {
        gameManager.setUp();
        boolean exceptionCaught = false;
        exceptionCaught = false;
        try {
            gameManager.player.setX(10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setX(-10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setY(-10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setY(10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);
        
        //
        exceptionCaught = false;
        try {
            gameManager.player.setGridX(10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setGridX(-10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setGridY(-10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);

        exceptionCaught = false;
        try {
            gameManager.player.setGridY(10000);
        } catch (OutOfMapException e) {
            exceptionCaught = true;
        }
        assert(exceptionCaught);
        
    }
    
    
}
