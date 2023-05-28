package uet.oop.bomberman.ai;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.createGame.Management;

import java.util.ArrayList;

public class PathFinder {
    public String whofind = "";
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder() {
        instantiateNodes();
    }
    public void instantiateNodes() {
        node = new Node[31][13];
        int col = 0;
        int row = 0;
        while(col < 31 && row < 13) {
    node[col][row] = new Node(col,row);
    col++;
    if (col == 31) {
        col = 0;
        row++;
    }
        }
    }
    public void resetNodes() {
        int col = 0;
        int row = 0;
        while(col < 31 && row < 13) {
            //reset open,checed and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if (col == 31) {
                col = 0;
                row++;
            }
        }
        //reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol,int startRow,
                         int goalCol, int goalRow, Entity entity) {
        resetNodes();
        // set start and goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        //set solid node
        for(Entity e : Management.walls) {
           node[(int)e.getX()/32][(int)e.getY()/32].solid = true;
        }

            if (whofind.equals("girl")) {
                for(Entity e : Management.enemy)
                node[(int) e.getX() / 32][(int) e.getY() / 32].solid = true;
            }

        for(Entity e : Management.bricks) {
            node[(int)e.getX()/32][(int)e.getY()/32].solid = true;
        }
        int col = 0;
        int row = 0;
        while(col < 31 && row < 13 ) {
            //reset open,checed and solid state
            // set cost
            getCost(node[col][row]);
            col++;
            if (col == 31) {
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node) {
        // get G cost ( the distance from the start node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // get H cost (the distance from the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // get F cost ( the total cost)
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {

        while(goalReached == false && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            //check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the up node
            if (row-1 >= 0)
                openNode(node[col][row-1]);
            // open the left node
            if (col-1 >= 0)
                openNode(node[col-1][row]);
            // open the down node
            if (row+1 < 13)
                openNode(node[col][row+1]);
            // open the right node
            if (col+1 < 31)
                openNode(node[col+1][row]);
        //find the best node
        int bestNodeIndex = 0;
        int bestNodefCost = 999;

        for (int i = 0; i < openList.size();i++) {
            //check if the node's F cost is better
            if (openList.get(i).fCost < bestNodefCost) {
                bestNodeIndex = i;
                bestNodefCost = openList.get(i).fCost;
            }
            // if F cost is equal, then check the G cost
            else if (openList.get(i).fCost == bestNodefCost) {
                if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                    bestNodeIndex = i;
                }
            }
        }
            // if there is so node in the openlist end the loop
            if (openList.size() == 0) {
                break;
            }
        // After the loop, we get the best node which is our next step
        currentNode = openList.get(bestNodeIndex);

        if(currentNode == goalNode) {
            goalReached = true;
            trackThePath();
        }
            step++;
    }
    return goalReached;
}
    public void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while(current!= startNode) {
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
