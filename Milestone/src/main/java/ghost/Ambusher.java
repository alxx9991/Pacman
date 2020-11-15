package ghost;

import processing.core.PImage;

public class Ambusher extends Ghost {
    public Ambusher(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY, 448, 0);
    }

    @Override public int[] generateVectors(int targetx, int targety) {
        int[] vector = new int[2];
        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();

        if (getMode().equals("chase")) {
            //Add 64 (4 grid spaces) in the player's direction of travel
            if (getApp().player.getDirection().equals("right")) {
                vector[0] += 64;
                if (getX() + vector[0] > 448) {
                    vector[0] = 448 - getX();
                }
            } else if (getApp().player.getDirection().equals("left")) {
                vector[0] -= 64;
                if (getX() + vector[0] < 0) {
                    vector[0] = -getX();
                }
            } else if (getApp().player.getDirection().equals("up")) {
                vector[1] -= 64;
                if (getY() + vector[1] < 0) {
                    vector[1] = -getY();
                }
            } else {
                vector[1] += 64;
                if (getY() + vector[1] > 576) {
                    vector[1] = 576 - getY();
                }
            }
        }

        return vector;
    }
}
