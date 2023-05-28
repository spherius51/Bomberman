package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import static uet.oop.bomberman.createGame.Management.*;

public class MoreLives extends Item{
    public MoreLives(int x, int y, Image img) {
        super(x,y,img);
    }

    public void update() {
        if(this.intersects(bomberman)) {
            BombermanGame.collectItem.play();
            bomberman.numOfLives ++;
            System.out.println("Boy has " + bomberman.numOfLives + " heart");
            items.remove(this);
        }
        if(this.intersects(bombergirl)) {
            BombermanGame.collectItem.play();
            bombergirl.numOfLives ++;
            System.out.println("Girl has " + bombergirl.numOfLives + " heart");
            items.remove(this);
        }
    }
}
