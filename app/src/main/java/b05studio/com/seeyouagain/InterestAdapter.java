package b05studio.com.seeyouagain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import b05studio.com.seeyouagain.model.InterestInfo;

/**
 * Created by mansu on 2017-07-05.
 */

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestsHolder> {

    private Context context;

    private List<InterestInfo> interestInfos;

    public InterestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public InterestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(InterestsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return interestInfos.size();
    }

    public final static class InterestsHolder extends RecyclerView.ViewHolder {

        public InterestsHolder(View itemView) {
            super(itemView);
        }
    }
}
