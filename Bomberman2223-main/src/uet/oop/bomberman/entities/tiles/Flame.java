package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.createGame.Management;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.createGame.Management.*;


public class Flame extends Entity {
    public int direction;
    int count_destroy = 30;
    public Flame(int x, int y, Image img,int direction) {
        super(x,y,img);
        this.direction = direction;

    }
    @Override
    public void update() {
        if (direction == 0) {
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_horizontal1,Sprite.explosion_horizontal2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        else if (direction == 1) {
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_vertical1,Sprite.explosion_vertical2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        else if (direction == 2) { // last left
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last1,
                    Sprite.explosion_horizontal_left_last2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        else if (direction == 3) { // last right
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last1,
                    Sprite.explosion_horizontal_right_last2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        else if (direction == 5) { // last right
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_vertical_top_last1,
                    Sprite.explosion_vertical_top_last2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        else if (direction == 4) { // last right
            animate+= Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.explosion_vertical_down_last1,
                    Sprite.explosion_vertical_down_last2,animate,Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (this.checkWall()){
            x = 1000;
            for (Entity e : Management.bricks) {
                if (this.intersects(e)) {
                    ((Brick) e).setStatus("destroy");
                }
            }
        }
        for (Entity e : Management.enemy) {
            if (this.intersects(e)) {
                if (e instanceof Enemy1) ((Enemy1) e).status = "die";
            }
        }
        for (Entity e : Management.bricks) {
            if (this.intersects(e)) {
                ((Brick) e).setStatus("destroy");
            }
        }
        if(this.intersects(bomberman)) bomberman.status = "die";
        if(this.intersects(bombergirl)) bombergirl.status = "die";
    }
}
