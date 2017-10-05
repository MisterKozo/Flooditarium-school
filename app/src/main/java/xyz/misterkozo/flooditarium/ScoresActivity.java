package xyz.misterkozo.flooditarium;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import  android.app.AlertDialog;
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
    private Context context;
    private Score score;
    private int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        this.context = getApplicationContext();
        DatabaseHandler db = new DatabaseHandler(this);
        this.listView = (ListView) findViewById(R.id.list_score);

        scores = new ArrayList<>();

        settings = getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        //editor = this.settings.edit();

        //this.tv_offlines = (TextView) findViewById(R.id.tv_offlines);

        if (getIntent().hasExtra("date") && getIntent().hasExtra("score")) {
            int score = getIntent().getExtras().getInt("score");
            String date = getIntent().getExtras().getString("date");
            String player = getIntent().getExtras().getString("player");
            String seed = getIntent().getExtras().getString("seed");
            Score scoreObj = new Score(date, score, player, seed);
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
                pos = position;
                score = scores.get(position);
                //Intent score_play = new Intent(context, PlayActivity.class);
                //score_play.putExtra("seed", score.getSeed());
                //startActivity(score_play);
                String question = "Do you want to replay " + score.getPlayer() + "'s game?";
                //lertDialog = new AlertDialog.Builder(this).create();
                AlertDialog.Builder builder = new AlertDialog.Builder(ScoresActivity.this);
                builder.setTitle(getString(R.string.leaveTitle))
                        .setMessage(question)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent score_play = new Intent(context, PlayActivity.class);
                                score_play.putExtra("seed", score.getSeed());
                                startActivity(score_play);
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss
                            }
                        })
                        .setIcon(R.drawable.icon)
                        .show();
                /*AlertDialog.Builder adb = new AlertDialog.Builder(
                        ScoresActivity.this);
                adb.setTitle("List");
                adb.setMessage(" selected Item is="
                        +parent.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();*/
                //Toast.makeText(getApplicationContext(), String.valueOf(score.getScore()), Toast.LENGTH_LONG).show();
            }
            });
        //tv_offlines.setText(scores);
    }

    public void scores_back(View v) { finish(); }
}
