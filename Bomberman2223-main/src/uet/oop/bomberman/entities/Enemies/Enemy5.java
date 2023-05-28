package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.createGame.Management.bombergirl;
import static uet.oop.bomberman.createGame.Management.bomberman;
public class Enemy5 extends Enemy3 {
    int count = 500;

    public Enemy5(int x, int y, Image img) {
        super( x, y, img);
    }
    public void update() {
        if (this.status.equals("alive")) {
            move();}
        else if (this.status.equals("die")) onDie();
            if (this.status.equals("alive") || this.status.equals("change")) {
            count--;
            if (count <100 && count > 75) {
                img = Sprite.ghost_dead.getFxImage(); status = "change";}
            if (count <75 && count > 0) {
                animate += Sprite.DEFAULT_SIZE/16;
                img = Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animate,
                        Sprite.DEFAULT_SIZE).getFxImage();
            }
            if (count == 0) {
                status = "alive";
                Random xxx = new Random();
                this.x = xxx.nextInt( Sprite.SCALED_SIZE * 31 - 64) + 32;
                this.y = xxx.nextInt(Sprite.SCALED_SIZE*13 - 96) + 32;
                count = 500;
            }
        }

    }

    @Override
    public void onDie() {
        if (this.count_die > 50) { img = Sprite.ghost_dead.getFxImage(); count_die--; }
        else if (this.count_die > 0 )    super.onDie();
    }
    void move() {
        if (getDirect()==1 ) { // di len ben trai
            x -= 1;
            y -= 1;
            img = Sprite.ghost_left3.getFxImage();
        }
        if (getDirect()==3 ) { // di xuong ben phai
            x += 1;
            y += 1;
            img = Sprite.ghost_right3.getFxImage();
        }

        if (getDirect()==2 ) { // di len ben phai
            x += 1;
            y -= 1;
            img = Sprite.ghost_right3.getFxImage();
        }

        if (getDirect()==4 ) { // di xuong ben trai
            x -= 1;
            y += 1;
            img = Sprite.ghost_left3.getFxImage();
        }
    }
    @Override
    protected int getDirect() {
            if (y <= 32 || y >= Sprite.SCALED_SIZE * 13 - 64) {
            if (ranNum == 1) {
                ranNum = 4;
                return ranNum;
            }
            if (ranNum == 2) {
                ranNum = 3;
                return ranNum;
            }
            if (ranNum == 3) {
                ranNum = 2;
                return ranNum;
            }
            if (ranNum == 4) {
                ranNum = 1;
                return ranNum;
            }
        }
        if (x <= 32 ) {
            if (ranNum == 1) {
                ranNum = 2;
                return ranNum;
            }
            if (ranNum == 4) {
                ranNum = 3;
                return ranNum;
            }
        }if (x >= Sprite.SCALED_SIZE * 31 - 64) {
            if (ranNum == 2) {
                ranNum = 1;
                return ranNum;
            }
            if (ranNum == 3) {
                ranNum = 4;
                return ranNum;
            }

        }
        return ranNum;
    }

}
