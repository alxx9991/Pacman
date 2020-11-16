package ghost;

import processing.core.PImage;

public class Ignorant extends Ghost {
    public Ignorant(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 0, 576);
    }

    public int[] generateVectors(int targetx, int targety) {
        int[] vector = new int[2];
        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();
        
        //Calculate distance to waka
        double distance = Math.pow(Math.pow((double) vector[0] / 16.0, 2.0) + Math.pow((double) vector[1] / 16.0, 2.0), 0.5);
        
        //If in chase mode and distance is less than 8, target corner
        if (getMode().equals(Mode.Chase) && distance <= 8) {
            vector[0] = getCorner()[0] - getX();
            vector[1] = getCorner()[1] - getY();
        }
        return vector;
    }

}
