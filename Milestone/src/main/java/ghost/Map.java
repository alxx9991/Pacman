package ghost;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class Map {
    private ArrayList<char[]> grid;
    private App app;

    public Map(File file, App app) {
        this.app = app;
        this.grid = readMapFile(file);
        this.app.grid = grid;
    }
    
    //Convert map file into a grid of characters
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
    
    //Iterate through grid of characters and generate all the objects in the game
    public void generateObjects() {
        int y = 0;
        if (this.grid == null) {
            return;
        }
        for (int gridX = 0; gridX < this.grid.size(); gridX++) {
            int x = 0;
            for (int gridY = 0; gridY < this.grid.get(gridX).length; gridY++) {
                if (this.grid.get(gridX)[gridY] == '1' || this.grid.get(gridX)[gridY] == '2'
                        || this.grid.get(gridX)[gridY] == '3' || this.grid.get(gridX)[gridY] == '4'
                        || this.grid.get(gridX)[gridY] == '5' || this.grid.get(gridX)[gridY] == '6') {
                    Wall wall = new Wall(x, y, this.grid.get(gridX)[gridY], this.app, gridY, gridX);
                    app.objects.add(wall);
                    app.wallList.add(wall);
                } else if (this.grid.get(gridX)[gridY] == '7') {
                    Fruit fruit = new Fruit(x, y, app.fruitImage, this.app, gridY, gridX);
                    app.objects.add(fruit);
                    app.fruits.add(fruit);
                } else if (this.grid.get(gridX)[gridY] == '8') {
                    Fruit fruit = new SuperFruit(x, y, app.fruitImage, this.app, gridY, gridX);
                    app.objects.add(fruit);
                    app.fruits.add(fruit);
                } else if (this.grid.get(gridX)[gridY] == 'p') {
                    Waka player = new Waka(x, y, app.faceRightImage, this.app, gridY, gridX);
                    app.objects.add(player);
                    app.player = player;
                } else if (this.grid.get(gridX)[gridY] == 'a') {
                    Ghost ghost = new Ambusher(x, y, app.ambusherImage, this.app, gridY, gridX);
                    app.objects.add(ghost);
                    app.ghosts.add(ghost);
                    ;
                } else if (this.grid.get(gridX)[gridY] == 'c') {
                    Ghost ghost = new Chaser(x, y, app.chaserImage, this.app, gridY, gridX);
                    app.objects.add(ghost);
                    app.ghosts.add(ghost);
                    ;
                } else if (this.grid.get(gridX)[gridY] == 'i') {
                    Ghost ghost = new Ignorant(x, y, app.ignorantImage, this.app, gridY, gridX);
                    app.objects.add(ghost);
                    app.ghosts.add(ghost);
                    ;
                } else if (this.grid.get(gridX)[gridY] == 'w') {
                    Ghost ghost = new Whim(x, y, app.whimImage, this.app, gridY, gridX);
                    app.objects.add(ghost);
                    app.ghosts.add(ghost);
                    ;
                }
                x += 16;
            }
            y += 16;
        }
    }
}