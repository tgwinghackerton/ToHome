package b05studio.com.mpf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mansu on 2017-07-05.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private Context context;

    private List<AlarmInfo> alarmInfos;

    public AlarmAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AlarmHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return alarmInfos.size();
    }

    public final static class AlarmHolder extends RecyclerView.ViewHolder {

        public AlarmHolder(View itemView) {
            super(itemView);
        }
    }
}
