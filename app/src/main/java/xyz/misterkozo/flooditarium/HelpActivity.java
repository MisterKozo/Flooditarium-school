package xyz.misterkozo.flooditarium;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    ImageView img_instruction;
    TextView tv_instruction;
    ImageButton bt_back;
    ImageButton bt_forward;

    int position;
    int[] db_instructions;
    int[] st_instructions; //both must be of the same length

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        img_instruction = (ImageView) findViewById(R.id.img_instruction);
        tv_instruction  = (TextView) findViewById(R.id.tv_instruction);
        bt_back         = (ImageButton) findViewById(R.id.bt_back);
        bt_forward      = (ImageButton) findViewById(R.id.bt_forward);

        position = 0;
        db_instructions = new int[] {
                /*R.drawable.inst0,
                R.drawable.inst1,
                R.drawable.inst2,
                R.drawable.inst3,*/
                R.drawable.icon
        };
        st_instructions = new int[] {
                /*R.string.inst0,
                R.string.inst1,
                R.string.inst2,
                R.string.inst3*/
                R.string.inst0
        };

        Switch(0);
    }

    public void help_forward(View v) {
        position++;
        Switch(position);
    }
    public void help_back(View v) {
        position--;
        Switch(position);
    }

    private void Switch(int position) {
        if (position == 0)
            bt_back.setVisibility(View.INVISIBLE);
        else
            bt_back.setVisibility(View.VISIBLE);

        if (position == db_instructions.length-1)
            bt_forward.setVisibility(View.INVISIBLE);
        else
            bt_forward.setVisibility(View.VISIBLE);

        img_instruction.setImageResource(db_instructions[position]);
        tv_instruction.setText(getString(st_instructions[position]));
    }
}