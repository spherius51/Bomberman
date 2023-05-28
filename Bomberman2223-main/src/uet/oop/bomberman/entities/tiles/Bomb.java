package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.createGame.Management;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;


import static uet.oop.bomberman.createGame.Management.*;

public class Bomb extends Entity {
    List<Flame> flameLeft = new ArrayList<>();
    List<Flame> flameRight = new ArrayList<>();
    List<Flame> flameUp = new ArrayList<>();
    List<Flame> flameDown = new ArrayList<>();

    private int countBOMB = 75;
    public boolean settled = true;

    private boolean isExploded = false;
    public boolean passThrough = true;

    public Bomb(int x, int y, Image img){
        super(x, y ,img);
        for (int i = 0; i < 5; i++) {
            flameLeft.add(new Flame(1000, 1000, Sprite.explosion_horizontal.getFxImage(), 0));
            flameRight.add(new Flame(1000, 1000, Sprite.explosion_horizontal.getFxImage(), 0));
            flameUp.add(new Flame(1000, 1000, Sprite.explosion_vertical.getFxImage(), 1));
            flameDown.add(new Flame(1000, 1000, Sprite.explosion_vertical.getFxImage(), 1));
            flamesvisual.add(flameLeft.get(i));
            flamesvisual.add(flameRight.get(i));
            flamesvisual.add(flameUp.get(i));
            flamesvisual.add(flameDown.get(i));
        }
    }
    public void update() {
        if (settled) {
            if (countBOMB > 20) {
                animate += Sprite.DEFAULT_SIZE / 10;
                img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            }
            if (countBOMB > 0 && countBOMB <= 20) {
                animate += Sprite.DEFAULT_SIZE / 10;
                img = Sprite.movingSprite(Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, Sprite.SCALED_SIZE).getFxImage();

                for (int j = 0; j < bomberman.sizeOfFlame; j++)  {
                    if (j == bomberman.sizeOfFlame-1) flameLeft.get(j).direction = 2; // duoi flame ngang
                    flameLeft.get(j).setX(x-32 * (j+1));
                    flameLeft.get(j).setY(y);
                    if (flameLeft.get(j).checkWall())
                    {
                        break;
                    }
                }
                for (int j = 0; j < bomberman.sizeOfFlame; j++)  {
                    if (j == bomberman.sizeOfFlame-1) flameRight.get(j).direction = 3; //doc
                    flameRight.get(j).setX(x+32 * (j+1));
                    flameRight.get(j).setY(y);
                    if (flameRight.get(j).checkWall())
                    {
                        break;
                    }
                }
                for (int j = 0; j < bomberman.sizeOfFlame; j++)  {
                    if (j == bomberman.sizeOfFlame-1) flameUp.get(j).direction = 5;
                    flameUp.get(j).setX(x);
                    flameUp.get(j).setY(y-32 * (j+1));
                    if (flameUp.get(j).checkWall())
                    {
                        break;
                    }
                }
                for (int j = 0; j < bomberman.sizeOfFlame; j++)  {
                    if (j == bomberman.sizeOfFlame-1) flameDown.get(j).direction = 4;
                    flameDown.get(j).setX(x);
                    flameDown.get(j).setY(y+32 * (j+1));
                    if (flameDown.get(j).checkWall())
                    {
                        break;
                    }
                }
            }
            if (countBOMB == 0) {
                BombermanGame.boomExplosion.play();
                setExploded(true);
                Management.removeBomb();
                countBOMB = 200;
                img = Sprite.bomb.getFxImage();
                settled = false;
                for (int i = 0; i < bomberman.sizeOfFlame; i++) {
                    flamesvisual.remove(flameLeft.get(i));
                    flamesvisual.remove(flameRight.get(i));
                    flamesvisual.remove(flameDown.get(i));
                    flamesvisual.remove(flameUp.get(i));
                }
            }
            countBOMB--;
        }
    }
    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }
}


