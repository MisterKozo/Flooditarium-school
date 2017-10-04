package xyz.misterkozo.flooditarium;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Switch sw_volume;
    SharedPreferences settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings  = this.getSharedPreferences("xyz.misterkozo.flooditarium", Context.MODE_PRIVATE);
        sw_volume = (Switch) findViewById(R.id.sw_volume);

        if (settings.getBoolean("sound", true) == true)
            sw_volume.setChecked(true);
        else
            sw_volume.setChecked(false);

        sw_volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("sound", isChecked);
                editor.commit();
            }

        });
    }

    public void settings_name(View v) {
        final SharedPreferences.Editor editor = this.settings.edit();
        final String previousName = this.settings.getString("name", "Player");

        final Dialog dialog_settings  = new Dialog(this);
        dialog_settings.setContentView(R.layout.dialog_name);
        final EditText et_name = (EditText) dialog_settings.findViewById(R.id.et_name);
        final Button bt_save   = (Button) dialog_settings.findViewById(R.id.bt_save);

        et_name.setText(previousName);

        bt_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putString("name", String.valueOf(et_name.getText()));
                editor.commit();
                dialog_settings.dismiss();
            }
        });
        dialog_settings.show();
    }

    public void settings_reset(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.leaveTitle))
                .setMessage(getString(R.string.deleteText))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.deleteAllScores();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("name", "Player");
                        editor.putBoolean("sound", true);
                        editor.commit();
                        sw_volume.setChecked(true);
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

    public void settings_back(View v) {
        finish();
    }

}
