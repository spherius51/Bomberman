package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.createGame.CreateMap.coinsStack;
import static uet.oop.bomberman.createGame.Management.*;

public class Coins extends  Item {
    public Coins(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {

            animate += Sprite.DEFAULT_SIZE / 11;
            this.img = Sprite.movingSprite(Sprite.coin_left1, Sprite.coin_left2, Sprite.coin_left3, animate / 2, Sprite.DEFAULT_SIZE).getFxImage();
            if (animate == 200) animate = 1;
            if (this.intersects(bomberman)) {
                BombermanGame.collectItem.play();
                items.remove(this);
            }
            if (this.intersects(bombergirl)) {
                BombermanGame.collectItem.play();
                items.remove(this);;
            }
        if(coinsStack.size()>0) {
            if (!items.contains(coinsStack.peek()) )
                coinsStack.pop();
        }
    }
}

