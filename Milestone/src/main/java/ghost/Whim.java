package ghost;

import processing.core.PImage;

public class Whim extends Ghost {
    private Chaser chaser;

    public Whim(int x, int y, PImage sprite, GameManager gm, int gridX, int gridY) {
        super(x, y, sprite, gm, gridX, gridY, 448, 576);
    }

    public void setChaser() {
        for (Ghost ghost : getGm().ghosts) {
            if (ghost instanceof Chaser) {
                if (((Chaser) ghost).hasWhim() == false) {
                    this.chaser = (Chaser) ghost;
                    this.chaser.setHasWhim(true);
                    return;
                }
            }
        }
    }

    public int[] generateVectors(int targetx, int targety) {
        // target will be where the vector of the chaser + location of target
        // set new vector
        int[] vector = new int[2];
        int[] chaserVector = new int[2];

        vector[0] = targetx - this.getX();
        vector[1] = targety - this.getY();

        if (getMode().equals(Mode.Chase)) {
            if (this.chaser == null) {

                vector[0] = targetx - this.getX();
                vector[1] = targety - this.getY();

            } else if (this.chaser.isAlive() == true) {

                chaserVector[0] = 2 * (targetx - chaser.getX());
                chaserVector[1] = 2 * (targety - chaser.getY());

                targetx = chaserVector[0] + chaser.getX();
                targety = chaserVector[1] + chaser.getY();

                vector[0] = targetx - this.getX();
                vector[1] = targety - this.getY();

                if (getGm().player.getDirection().equals(Direction.Right)) {
                    vector[0] += 64;
                    if (getX() + vector[0] > 448) {
                        vector[0] = 448 - getX();
                    }
                } else if (getGm().player.getDirection().equals(Direction.Left)) {
                    vector[0] -= 64;
                    if (getX() + vector[0] < 0) {
                        vector[0] = -getX();
                    }
                } else if (getGm().player.getDirection().equals(Direction.Up)) {
                    vector[1] -= 64;
                    if (getY() + vector[1] < 0) {
                        vector[1] = -getY();
                    }
                } else if (getGm().player.getDirection().equals(Direction.Down)){
                    vector[1] += 64;
                    if (getY() + vector[1] > 576) {
                        vector[1] = 576 - getY();
                    }
                }
            }
        }
        return vector;
    }
}
