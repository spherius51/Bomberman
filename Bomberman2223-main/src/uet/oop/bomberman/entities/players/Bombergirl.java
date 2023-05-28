package uet.oop.bomberman.entities.players;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.PathFinder;
import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.gc;
import static uet.oop.bomberman.createGame.CreateMap.coinsStack;
import static uet.oop.bomberman.createGame.Management.*;
import static uet.oop.bomberman.createGame.Management.bomberman;


public class Bombergirl extends Bomber {


    boolean drawPath = true;
    char s;
    int state = 1;
    public int direction;
    PathFinder finder = new PathFinder();
    public boolean onPath = false;
    public javafx.geometry.Rectangle2D getBoundary() {
        return new Rectangle2D(x+2, y+2, 28, 30);
    }
    public  String status = "alive";

    public static boolean checkAI = false;
    private static int count = 0;
    public int numOfLives = 3;
    public Bombergirl(int x, int y, Image img) {

        super( x, y, img);
        onPath = true;
        finder.whofind = "girl";
       // System.out.println(this.status);
    }


    public int speed = 2;



    public void update() {
        for (Enemy1 e : enemy) {
            if (e.intersects(bombergirl)) this.status = "die";
        }
          if(checkAI) {
              if (this.intersects(bomberman)) bomberman.status = "die";
              if (onPath == true && coinsStack.size() != 0) {
                  //int goalCol = (int)bomberman.getX() / 32 ;
                  //int goalRow = (int)bomberman.getY() / 32 ;
                  int goalCol = coinsStack.peek().getX() / 32;
                  int goalRow = coinsStack.peek().getY() / 32; // ham an coin AI
                  searchPath(goalCol, goalRow);
                  automove();
              } else {
                  System.out.println("an het tien");
                  //System.exit(0);
              }
              if (drawPath == true) {
                  for (int i = 0; i < finder.pathList.size(); i++) {
                      int worldX = finder.pathList.get(i).col * 32;
                      int worldY = finder.pathList.get(i).row * 32;
                      gc.setStroke(Color.DARKBLUE);
                      gc.strokeRect(worldX, worldY, 32, 32);
                  }
              }
          }
          else
            move();
        //System.out.println(this.status);
         //if (status.equals("die")) ondie();
    }
    //Thuat toan tim duong

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (int)(this.x+5)/32;
        int startRow = (int)(this.y+10)/32;
        finder.setNodes(startCol,startRow,goalCol,goalRow,this);
        if (finder.search() == true) {
            // Next x and y
            int nextX = finder.pathList.get(0).col * 32;
            int nextY = finder.pathList.get(0).row * 32;
            // Entity's solidArea position
            int enLeftX = this.x+2;
            int enRightX = this.x + 32 ;
            int enTopY = y+2;
            int enBottomY = y +30;

            // thuat toan duoi theo dang co van de
            if(enTopY > nextY && enLeftX >= nextX
                    && enRightX < nextX + 32) {
                direction = 4;
            }
            else if(enTopY < nextY && enLeftX >= nextX
                    && enRightX < nextX + 32) {
                direction = 3;
            }
            else if(enTopY > nextY && enBottomY < nextY + 32) {
                // left or right
                if (enLeftX > nextX) {
                    direction = 1;
                }
                if (enLeftX < nextX) {
                    direction = 2;
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = 4;
                if (this.checkWall() || checkBrick() || checkBomb()) {
                    direction = 1;
                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                direction = 4;
                if(this.checkWall() || checkBrick() || checkBomb()) {
                    direction =2;
                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = 3;
                if(checkWall()|| checkBrick() || checkBomb()) {
                    direction = 1;
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = 3;
                if(checkWall() || checkBrick() || checkBomb()) {
                    direction = 2;
                }
            }
            // if reached the goal
            int nextCol = finder.pathList.get(0).col;
            int nextRow = finder.pathList.get(0).row;
            //System.out.println(nextCol);
            // System.out.println(nextRow);
            if (nextCol == goalCol && nextRow == goalRow) {
                //onPath = false;
                // items.remove(coinsStack.peek());
                //coinsStack.pop();
                if (coinsStack.size() == 0) onPath = false;
            }
        }


    }
    public void move() {
        if(BombermanGame.input.contains("LEFT")) {
            //x-=1;
            goLeft();
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_left_1, Sprite.spider_left_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            s = 'L';
        }
        if(BombermanGame.input.contains("RIGHT")) {
            //x+=1;
            goRight();
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_right_1, Sprite.spider_right_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            s = 'R';
        }
        if(BombermanGame.input.contains("UP")) {
            //y-=1;
            goUp();
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_up_1, Sprite.spider_up_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            s = 'U';
        }
        if(BombermanGame.input.contains("DOWN")) {
            //y+=1;
            goDown();
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_down_1, Sprite.spider_down_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
            s = 'D';
        }
        if ( x <= 80 && x >=64 && y >= 96 && y <= 112) { x = 850; y = 352; }
        if ( x <= 848 && x >= 832 && y >= 352 && y <= 368) { x = 62 ; y = 96;}
        if (BombermanGame.input.isEmpty()) {
            switch (s) {
                case 'L':
                    img = Sprite.spider_left.getFxImage();
                    state = 1;
                    break;
                case 'R':
                    img = Sprite.spider_right.getFxImage();
                    state = 1;
                    break;
                case 'U':
                    img = Sprite.spider_up.getFxImage();
                    state = 1;
                    break;
                case 'D':
                    img = Sprite.spider_down.getFxImage();
                    state = 1;
                    break;
            }
        }
    }
    public void ondie() {
        state++;
        img = Sprite.movingSprite(Sprite.spider_dead1, Sprite.spider_dead2, Sprite.spider_dead3, 15 + state, 3 + state).getFxImage();
        count++;
        if(count == 40) {
            numOfLives--;
            if (numOfLives > 0)
            {img = Sprite.spider_right.getFxImage();
            this.x = 32;
            this.y = 32 * 11;
            status = "alive";
            this.count = 0;}
            else {
                this.x = 1000000;
            }
        }
    }
    public void automove() {  // duoi day la di chuyen random
        if (direction==1 ) { // left
            x -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_left_1, Sprite.spider_left_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction==2 ) { // right
            x += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_right_1, Sprite.spider_right_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction==3 ) { // down
            y += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_down_1, Sprite.spider_down_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction== 4 ) { // up
            y -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.spider_up_1, Sprite.spider_up_2, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }

}
