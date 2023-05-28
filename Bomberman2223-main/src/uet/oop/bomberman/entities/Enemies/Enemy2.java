package uet.oop.bomberman.entities.Enemies;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.createGame.Management.*;


// ONEAL : đặc điểm : nếu nó thấy bạn, nó đi về phía bạn.
public class Enemy2 extends Enemy1 {
    int countupdownspeed = 75;
    protected int speed = 1;
    public Enemy2(int x, int y, Image img) {
    super(x,y,img);
    }


    public void update() {
        countupdownspeed--;
        if (countupdownspeed == 0) {
            speed = speed == 1 ? 3 : 1;
            countupdownspeed = 75;
        }
        if (this.status.equals("alive")) move();
        else if (this.status.equals("die")) onDie();
    }







    // Check Player is arround
    public Rectangle2D getBoundarybyRow() {
        return new Rectangle2D(x-48 , y+1 , Sprite.SCALED_SIZE + 96 , Sprite.SCALED_SIZE - 2 );
    }
    public boolean intersectsPlayerbyRow(Entity s) {
        return this.getBoundarybyRow().intersects(s.getBoundary());
    }
    public Rectangle2D getBoundarybyColumn() {
        return new Rectangle2D(x+1 , y-32 , Sprite.SCALED_SIZE -2 , Sprite.SCALED_SIZE + 64);
    }


    @Override
    public void onDie() {
        if (this.count_die > 20) { img = Sprite.oneal_dead.getFxImage(); count_die--; }
        else super.onDie();
    }

    void move() {
        if(intersectsPlayerbyRow(bomberman)) {
            if (bomberman.getX() < this.x) {
                ranNum = 1;
            } else {
                ranNum = 2;
            }
        }
            else if (intersectsPlayerbyRow(bombergirl)) {
                if (bombergirl.getX() < this.x) { ranNum = 1;  }
                else { ranNum = 2;  }
            }

        if (getDirect()==1) {
            x -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,Sprite.oneal_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==2 ) {
            x += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,Sprite.oneal_right3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()==3 ) {
            y += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,Sprite.oneal_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (getDirect()== 4 ) {
            y -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,Sprite.oneal_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }
}
