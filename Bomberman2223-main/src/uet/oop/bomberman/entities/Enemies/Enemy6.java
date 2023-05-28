package uet.oop.bomberman.entities.Enemies;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.ai.PathFinder;

import uet.oop.bomberman.graphics.Sprite;


import static uet.oop.bomberman.BombermanGame.gc;
import static uet.oop.bomberman.createGame.Management.bombergirl;
import static uet.oop.bomberman.createGame.Management.bomberman;


public class Enemy6 extends Enemy1{
    int speed = 2;
    boolean drawPath = true;
    public int direction;
    PathFinder finder = new PathFinder();
    public boolean onPath = false;
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x+2, y+2, 30, 30);
    }
    public Enemy6(int x, int y, Image img) {
        super(x, y, img);
        onPath = true;
        finder.whofind = "doll";
    }

    public void update() {

        if(onPath == true && this.status == "alive"   ) {
            int goalCol = (int)bomberman.getX() / 32 ;
            int goalRow = (int)bomberman.getY() / 32 ;
            // int goalCol = coinsStack.peek().getX()/ 32 ;
            // int goalRow = coinsStack.peek().getY() / 32 ; // ham an coin AI
            searchPath(goalCol,goalRow);
            automove();
        }
        else if (this.status.equals("die")) onDie();
        else move();
        if(drawPath == true) {
            for (int i = 0; i < finder.pathList.size();i++) {
                int worldX = finder.pathList.get(i).col * 32;
                int worldY = finder.pathList.get(i).row * 32;
                gc.strokeRect(worldX,worldY,32,32);
                //gc.fillRect(screenX,screenY,32,32);
            }
        }

    }
    //Thuat toan tim duong



    @Override
    public void onDie() {
        if (count_die > 20)
        {img = Sprite.doll_dead.getFxImage(); count_die--;}
        else super.onDie();
    }
    void automove() {  // duoi day la di chuyen random

        if (direction==1 ) { // left
            x -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2,Sprite.doll_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction==2 ) { // right
            x += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2,Sprite.doll_right3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction==3 ) { // down
            y += speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2,Sprite.doll_left3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }
        if (direction== 4 ) { // up
            y -= speed;
            animate += Sprite.DEFAULT_SIZE/10;
            img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2,Sprite.doll_right3, animate, Sprite.DEFAULT_SIZE).getFxImage();
        }

        /*for (int i = 0; i < items.size(); i++) { // ham an coin AI
            if (items.get(i) instanceof Coins && this.intersects(items.get(i)))
            {   items.remove(items.get(i));
                while (!items.contains(coinsStack.peek()))
                    coinsStack.pop();
            }
        }*/

    }
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (int)(this.x+2)/32;
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
            /* if reached the goal
            int nextCol = finder.pathList.get(0).col;
            int nextRow = finder.pathList.get(0).row;
            //System.out.println(nextCol);
            // System.out.println(nextRow);
            if (nextCol == goalCol && nextRow == goalRow) {
              //onPath = false;
               // items.remove(coinsStack.peek());
                //coinsStack.pop();
            }*/
        }
    }
    protected int getDirect() {
        if (this.checkWall() || this.checkBrick() || this.checkBomb())
        {
            switch (direction) {
                case 1 : x += speed; supportRow(); break;
                case 2 : x -= speed ; supportRow(); break;
                case 3 : y -= speed;supportColumn(); break;
                case 4 : y += speed;supportColumn(); break;
            }
            direction = rand.nextInt(4)+1;}
        return direction;
    }
}