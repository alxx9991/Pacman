package ghost;
import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    
    @Test
    public void noSuchMapTest() { // Test no such map
        App testApp = new App();
        GameManager gameManager = new GameManager(testApp);
        File file = new File("Sfaefadf");
        Map map = new Map(file, gameManager);
        assertNotNull(map);
        assert(gameManager.grid.size() == 0);
        map.generateObjects();
        assert(gameManager.objects.size() == 0);
    }
}
