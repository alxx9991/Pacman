package ghost;

import processing.core.PImage;

public class Chaser extends Ghost {
    private boolean hasWhim;

    public Chaser(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 0, 0);
        this.hasWhim = false;
    }

    public int[] generateVectors(int targetx, int targety) {
        int[] vector = new int[2];
        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();
        return vector;
    }

    public void setHasWhim(boolean hasWhim) {
        this.hasWhim = hasWhim;
    }

    public boolean hasWhim() {
        return this.hasWhim;
    }
}
