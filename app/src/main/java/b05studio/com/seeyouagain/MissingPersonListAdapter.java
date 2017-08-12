package b05studio.com.seeyouagain;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Map;

import b05studio.com.seeyouagain.listener.EmptyListener;
import b05studio.com.seeyouagain.listener.FullListener;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonListAdapter extends RecyclerView.Adapter<MissingPersonListAdapter.ListHolder> {

    private Context context;

    private Map<String, MissingPersonInfo> missingPersonInfos;

    public MissingPersonListAdapter(Context context) {
        this.context = context;
    }

    public void setMissingPersonInfos(Map<String, MissingPersonInfo> missingPersonInfos) {
        this.missingPersonInfos = missingPersonInfos;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_missingperson, parent, false);
        ListHolder holder = new ListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolder holder, final int position) {
        final String key = (String) (missingPersonInfos.keySet().toArray()[position]);
        final MissingPersonInfo info = missingPersonInfos.get(key);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", info);
                intent.putExtra("key", key);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).load(info.getBeforeUrl()).into(holder.before);
        Picasso.with(context).load(info.getAfterUrl()).into(holder.after);
        holder.namegenderage.setText(info.getName() + " (" + info.getGender() + ", 현재 " + Utils.getAge(info.getTimeOfMissing(), info.getAge()) + "세)");

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        holder.birth.setText(format.format(info.getTimeOfMissing()) + "(당시 만 " + info.getAge() + "세)");
        holder.address.setText(info.getAddress());
        holder.aword.setText(info.getAword());

        if (User.getUserInstance().getUserLikeList().indexOf(key) != -1) {
            holder.like.setImageResource(R.drawable.icon_heart_full);
            holder.like.setOnClickListener(new FullListener(context, holder.like, key));
        } else {
            holder.like.setImageResource(R.drawable.icon_heart_empty);
            holder.like.setOnClickListener(new EmptyListener(context, holder.like, key));
        }
    }

    @Override
    public int getItemCount() {
        if(missingPersonInfos == null)
            return 0;
        else
            return missingPersonInfos.size();
    }

    public final static class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.missingperson_wrapper)
        ConstraintLayout wrapper;
        @BindView(R.id.missingperson_before)
        ImageView before;
        @BindView(R.id.missingperson_after)
        ImageView after;
        @BindView(R.id.missingperson_namegenderage)
        TextView namegenderage;
        @BindView(R.id.missingperson_like)
        ImageButton like;
        @BindView(R.id.missingperson_birth)
        TextView birth;
        @BindView(R.id.missingperson_address)
        TextView address;
        @BindView(R.id.missingperson_aword)
        TextView aword;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
