package game.ky.snake;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public class Point{
        public int x,y;
        Point(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    public final int BWIDTH = 50;
    public final int BHEIGHT = 50;
    List<Point> snake = new ArrayList<>();
    int board[][] = new int[BHEIGHT][BWIDTH];
    public static final int SNAKEBODY = 1;
    public static final int FOOD = 2;
    public static final int NONE = 0;
    public static final int ILLEAGL = -1;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    int[][] directionMap = {{0,-1},{1, 0},{0,1},{-1,0}};
    int snakeDir = 1;

    String TAG = "SnakeGame";
    void setBody(Point p,int val){
        board[p.y][p.x] = val;
    }
    public void start(){
        Point p = new Point(1,1);
        snake.add(p);
        setBody(p,1);
    }
    public void generateFood(){
        while(true) {
            int x = (int) (Math.random() * BWIDTH);
            int y = (int) (Math.random() * BHEIGHT);
            if (board[y][x] == 0) {
                Log.d(TAG,"gen food " + x + " " + y);
                board[y][x] = FOOD;
                break;
            }
            //Todo(1) if board is full
        }
    }
    public boolean move(){
        Point head = new Point(snake.get(0).x,snake.get(0).y);

        head.x += directionMap[snakeDir][0];
        head.y += directionMap[snakeDir][1];

        if(head.y < 0 || head.x < 0 || head.y >= BHEIGHT || head.x >= BWIDTH) {
            return false;
        }


        if(board[head.y][head.x] != FOOD) {
            int lastIdx = snake.size() - 1;
            setBody(snake.get(lastIdx), 0);
            snake.remove(lastIdx);
        }else {
            generateFood();
        }
        snake.add(0,head);
        setBody(head,1);

        return true;
    }
    public void changeDirection(int direction){
        Point head = new Point(snake.get(0).x,snake.get(0).y);

        head.x += directionMap[direction][0];
        head.y += directionMap[direction][1];
        if(board[head.y][head.x] == SNAKEBODY) {
            return ;
        }
        this.snakeDir = direction;
    }
    public Point getBodyPostition(){
        return snake.get(0);
    }
    public void eatFood(Point food){
        snake.add(0,food);
    }
    public int[][] getBoard(){
        return board;
    }

    public List<Point> getSnake() {
        return snake;
    }
}
