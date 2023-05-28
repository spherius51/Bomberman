package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;


public class Grass extends Entity {
    public boolean isCovered = true;
    public Grass(int x, int y, Image img, boolean isCovered) {
        super(x, y, img); this.isCovered = isCovered;
    }

    @Override
    public void update() {

    }

}
