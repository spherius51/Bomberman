package uet.oop.bomberman.createGame;

import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.players.Bomber;
import uet.oop.bomberman.entities.players.Bombergirl;
import uet.oop.bomberman.entities.tiles.Bomb;
import uet.oop.bomberman.entities.tiles.Flame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Management {
    public static List<Bomber> bombers = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Entity> walls = new ArrayList<>();
    public static List<Entity> doors = new ArrayList<>();
    public static List<Entity> portals = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    public static List<Enemy1> enemy = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List <Flame> flamesvisual = new ArrayList<>();
    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static Bombergirl bombergirl = new Bombergirl(1, 11, Sprite.player_right.getFxImage());

    public static void removeBomb() {
        try{
            bomberman.bombs.removeIf(Bomb::isExploded);
            bombergirl.bombs.removeIf(Bomb::isExploded);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clear() {
        bombers.clear();
        grasses.clear();
        walls.clear();
        portals.clear();
        enemy.clear();
        items.clear();
        bricks.clear();
        flamesvisual.clear();
        doors.clear();
        bomberman.numOfLives = 3;
        bombergirl.numOfLives = 3;
        bomberman.speed = 2;
        bombergirl.speed = 2;
    }


}
