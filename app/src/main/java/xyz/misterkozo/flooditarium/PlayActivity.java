package xyz.misterkozo.flooditarium;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {
    private PlayView playView;
    private int difficulty, size, colors;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.difficulty = getIntent().getExtras().getInt("difficulty");
        this.size = getIntent().getExtras().getInt("size");
        this.colors = getIntent().getExtras().getInt("colors");
        this.playView = new PlayView(this, this.difficulty, this.size, this.colors);
        setContentView(this.playView);
    }
}
