package uet.oop.bomberman.entities.players;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.entities.tiles.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.createGame.Management.*;


public class Bomber extends Entity {
    private static int count = 0; // count die
    char s;

    public  String status = "alive";
    public List<Bomb> bombs = new ArrayList<>();
    public int numOfLives = 3;
    public int sizeOfFlame = 2; // cua chung 2 players
    public int numBombs = 2;
    public int speed = 2;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }




    public void update() {
        for (Enemy1 e : enemy) {
            if (e.intersects(bombergirl)) this.status = "die";
        }
       // if (status.equals("alive"))
        move();
        //else if (status.equals("die") && x < 1000) {
        //    bomberDie.play();
       //     ondie();
      //  }
    }

    public void ondie() {
                animate += Sprite.DEFAULT_SIZE/10;
                img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, Sprite.DEFAULT_SIZE).getFxImage();
                count++;
                if(count == 40) {
                    numOfLives--;
                    if (numOfLives > 0) {
                        img = Sprite.player_right.getFxImage();
                    this.x = 32;
                    this.y = 32;
                    status = "alive";
                    count = 0; }
                    else {
                        this.x = 10000;
                    }
                }
    }
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE-10, Sprite.SCALED_SIZE);
    }
    public void setRow() {
        if (y % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
            y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
            y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE);
        }
    }

    public void setColumn() {
        if (x % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
            x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (this.x % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
            x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE);
        }
    }
    public void goLeft() {
            this.x -= speed;
            if (checkBrick() || checkWall() || checkBomb()) {
                this.x += speed;
                setRow();
             }
    }

    public void goRight() {
            this.x += speed;
            if (checkBrick() || checkWall() || checkBomb())  {
                this.x -= speed;
                setRow();
            }
    }

    public void goUp() {
            this.y -= speed;
            if (checkBrick() || checkWall() || checkBomb())  {
                this.y += speed;
                setColumn();
             }
    }

    public void goDown() {
            this.y += speed;
            if (checkBrick() || checkWall() || checkBomb())  {
                this.y -= speed;
                setColumn();
            }
    }

    private boolean checkDuplicateBomb(Bomb bomb) {
        for (Bomb b : this.bombs) {
            if (b.getX() == bomb.getX() && b.getY() == bomb.getY()) {
                return true;
            }
        }
        return false;
    }

    public void putBomb() {
        int xBomb, yBomb;
        if (getX() % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 3) {
            xBomb = (int) ((getX() / Sprite.SCALED_SIZE) + 1);
        } else {
            xBomb = (int) (getX() / Sprite.SCALED_SIZE);
        }
        if (getY() % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 3) {
            yBomb = (int) ((getY() / Sprite.SCALED_SIZE) + 1);
        } else {
            yBomb = (int) (getY() / Sprite.SCALED_SIZE);
        }
        Bomb bomb = new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage());

        if (!this.checkDuplicateBomb(bomb)
                && getNumBombs() >= this.bombs.size() + 1) {
            this.bombs.add(bomb);
        }
    }



    public void move() {
        if(input.contains("A")) {
            //x-=1;
            goLeft();
            animate += Sprite.DEFAULT_SIZE / 10;
            img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            s = 'L';
        }
        if(input.contains("D")) {
            //x+=1;
            goRight();
            animate += Sprite.DEFAULT_SIZE / 10;
            img = Sprite.movingSprite(Sprite.player_right_1,Sprite.player_right_2,animate,Sprite.DEFAULT_SIZE).getFxImage();
            s = 'R';
        }
        if(input.contains("W")) {
            //y-=1;
            goUp();
            animate += Sprite.DEFAULT_SIZE / 10;
            img = Sprite.movingSprite(Sprite.player_up_1,Sprite.player_up_2,animate,Sprite.DEFAULT_SIZE).getFxImage();
            s = 'U';
        }
        if(input.contains("S")) {
            //y+=1;
            goDown();
            animate += Sprite.DEFAULT_SIZE / 10;
            img = Sprite.movingSprite(Sprite.player_down_1,Sprite.player_down_2,animate,Sprite.DEFAULT_SIZE).getFxImage();
            s = 'D';
        }
        if ( x <= 80 && x >=64 && y >= 96 && y <= 112) { x = 850; y = 352; }
        if ( x <= 848 && x >= 832 && y >= 352 && y <= 368) { x = 62 ; y = 96;}
        if (input.isEmpty()) {
            switch (s) {
                case 'L':
                    img = Sprite.player_left.getFxImage();
                    break;
                case 'R':
                    img = Sprite.player_right.getFxImage();
                    break;
                case 'U':
                    img = Sprite.player_up.getFxImage();
                    break;
                case 'D':
                    img = Sprite.player_down.getFxImage();
                    break;
            }
        }
    }

    @Override
    public boolean checkBomb() {
        for (Bomb e : bomberman.bombs) {
            double diffX = this.getX() - e.getX();
            double diffY = this.getY() - e.getY();
            if (!(diffX > -32 && diffX < 32 && diffY > -32 && diffY < 32)) {
                e.passThrough = false;
            }
            if (e.passThrough) return false;
            if (this.intersects(e)) return true;

        }
        for (Bomb e : bombergirl.bombs) {
            double diffX = this.getX() - e.getX();
            double diffY = this.getY() - e.getY();
            if (!(diffX > -32 && diffX < 32 && diffY > -32 && diffY < 32)) {
                e.passThrough = false;
            }
            if (e.passThrough) return false;
            if (this.intersects(e)) return true;
        }
        return false;
    }
    public int getNumBombs() {
        return numBombs;
    };
}
