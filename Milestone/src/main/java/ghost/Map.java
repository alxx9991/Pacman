package ghost;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class Map {
    private final GameManager gm;

    public Map(File file, GameManager gm) {
        this.gm = gm;
        this.gm.grid = readMapFile(file);
    }
    
    /** 
     * Convert map file into a grid of characters
     */
    
    public static ArrayList<char[]> readMapFile(File file) {
        try {
            ArrayList<char[]> grid = new ArrayList<char[]>();
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.next();
                char[] lineList = new char[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    lineList[i] = line.charAt(i);
                }
                grid.add(lineList);
            }
            scanner.close();
            return grid;
        } catch (FileNotFoundException e) {
            return new ArrayList<char[]>();
        }
    }
    
    /**
     * Iterates through grid of characters and generate all the objects in the game
    */
    public void generateObjects() {
        int y = 0;
        if (gm.grid.size() == 0) {
            return;
        }
        for (int gridX = 0; gridX < gm.grid.size(); gridX++) {
            int x = 0;
            for (int gridY = 0; gridY < gm.grid.get(gridX).length; gridY++) {
                if (gm.grid.get(gridX)[gridY] == '1' || gm.grid.get(gridX)[gridY] == '2'
                        || gm.grid.get(gridX)[gridY] == '3' || gm.grid.get(gridX)[gridY] == '4'
                        || gm.grid.get(gridX)[gridY] == '5' || gm.grid.get(gridX)[gridY] == '6') {
                    Wall wall = new Wall(x, y, gm.grid.get(gridX)[gridY], this.gm, gridY, gridX);
                    gm.objects.add(wall);
                    gm.wallList.add(wall);
                } else if (gm.grid.get(gridX)[gridY] == '7') {
                    Fruit fruit = new Fruit(x, y, gm.app.fruitImage, this.gm, gridY, gridX);
                    gm.objects.add(fruit);
                    gm.fruits.add(fruit);
                } else if (gm.grid.get(gridX)[gridY] == '8') {
                    Fruit fruit = new SuperFruit(x, y, gm.app.fruitImage, this.gm, gridY, gridX);
                    gm.objects.add(fruit);
                    gm.fruits.add(fruit);
                } else if (gm.grid.get(gridX)[gridY] == 'p') {
                    Waka player = new Waka(x, y, gm.app.faceRightImage, this.gm, gridY, gridX);
                    gm.objects.add(player);
                    gm.player = player;
                } else if (gm.grid.get(gridX)[gridY] == 'a') {
                    Ghost ghost = new Ambusher(x, y, gm.app.ambusherImage, this.gm, gridY, gridX);
                    gm.objects.add(ghost);
                    gm.ghosts.add(ghost);
                    ;
                } else if (gm.grid.get(gridX)[gridY] == 'c') {
                    Ghost ghost = new Chaser(x, y, gm.app.chaserImage, this.gm, gridY, gridX);
                    gm.objects.add(ghost);
                    gm.ghosts.add(ghost);
                    ;
                } else if (gm.grid.get(gridX)[gridY] == 'i') {
                    Ghost ghost = new Ignorant(x, y, gm.app.ignorantImage, this.gm, gridY, gridX);
                    gm.objects.add(ghost);
                    gm.ghosts.add(ghost);
                    ;
                } else if (gm.grid.get(gridX)[gridY] == 'w') {
                    Ghost ghost = new Whim(x, y, gm.app.whimImage, this.gm, gridY, gridX);
                    gm.objects.add(ghost);
                    gm.ghosts.add(ghost);
                    ;
                }
                x += 16;
            }
            y += 16;
        }
    }
        

}