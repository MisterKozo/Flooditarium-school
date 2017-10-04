package xyz.misterkozo.flooditarium;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by adhoms on 10/3/17.
 */

public class DifficultyDialog {
    private EditText ts, tc;
    private int size, colors;
    private Activity activity;
    public void showDialog(final Activity activity){
        this.activity = activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_difficulty);

        ts = (EditText) dialog.findViewById(R.id.et_size);
        tc = (EditText) dialog.findViewById(R.id.et_colors);
        final Button dismissButton = (Button) dialog.findViewById(R.id.bt_finish);

        ts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int clr = Integer.valueOf(tc.getText().toString());
                    int siz = Integer.valueOf(ts.getText().toString());
                    if (clr >= 2 && clr <= 6 && siz >= 10 && siz <= 30)
                        dismissButton.setEnabled(true);
                    else
                        dismissButton.setEnabled(false);
                } catch (NumberFormatException e) {
                    dismissButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int clr = Integer.valueOf(tc.getText().toString());
                    int siz = Integer.valueOf(ts.getText().toString());
                    if (clr >= 2 && clr <= 6 && siz >= 10 && siz <= 30)
                        dismissButton.setEnabled(true);
                    else
                        dismissButton.setEnabled(false);
                } catch (NumberFormatException e) {
                    dismissButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ts = (EditText) dialog.findViewById(R.id.et_size);
                EditText tc = (EditText) dialog.findViewById(R.id.et_colors);
                size = Integer.valueOf(ts.getText().toString());
                colors = Integer.valueOf(tc.getText().toString());
                Intent intent_play = new Intent(activity, PlayActivity.class);
                intent_play.putExtra("difficulty", 1);
                intent_play.putExtra("size", size);
                intent_play.putExtra("colors", colors);
                dialog.dismiss();
                activity.startActivity(intent_play);
            }
        });

        dialog.show();

    }

    public int GetSize() { return this.size; }
    public int GetColors() { return this.colors; }
}
