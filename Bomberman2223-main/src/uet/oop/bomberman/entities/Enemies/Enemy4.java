package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.createGame.CreateMap.numOfEnemy;
import static uet.oop.bomberman.createGame.Management.*;

public class Enemy4 extends Enemy1 {
    int lives_remain = 2;
    int speed = 1;
    public Enemy4(int x, int y, Image img) {
        super( x, y, img);
    }
    public void update() {
        if (this.status.equals("alive")) move();
        else
            if (this.status.equals("die")) { onDie(); }
    }
    @Override
    public void onDie() {
        if (this.count_die > 50) { img = Sprite.minvo_dead.getFxImage(); count_die--; }
        else if (this.count_die > 0 )   { animate += Sprite.DEFAULT_SIZE/16;
        img = Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animate,
                Sprite.DEFAULT_SIZE).getFxImage();
        count_die--;}
        if (count_die == 0) {
            lives_remain--;
            if (lives_remain > 0) {status = "alive"; count_die = 75; speed++;}
            else if (lives_remain == 0)  {x = 1000;
                 numOfEnemy--; System.out.println("Number of Enemies: " + numOfEnemy);
                 status = "stop";}
        }
    }
    void move() {
        if (getDirect()==1 ) {
            x -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2,Sprite.minvo_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==2 ) {
            x += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2,Sprite.minvo_right3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==3 ) {
            y += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2,Sprite.minvo_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()== 4 ) {
            y -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2,Sprite.minvo_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }
}
