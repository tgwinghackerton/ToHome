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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import b05studio.com.seeyouagain.listener.EmptyListener;
import b05studio.com.seeyouagain.listener.FullListener;
import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.LikeInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-10-21.
 */

public class GreenUmAdapter extends RecyclerView.Adapter<GreenUmAdapter.ViewHolder> {

    private Context context;

    private Map<String, GreenUmInfo> greenUmInfos;

    public GreenUmAdapter(Context context) {
        this.context = context;
    }

    public void setGreenUmInfos(Map<String, GreenUmInfo> greenUmInfos) {
        this.greenUmInfos = greenUmInfos;
    }

    @Override
    public GreenUmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_greenum, parent, false);
        GreenUmAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GreenUmAdapter.ViewHolder holder, final int position) {
        final String key = (String) (greenUmInfos.keySet().toArray()[position]);
        final GreenUmInfo info = greenUmInfos.get(key);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GreenUmDetailActivity.class);
                intent.putExtra("info", info);
                intent.putExtra("key", key);
                context.startActivity(intent);
            }
        });


        Picasso.with(context).load(info.getBeforeUrl()).into(holder.before);

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        try {
            Date date = format.parse(info.getTimeOfMissing());
            holder.namegenderage.setText(info.getName() + " (" + info.getGender() + ", 현재 " + Utils.getAge(date.getTime(), Integer.parseInt(info.getAge())) + "세)");
            holder.birth.setText(info.getTimeOfMissing() + "(당시 만 " + info.getAge() + "세)");
            holder.address.setText(info.getAddress());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isEqual = false;
        LikeInfo likeInfo1 = new LikeInfo(1, key);
        for(LikeInfo likeInfo2 : User.getUserInstance().getUserLikeList()) {
            if(likeInfo2.equals(likeInfo1)) {
                isEqual = true;
            }
        }
        if (isEqual) {
            holder.like.setImageResource(R.drawable.icon_heart_full);
            holder.like.setOnClickListener(new FullListener(context, holder.like, 1, key));
        } else {
            holder.like.setImageResource(R.drawable.icon_heart_empty);
            holder.like.setOnClickListener(new EmptyListener(context, holder.like, 1, key));
        }
    }

    @Override
    public int getItemCount() {
        if(greenUmInfos == null)
            return 0;
        else
            return greenUmInfos.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.greenum_wrapper)
        ConstraintLayout wrapper;
        @BindView(R.id.greenum_before)
        ImageView before;
        @BindView(R.id.greenum_namegenderage)
        TextView namegenderage;
        @BindView(R.id.greenum_birth)
        TextView birth;
        @BindView(R.id.greenum_address)
        TextView address;
        @BindView(R.id.greenum_like)
        ImageButton like;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
