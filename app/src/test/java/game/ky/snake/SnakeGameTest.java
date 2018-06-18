package game.ky.snake;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnakeGameTest {
    @Test
    public void snake_move() {
        Game game = new Game();
        game.start();
        game.move();
        Game.Point p = game.getBodyPostition();
        assertEquals(p.x,2);
        assertEquals(p.y,1);
    }
    @Test
    public void snake_move2() {
        Game game = new Game();
        game.start();
        Game.Point p = new Game(). new Point(2,1);
        game.eatFood(p);
        game.move();
        p = game.getBodyPostition();
        assertEquals(p.x,3);
        assertEquals(p.y,1);
    }
    @Test
    public void snake_change_direction() {
        Game game = new Game();
        game.start();
        game.changeDirection(Game.UP);
        game.move();
        Game.Point p = game.getBodyPostition();
        assertEquals(p.x,1);
        assertEquals(p.y,0);


        game.changeDirection(Game.LEFT);
        game.move();

        assertEquals(p.x,0);
        assertEquals(p.y,0);

        game.changeDirection(Game.DOWN);
        game.move();
        assertEquals(p.x,0);
        assertEquals(p.y,1);

        game.changeDirection(Game.RIGHT);
        game.move();
        assertEquals(p.x,1);
        assertEquals(p.y,1);
    }
    @Test
    public void snke_eat_food(){
        Game game = new Game();
        Game.Point p = new Game(). new Point(1,2);
        game.start();
        game.eatFood(p);
        p = new Game(). new Point(2,2);
        game.eatFood(p);

    }
}
