package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import static uet.oop.bomberman.createGame.Management.*;

public class Speedup extends Item{
    public Speedup(int x, int y, Image img) {
        super(x,y,img);
    }
    public void update() {
        if(this.intersects(bomberman)) {
            BombermanGame.collectItem.play();
            bomberman.speed++;
            items.remove(this);
        }
        if(this.intersects(bombergirl)) {
            BombermanGame.collectItem.play();
            bombergirl.speed++;
            items.remove(this);
        }
    }
}
