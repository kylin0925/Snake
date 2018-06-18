package game.ky.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    SurfaceHolder surfaceHolder;
    ScheduledExecutorService scheduledExecutorService;
    Game game = new Game();
    String TAG = "SnakeMain";
    Paint backgroud;
    Paint bodycolor;
    Paint foodcolor;
    Button btnUp;
    Button btnDown;
    Button btnLeft;
    Button btnRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GameView gameView = new GameView(this);
        setContentView(R.layout.activity_main);
        //setContentView(gameView);


        /*final GameView gameView = (GameView)findViewById(R.id.Gameview);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    gameView.drawBoard();
                    gameView.invalidate();
                    try{
                        Log.e(TAG,"e..." );
                        Thread.sleep(100);
                    }catch (Exception ex){
                        Log.e(TAG,"error " + ex);
                    }
                }
            }
        });
        thread.start();*/
        backgroud = new Paint();
        backgroud.setColor(Color.BLUE);

        bodycolor = new Paint();
        bodycolor.setColor(Color.BLUE);

        foodcolor = new Paint();
        foodcolor.setColor(Color.RED);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        game.start();
        game.generateFood();
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();

                canvas.drawRect(new Rect(10,10,100,100),backgroud);
                holder.unlockCanvasAndPost(canvas);

                scheduledExecutorService.scheduleAtFixedRate(
                        new Runnable() {
                            @Override
                            public void run() {
                               // Log.d(TAG,"update");

                                drawBoard();
                                game.move();
                                try{
                                    Thread.sleep(100);
                                }catch (Exception ex){

                                }
                            }
                        },1000,160, TimeUnit.MICROSECONDS
                );
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        btnUp = (Button)findViewById(R.id.btnUp);
        btnDown = (Button)findViewById(R.id.btnDown);
        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.changeDirection(Game.UP);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.changeDirection(Game.DOWN);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.changeDirection(Game.LEFT);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.changeDirection(Game.RIGHT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void drawBoard(){
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int gridWidth = width/game.BWIDTH;
        int gridHeight = gridWidth;

        int [][] board = game.getBoard();

        for(int i = 0 ; i <game.BHEIGHT;i ++){
            for(int j = 0 ; j <game.BWIDTH;j ++) {
                if(board[i][j]==Game.SNAKEBODY) {
                    Log.d(TAG,"x " + j * gridWidth + " y " + i * gridWidth + " ");
                    canvas.drawRect(new Rect(j * gridWidth, i * gridWidth,
                            j * gridWidth+gridWidth, i * gridWidth+gridHeight), bodycolor);
                }else if(board[i][j]==Game.FOOD){
                    canvas.drawRect(new Rect(j * gridWidth, i * gridWidth,
                            j * gridWidth+gridWidth, i * gridWidth+gridHeight), foodcolor);
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
}
