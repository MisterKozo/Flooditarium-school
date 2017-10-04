package xyz.misterkozo.flooditarium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {
    TextView tv_offlines;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        DatabaseHandler db = new DatabaseHandler(this);

        //settings = getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        //editor = this.settings.edit();

        tv_offlines = (TextView) findViewById(R.id.tv_offlines);

        if (getIntent().hasExtra("date") && getIntent().hasExtra("score")) {
            int score = getIntent().getExtras().getInt("score");
            String date = getIntent().getExtras().getString("date");
            Score scoreObj = new Score(date, score);
            db.addScore(scoreObj);
        }

        String scores = "";

        for (int i = 0; i < db.getAllScores().size(); i++) {
            Score tmp = db.getAllScores().get(i);
            scores += (i+1) + ". At " + tmp.getDate() + " with " + tmp.getScore() + "\n";

            if (i == 9)
                i = db.getAllScores().size();
        }

        if (scores == "")
            scores = "No scores. Data has either never existed or has been erased.";

        tv_offlines.setText(scores);
    }

    public void scores_back(View v) { finish(); }
}
