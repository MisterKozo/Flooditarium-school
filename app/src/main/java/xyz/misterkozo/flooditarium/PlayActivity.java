package xyz.misterkozo.flooditarium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class PlayActivity extends AppCompatActivity {
    private PlayView playView;
    private int difficulty, size, colors;
    private String seed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("seed")) {
            Log.i("dardale", getIntent().getExtras().getString("seed"));
            this.seed = getIntent().getExtras().getString("seed");
            Log.i("dardale", this.seed);
            this.playView = new PlayView(this, this.seed);
        } else {
            this.difficulty = getIntent().getExtras().getInt("difficulty");
            this.size = getIntent().getExtras().getInt("size");
            this.colors = getIntent().getExtras().getInt("colors");
            this.playView = new PlayView(this, this.difficulty, this.size, this.colors);
        }
        setContentView(this.playView);

        /*Intent svc=new Intent(this, BackgroundSoundService.class);
        stopService(svc);*/
    }
}
