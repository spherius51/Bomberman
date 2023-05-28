package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.createGame.CreateMap.numOfEnemy;
import static uet.oop.bomberman.createGame.Management.*;

public class Enemy1 extends Entity {

    int count_die = 100;
    public String status = "alive";
    public Enemy1(int x, int y, Image img) {
        super( x, y, img);
    }
    Random rand = new Random();
    protected int ranNum = rand.nextInt(2) + 1;
    protected int state = 1;

    public void update() {
        if (this.status.equals("alive")) move();
        else if (this.status.equals("die")) onDie();
    }
    void move() {
        if (getDirect()==1 ) {
            x -= 1;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,Sprite.balloom_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==2 ) {
            x +=1;
           animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2,Sprite.balloom_right3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==3 ) {
            y +=1;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,Sprite.balloom_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()== 4 ) {
            y -=1;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,Sprite.balloom_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }

    public void onDie() {
            animate += Sprite.DEFAULT_SIZE/16;
            img = Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animate,
                    Sprite.DEFAULT_SIZE).getFxImage();
            count_die--;
            if (count_die == 0) {
                BombermanGame.enemyDie.play();
                enemy.remove(this);
                numOfEnemy--;
                status = "stop";
                System.out.println("Number of Enemies: " + numOfEnemy);
            }
    }
    public void supportRow() {
        if (this.y % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
            this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (this.y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
            this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
        }
    }

    public void supportColumn() {
        if (this.x % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
            this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (this.x % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
            this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
        }
    }
    protected int getDirect() {
                if (this.checkWall() || this.checkBrick() || this.checkBomb())
                {
                    switch (ranNum) {
                    case 1 : x += 1; supportRow(); break;
                    case 2 : x -=1 ; supportRow(); break;
                    case 3 : y -= 1;supportColumn(); break;
                    case 4 : y += 1;supportColumn(); break;
                }
                ranNum = rand.nextInt(4)+1;}
               return ranNum;
    }
}

