package b05studio.com.seeyouagain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Map;

import b05studio.com.seeyouagain.model.AlarmInfo;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-07-05.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private Context context;

    private Map<String, AlarmInfo> alarmInfos;

    public AlarmAdapter(Context context) {
        this.context = context;
    }

    public void setAlarmInfos(Map<String, AlarmInfo> alarmInfos) {
        this.alarmInfos = alarmInfos;
    }

    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
        AlarmHolder holder = new AlarmHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmHolder holder, int position) {
        AlarmInfo info = (AlarmInfo)(alarmInfos.keySet().toArray())[position];
        Picasso.with(context).load(info.getImageUrl()).into(holder.image);
        holder.name.setText(info.getName());
        SimpleDateFormat format = new SimpleDateFormat("a hh:mm");
        holder.date.setText(Utils.getDateString(info.getDate().getTimeInMillis()) + " " + format.format(info.getDate().getTime()));
    }

    @Override
    public int getItemCount() {
        return alarmInfos.size();
    }

    public final static class AlarmHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.alarm_image)
        ImageView image;
        @BindView(R.id.alarm_name)
        TextView name;
        @BindView(R.id.alarm_date)
        TextView date;
        @BindView(R.id.alarm_content)
        TextView content;

        public AlarmHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
