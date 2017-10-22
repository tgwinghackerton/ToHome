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
import java.util.List;
import java.util.Map;

import b05studio.com.seeyouagain.listener.EmptyListener;
import b05studio.com.seeyouagain.listener.FullListener;
import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.InterestInfo;
import b05studio.com.seeyouagain.model.LikeInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.SearchInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-10-22.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;

    private List<SearchInfo> searchInfos;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setSearchInfo(List<SearchInfo> searchInfos) {
        this.searchInfos = searchInfos;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_greenum, parent, false);
        SearchAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder holder, final int position) {
        final SearchInfo searchInfo = searchInfos.get(position);
        if(searchInfo.getType() == 0) {
            final MissingPersonInfo missingPersonInfo = searchInfo.getMissingPersonInfo();
            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("info", missingPersonInfo);
                    intent.putExtra("key", searchInfo.getKey());
                    context.startActivity(intent);
                }
            });


            Picasso.with(context).load(missingPersonInfo.getBeforeUrl()).into(holder.before);

            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
            holder.namegenderage.setText(missingPersonInfo.getName() + " (" + missingPersonInfo.getGender() + ", 현재 " + Utils.getAge(missingPersonInfo.getTimeOfMissing(), missingPersonInfo.getAge()) + "세)");
            holder.birth.setText(format.format(missingPersonInfo.getTimeOfMissing()) + "(당시 만 " + missingPersonInfo.getAge() + "세)");
            holder.address.setText(missingPersonInfo.getAddress());

            boolean isEqual = false;
            LikeInfo likeInfo1 = new LikeInfo(0, searchInfo.getKey());
            for(LikeInfo likeInfo2 : User.getUserInstance().getUserLikeList()) {
                if(likeInfo2.equals(likeInfo1)) {
                    isEqual = true;
                }
            }
            if (isEqual) {
                holder.like.setImageResource(R.drawable.icon_heart_full);
                holder.like.setOnClickListener(new FullListener(context, holder.like, 0, searchInfo.getKey()));
            } else {
                holder.like.setImageResource(R.drawable.icon_heart_empty);
                holder.like.setOnClickListener(new EmptyListener(context, holder.like, 0, searchInfo.getKey()));
            }
        }
        else if(searchInfo.getType() == 1) {
            final GreenUmInfo greenUmInfo = searchInfo.getGreenUmInfo();

            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GreenUmDetailActivity.class);
                    intent.putExtra("info", greenUmInfo);
                    intent.putExtra("key", searchInfo.getKey());
                    context.startActivity(intent);
                }
            });


            Picasso.with(context).load(greenUmInfo.getBeforeUrl()).into(holder.before);

            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
            try {
                Date date = format.parse(greenUmInfo.getTimeOfMissing());
                holder.namegenderage.setText(greenUmInfo.getName() + " (" + greenUmInfo.getGender() + ", 현재 " + Utils.getAge(date.getTime(), Integer.parseInt(greenUmInfo.getAge())) + "세)");
                holder.birth.setText(greenUmInfo.getTimeOfMissing() + "(당시 만 " + greenUmInfo.getAge() + "세)");
                holder.address.setText(greenUmInfo.getAddress());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            boolean isEqual = false;
            LikeInfo likeInfo1 = new LikeInfo(1, searchInfo.getKey());
            for(LikeInfo likeInfo2 : User.getUserInstance().getUserLikeList()) {
                if(likeInfo2.equals(likeInfo1)) {
                    isEqual = true;
                }
            }
            if (isEqual) {
                holder.like.setImageResource(R.drawable.icon_heart_full);
                holder.like.setOnClickListener(new FullListener(context, holder.like, 1, searchInfo.getKey()));
            } else {
                holder.like.setImageResource(R.drawable.icon_heart_empty);
                holder.like.setOnClickListener(new EmptyListener(context, holder.like, 1, searchInfo.getKey()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if(searchInfos == null)
            return 0;
        else
            return searchInfos.size();
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

