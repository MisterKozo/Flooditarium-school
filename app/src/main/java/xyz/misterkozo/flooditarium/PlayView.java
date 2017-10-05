package xyz.misterkozo.flooditarium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.misterkozo.flooditarium.Flood.*;
import xyz.misterkozo.flooditarium.Utilities.Utilities;

public class PlayView extends View {
    private Board board;
    private Canvas canvas = null;
    private Context context = null;
    private int difficulty = 1, height = 0, width = 0, colors = 4;
    private int size = 25, one = 0;
    private int bsx = 0, bsy = 0, bex = 0, bey = 0;
    private int moves = 0;
    private int maxMoves = 0;
    private boolean sound;
    private MediaPlayer mp;
    private Drawable bt;
    private Paint scorePaint;
    private String scoreText;
    private int scoreWidth, scoreX, scoreY;

    public PlayView(Context context, int difficulty, int size, int colors) {
        super(context);
        this.context = context;
        this.difficulty = difficulty;
        this.size = size;
        this.colors = colors;
        this.board = new Board(context, this.colors, this.size);
        this.maxMoves = Math.round((this.colors * this.size)/4);

        SharedPreferences settings  = this.context.getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        this.sound = settings.getBoolean("sound", true);

        this.scorePaint = new Paint();
        this.scorePaint.setColor(Color.WHITE);
        this.scorePaint.setAntiAlias(true);
        this.scorePaint.setTextSize(16 * getResources().getDisplayMetrics().density);
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

        this.scoreText  = String.valueOf(this.moves) + "/" + String.valueOf(this.maxMoves);
        if (this.moves == this.maxMoves)
            this.scoreText = "FAILURE!";
        if (this.board.IsSolved())
            this.scoreText = "SUCCESS!";
        this.scoreWidth = (int) this.scorePaint.measureText(this.scoreText);
        this.scoreX = ((canvas.getWidth())/2)-(this.scoreWidth)/2;
        this.scoreY = this.bey + 2*this.one;
        canvas.drawText(this.scoreText, (float)this.scoreX, (float)this.scoreY, this.scorePaint);

        //this.board.AfterThought(canvas, this.bsx, this.bsy, this.one);
        //this.board.PrepareCells();
        //this.board = new Board(this, )
        //this.board = new Board(this.context, canvas, this.bsx, this.bsy, this.one, this.colors, this.size);
        this.board.AfterThought(canvas, this.bsx, this.bsy, this.one);
        this.board.PrepareCells();
        this.board.PrepareControls();

        int posY = this.board.GetCtrlPosY();

        bt = getResources().getDrawable(R.drawable.bt);
        bt.setBounds((width/2)-(width/8), bey + ((posY - bey)/2)-((posY-bey)/5), (width/2)+(width/8), ((posY - bey)/2)+ bey + ((posY-bey)/5));
        //bt.draw(this.canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case 0:
                //Log.i("loc", String.valueOf((int)x) + "," + String.valueOf((int)y));
                if (this.moves != this.maxMoves) {
                    AnalTouch(x, y);
                }
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
            //Log.i("dardale", "detected");

            int sX, sY, eX, eY;
            for (int i = 0; i < colors; i++) {
                sX = ctrl[i].getPosX();
                eX = sX + unit * 3;
                sY = ctrl[i].getPosY();
                eY = sY + unit * 3;
                //Log.i("dradale", "colooooo");

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
                        if (this.moves == this.maxMoves) {
                            //Toast.makeText(this.context, "FAILURE!", Toast.LENGTH_LONG).show();
                        }
                    }

                   // this.board.HitWith(0, 0, i, this.board.GetBoard()[0][0], null);
                    this.board.Flood(this.board.GetBoard()[0][0], i, null, 0,0);
                    i = colors;
                    invalidate();
                }
            }

            if (this.board.IsSolved()) {
                Float sf = (float)(this.maxMoves / this.moves)*100;
                int score = colors * 10000;
                score = (this.maxMoves^2 / this.moves)*100;
                Toast.makeText(this.context, "Congratulations! You have flooded the board in " + String.valueOf(this.moves) + " moves.", Toast.LENGTH_LONG).show();
                Intent scores_intent = new Intent(this.context, ScoresActivity.class);

                scores_intent.putExtra("score", score);//higher is better
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String now = dateFormat.format(date);
                scores_intent.putExtra("date", now);
                scores_intent.putExtra("seed", this.board.GetSeed());
                this.context.startActivity(scores_intent);
            }
        }

        if (bt.getBounds().contains((int)touchX, (int)touchY)) {
            String flat = Utilities.deflateBoard(this.board.GetBoard());
            /*try {
                //Create a file and write the String to it
                BufferedWriter out;
                final String filePath = Environment.getExternalStorageDirectory().getPath() + "/wadus.txt";
                FileWriter fileWriter = new FileWriter(filePath);
                out = new BufferedWriter(fileWriter);
                out.write("I know you'll love me for finding the solution");
                out.close();

                //Access the file and share it through the original intent
                File file = new File(filePath);
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                sendIntent.setType("text/plain");
                String title = "Share with…";

                //Create a file observer to monitor the access to the file
                FileObserver fobsv = new FileObserver(filePath) {
                    @Override
                    public void onEvent(int event, String path) {
                        if (event == FileObserver.CLOSE_NOWRITE) {
                            //The file was previously written to, now it's been sent and closed
                            //we can safely delete it.
                            File file = new File(filePath);
                            file.delete();
                        }
                    }
                };
                fobsv.startWatching();

                //Launch sharing intent
                startActivity(Intent.createChooser(sendIntent, title));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

}
