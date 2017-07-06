package b05studio.com.mpf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonAdapter extends RecyclerView.Adapter<MissingPersonAdapter.ListHolder> {

    private Context context;

    private List<MissingPersonInfo> missingPersonInfos;

    public MissingPersonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return missingPersonInfos.size();
    }

    public final static class ListHolder extends RecyclerView.ViewHolder {

        public ListHolder(View itemView) {
            super(itemView);
        }
    }
}
