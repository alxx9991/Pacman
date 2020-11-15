package ghost;

import processing.core.PImage;

public class Chaser extends Ghost {
    private boolean hasWhim;

    public Chaser(int x, int y, PImage sprite, App app, int gridX, int gridY) {
        super(x, y, sprite, app, gridX, gridY, 0, 0);
        this.hasWhim = false;
    }

    public void setHasWhim(boolean hasWhim) {
        this.hasWhim = hasWhim;
    }

    public boolean hasWhim() {
        return this.hasWhim;
    }
}
