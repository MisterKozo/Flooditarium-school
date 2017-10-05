package xyz.misterkozo.flooditarium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {
    private TextView tv_offlines;
    private ListView listView;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private ArrayList<Score> scores;
    private static ScoreListAdapter scoreListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        DatabaseHandler db = new DatabaseHandler(this);
        this.listView = (ListView) findViewById(R.id.list_score);

        scores = new ArrayList<>();

        //settings = getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        //editor = this.settings.edit();

        //this.tv_offlines = (TextView) findViewById(R.id.tv_offlines);

        if (getIntent().hasExtra("date") && getIntent().hasExtra("score")) {
            int score = getIntent().getExtras().getInt("score");
            String date = getIntent().getExtras().getString("date");
            Score scoreObj = new Score(date, score);
            db.addScore(scoreObj);
        }

        //String scores = "";

        for (int i = 0; i < db.getAllScores().size(); i++) {
            Score tmp = db.getAllScores().get(i);
            //scores += (i+1) + ". At " + tmp.getDate() + " with " + tmp.getScore() + "\n";

            this.scores.add(tmp);

            if (i == 9)
                i = db.getAllScores().size();
        }

        //if (scores == "")
        //    scores = "No scores. Data has either never existed or has been erased.";

        this.scoreListAdapter = new ScoreListAdapter(this.scores, getApplicationContext());
        this.listView.setAdapter(this.scoreListAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Score score = scores.get(position);
                Toast.makeText(getApplicationContext(), String.valueOf(score.getScore()), Toast.LENGTH_LONG).show();
            }
            });
        //tv_offlines.setText(scores);
    }

    public void scores_back(View v) { finish(); }
}
