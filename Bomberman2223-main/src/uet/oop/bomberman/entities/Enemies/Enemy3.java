package uet.oop.bomberman.entities.Enemies;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.createGame.Management.bombergirl;
import static uet.oop.bomberman.createGame.Management.bomberman;

public class Enemy3 extends Enemy1 {
    public Enemy3(int x, int y, Image img) {
        super( x, y, img);
    }
    public void update() {
       if (this.status.equals("alive")) move();
        else if (this.status.equals("die")) onDie();
    }

    public Rectangle2D getBoundaryBIG() {
        return new Rectangle2D(x-48 , y-48 , Sprite.SCALED_SIZE + 96 , Sprite.SCALED_SIZE +96);
    }
    public boolean intersectsPlayer(Entity s) {
        return this.getBoundaryBIG().intersects(s.getBoundary());
    }
    @Override
    public void onDie() {
        if (this.count_die > 50) { img = Sprite.kondoria_dead.getFxImage(); count_die--; }
        else if (this.count_die > 0 )    super.onDie();
    }
    void move() {
        if (getDirect()==1 ) {
            if(intersectsPlayer(bomberman) == true )
            { if (x < bomberman.getX() ) ranNum = 2;
                if (x != bomberman.getX())
                x -= (this.x - bomberman.getX())/Math.abs(this.x - bomberman.getX());
                if (y != bomberman.getY()) y -= (this.y - bomberman.getY())/Math.abs(this.y - bomberman.getY()); }
            if(intersectsPlayer(bombergirl) == true )
            { if (x > bombergirl.getX()) ranNum = 2;
                if (x != bombergirl.getX())x += 2*(this.x - bombergirl.getX())/Math.abs(this.x - bombergirl.getX());
                if (y != bombergirl.getY()) y += (this.y - bombergirl.getY())/Math.abs(this.y - bombergirl.getY()); }
            x -= 1;
            state++;
            img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,Sprite.kondoria_left3, 10+state, 3 + state).getFxImage();
            if (state == 30) state = 1;
        }
        if (getDirect()==2 ) {
            if(intersectsPlayer(bomberman) == true )
            { if (x > bomberman.getX() ) ranNum = 1;
                else if (x != bomberman.getX()) x -= (this.x - bomberman.getX())/Math.abs(this.x - bomberman.getX());
                        if (y != bomberman.getY())  y -= (this.y - bomberman.getY())/Math.abs(this.y - bomberman.getY()); }
            if(intersectsPlayer(bombergirl) == true )
            {   if (x < bomberman.getX() ) ranNum = 1;
                if (x != bombergirl.getX()) x += (this.x - bombergirl.getX())/Math.abs(this.x - bombergirl.getX());
                if (y != bombergirl.getY()) y += (this.y - bombergirl.getY())/Math.abs(this.y - bombergirl.getY()); }
            x +=1;
            state++;
            img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,Sprite.kondoria_right3, 10+state, 3 + state).getFxImage();
            if (state == 30) state = 1;
        }
    }

    @Override
    protected int getDirect() {
        if (x <= 32 || x >= Sprite.SCALED_SIZE * 31 - 64  )
        ranNum = ranNum == 1 ? 2 : 1;
        return ranNum;
    }
}
