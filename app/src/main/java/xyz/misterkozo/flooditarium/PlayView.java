package xyz.misterkozo.flooditarium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.misterkozo.flooditarium.Flood.*;

public class PlayView extends View {
    private Board board;
    private Canvas canvas = null;
    private Context context = null;
    private int difficulty = 1, height = 0, width = 0, colors = 4;
    private int size = 25, one = 0;
    private int bsx = 0, bsy = 0, bex = 0, bey = 0;
    private int moves = 0;
    private boolean sound;
    private MediaPlayer mp;

    public PlayView(Context context, int difficulty, int size, int colors) {
        super(context);
        this.context = context;
        this.difficulty = difficulty;
        this.size = size;
        this.colors = colors;
        this.board = new Board(context, this.colors, this.size);

        SharedPreferences settings  = this.context.getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        this.sound = settings.getBoolean("sound", true);
        //invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        this.canvas = canvas;
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.bsx = this.width / (this.size + 2);
        this.bsy = this.bsx;
        this.bex = this.width - this.bsx;
        this.bey = this.bex;
        this.one = (bex - bsx) / size;

        //this.board.AfterThought(canvas, this.bsx, this.bsy, this.one);
        //this.board.PrepareCells();
        //this.board = new Board(this, )
        //this.board = new Board(this.context, canvas, this.bsx, this.bsy, this.one, this.colors, this.size);
        this.board.AfterThought(canvas, this.bsx, this.bsy, this.one);
        this.board.PrepareCells();
        this.board.PrepareControls();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case 0:
                //Log.i("loc", String.valueOf((int)x) + "," + String.valueOf((int)y));
                AnalTouch(x, y);
                break;
        }
        return true;
    }

    public void AnalTouch(float touchX, float touchY) {
        if (!this.board.IsSolved()) {
            int tX = (int) touchX;
            int tY = (int) touchY;
            boolean free = true;
            int unit = this.board.GetUnit();
            int division = this.board.GetDivision();
            Cell[] ctrl = this.board.GetCtrl();

            int sX, sY, eX, eY;
            for (int i = 0; i < colors; i++) {
                sX = ctrl[i].getPosX();
                eX = sX + unit * 3;
                sY = ctrl[i].getPosY();
                eY = sY + unit * 3;

                if (tY > sY && tY < eY && tX > sX && tX < eX) {
                    if (i != this.board.GetBoard()[0][0]) {
                        this.moves++;
                        if (this.sound) {
                            this.mp = MediaPlayer.create(this.context, R.raw.on);
                            this.mp.start();
                            this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });
                        }
                    }

                   // this.board.HitWith(0, 0, i, this.board.GetBoard()[0][0], null);
                    this.board.Flood(this.board.GetBoard()[0][0], i, null, 0,0);
                    i = colors;
                    invalidate();
                }
            }

            if (this.board.IsSolved()) {
                Float sf = (float) colors * 10000;
                sf = sf / (moves * size);
                int score = colors * 10000;
                score = score / (moves * size);
                Toast.makeText(this.context, "Congratulations! You have flooded the board in " + String.valueOf(this.moves) + " moves.", Toast.LENGTH_LONG).show();
                Intent scores_intent = new Intent(this.context, ScoresActivity.class);

                scores_intent.putExtra("score", score);//higher is better
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String now = dateFormat.format(date);
                scores_intent.putExtra("date", now);
                this.context.startActivity(scores_intent);
            }
        }
    }

}
