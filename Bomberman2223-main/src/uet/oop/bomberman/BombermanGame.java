package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.createGame.CreateMap;
import uet.oop.bomberman.createGame.Management;
import uet.oop.bomberman.entities.tiles.Bomb;
import uet.oop.bomberman.graphics.Sprite;


import java.util.*;
import java.util.List;

import uet.oop.bomberman.sound.Sound;


import static uet.oop.bomberman.createGame.CreateMap.*;
import static uet.oop.bomberman.createGame.Management.*;
import static uet.oop.bomberman.entities.players.Bombergirl.checkAI;


public class BombermanGame extends Application {
    // day la file cua dat1

    private Canvas canvas;
    int levelnow;
    boolean state = true;

    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static GraphicsContext gc;

    public static List<Entity> entities = new ArrayList<>();

    public static Scene scene;
    public Sound themeSong = new Sound("nhacnen");
    public Sound gameStart = new Sound("gameStart");
    public Sound boomSettle = new Sound("boomSettle");
    public static Sound enemyDie = new Sound("enemydie");

    public static Sound bomberDie = new Sound("bomberdie");
    public static Sound boomExplosion = new Sound("boomExplosion");

    public static Sound collectItem = new Sound("Item");
    public static Group root = new Group();
    static boolean started = false;
    public static ArrayList<String> input = new ArrayList<String>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(final Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);


        // Tao scene
        scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("BOMBERMAN GAME");
        welcomeGame(scene);
        stage.show();
        //ok



        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(started == true) {
                    render();
                    update();
                }
                if (bomberman.numOfLives == 0 && numOfplayer == 1) {
                    losingEndingScene(stage);
                }
                else if (bomberman.numOfLives == 0 && numOfplayer == 2 && bombergirl.numOfLives == 0){
                    losingEndingScene(stage);
                }
                else if (levelnow == 1  && numOfEnemy == 0 && bomberman.checkPortal() ) { // tao 1 portal de win
                    winningEndingScene(stage);
                                   }
                else if ((levelnow == 2) && numOfEnemy == 0 && bomberman.checkPortal() && bombergirl.checkPortal() ) { // tao 1 portal de win
                    winningEndingScene(stage);
                }
                else if ((levelnow == 3 && started && coinsStack.size() == 0) || bomberman.numOfLives == 0) {
                    losingEndingScene(stage);
                }
                else if (levelnow == 3 && started && bombergirl.numOfLives == 0) {
                    winningEndingScene(stage);
                }
            }
        };

        themeSong.loop();
        timer.start();

        /**
         * dieu khien di chuyen bomber.
         */
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (e.getCode().toString().equals("SPACE")  ){
                            // HAM DAT BOM
                            boomSettle.play();
                            bomberman.putBomb();
                        }

                        if (e.getCode().toString().equals("ENTER")) {
                            // HAM dat bom
                            boomSettle.play();
                            bombergirl.putBomb();
                        }
                        // only add once... prevent duplicates
                        if (!input.contains(code) && !code.equals("SPACE") && !code.equals("ENTER"))
                            input.add(code);
                    }
                });
        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

    }

    private void playGame()
    {
        state = false;
    }

    public void welcomeGame(Scene scene) {

        Image background = new Image("background.jpg"); // luc chay thi doi dia chi nay
        gc.drawImage(background,0,0,Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        scene.setOnMouseClicked(Mouseevent);
    }

       EventHandler Mouseevent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (started == false && e.getX() >= 515 && e.getX() <= 790 && e.getY() >= 315 && e.getY() <= 350)
            {   gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                CreateMap.createMapByLevel(2,2);
                themeSong.stop();
                gameStart.play();
                started = true;
                levelnow = 2;
            }
            if (started == false && e.getX() >= 175 && e.getX() <= 475 && e.getY() >= 315 && e.getY() <= 350)
            {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                CreateMap.createMapByLevel(1,1);
                themeSong.stop();
                gameStart.play();
                started = true;
                levelnow = 1;
            }
            if (started == false && e.getX() >= 408 && e.getX() <= 608 && e.getY() >= 375 && e.getY() <= 407)
            {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                checkAI = true;
                CreateMap.createMapByLevel(3,2);
                themeSong.stop();
                gameStart.play();
                started = true;
                levelnow = 3;
            }
            if (started == false && e.getX() >= 875 && e.getX() <= 975 && e.getY() >= 338 && e.getY() <= 413)
                System.exit(0);
        };
    };

    public void update() {
        Management.bombers.forEach(Entity::update);
        List<Bomb> another = new ArrayList<>();
        List<Bomb> another1 = new ArrayList<>();
        for (int i = 0; i < bomberman.bombs.size(); i++) {
            another.add(bomberman.bombs.get(i));
        }
        for (int i = 0; i < bombergirl.bombs.size(); i++) {
            another1.add(bombergirl.bombs.get(i));
        }
        another.forEach(Bomb::update);
        another1.forEach(Bomb::update);

        List<Enemy1> oneother = new ArrayList<>();
        for (int i = 0; i < enemy.size(); i++) {
            oneother.add(enemy.get(i));
        }
        oneother.forEach(Enemy1::update);
        List<Item> other = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            other.add(items.get(i));
        }
        other.forEach(Item::update);
        List<Entity> twoother = new ArrayList<>();
        for (int i = 0; i < bricks.size(); i++) {
            twoother.add(bricks.get(i));
        }
        twoother.forEach(Entity::update);

        flamesvisual.forEach(Entity::update);
    }
    //hien thi ket thuc neu thang
    public void winningEndingScene(Stage stage){
        Image end = new Image("win.jpg");
        gc.drawImage(end , 0, 0,Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        started = false;
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (e.getCode().toString().equals("R")  ){
                            restartGame(stage);
                        }

                        if (e.getCode().toString().equals("E")) { // HAM DAT BOM
                            System.exit(0);
                        }
                    }
                });
    }
    //hien thi ket thuc neu thua
    public void losingEndingScene(Stage stage){
        Image end = new Image("lose.jpg");
        gc.drawImage(end , 0, 0,Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        started = false;
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (e.getCode().toString().equals("R")  ){
                            restartGame(stage);
                        }

                        if (e.getCode().toString().equals("E")) {
                            System.exit(0);
                        }
                    }
                });
    }

    public void render() {
        Management.grasses.forEach(grass -> grass.render(gc));
        Management.items.forEach(g->g.render(gc));
        Management.walls.forEach(wall -> wall.render(gc));
        Management.doors.forEach(doors -> doors.render(gc));
        Management.portals.forEach(portal -> portal.render(gc));
        Management.bricks.forEach(g -> g.render(gc));
        Management.enemy.forEach(g -> g.render(gc));
        Management.bombers.forEach(g -> g.render(gc));
        Management.bomberman.bombs.forEach(g -> g.render(gc));
        Management.bombergirl.bombs.forEach(g -> g.render(gc));
        flamesvisual.forEach(g->g.render(gc));
    }

    public void restartGame(Stage stage) {
        playGame();
        stage.close();
        Management.clear();
        started = false;
        levelnow = 0;
        checkAI = false;
        Platform.runLater( () -> new BombermanGame().start( new Stage() ) );
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        GraphicsContext a = null;
        gc = a;
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
    }

}
