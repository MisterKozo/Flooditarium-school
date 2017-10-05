package xyz.misterkozo.flooditarium;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences settings;
    private Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings  = this.getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        svc = new Intent(this, BackgroundSoundService.class);

        if (settings.getBoolean("sound", true))
            startService(svc);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (settings.getBoolean("sound", true))
            startService(svc);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (settings.getBoolean("sound", true))
            stopService(svc);
    }

    public void main_play(View v) {
        DifficultyDialog difficultyDialog = new DifficultyDialog();
        difficultyDialog.showDialog(this);
        //int size = difficultyDialog.GetSize();
        //int colors = difficultyDialog.GetColors();
    }

    public void main_scores(View v) {
        Intent main_scores = new Intent(this, ScoresActivity.class);
        startActivity(main_scores);
    }

    public void main_about(View v) {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.showDialog(this);
    }

    public void main_settings(View v) {
        Intent main_settings = new Intent(this, SettingsActivity.class);
        startActivity(main_settings);
    }

    public void main_help(View v) {
        Intent main_help = new Intent(this, HelpActivity.class);
        startActivity(main_help);
        //Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show();
    }

    public void main_leave(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.leaveTitle))
                .setMessage(getString(R.string.leaveText))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                })
                .setIcon(R.drawable.icon)
                .show();
    }
}
