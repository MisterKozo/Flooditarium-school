package xyz.misterkozo.flooditarium;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<Score> implements View.OnClickListener{

    private ArrayList<Score> dataSet;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView tv_score;
        TextView tv_details;
    }

    public ScoreListAdapter(ArrayList<Score> data, Context context) {
        super(context, R.layout.list_score, data);
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public void onClick(View v) {

        int position  = (Integer) v.getTag();
        Object object = getItem(position);
        Score score = (Score) object;

        /*switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +score.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
        /*String seed = score.getSeed();
        Intent score_play = new Intent(this.context, PlayActivity.class);
        score_play.putExtra("seed", seed);
        this.context.startActivity(score_play);*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Score score = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.list_score, parent, false);
            viewHolder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            viewHolder.tv_details = (TextView) convertView.findViewById(R.id.tv_details);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);*/
        lastPosition = position;

        viewHolder.tv_score.setText(String.valueOf(position+1) + ". " + score.getPlayer());
        viewHolder.tv_details.setText("At " + score.getDate() + " with " + score.getScore());
        /*viewHolder.txtVersion.setText(score.getVersion_number());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);*/
        // Return the completed view to render on screen
        return convertView;
    }
}