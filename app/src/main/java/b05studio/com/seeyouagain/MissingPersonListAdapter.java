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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;

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
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("key", (String)((List)missingPersonInfos.keySet()).get(position));
                intent.putExtra("key", (MissingPersonInfo)(missingPersonInfos.keySet().toArray())[position]);
                context.startActivity(intent);
            }
        });
        MissingPersonInfo info = (MissingPersonInfo)(missingPersonInfos.keySet().toArray())[position];
        Picasso.with(context).load(info.getBeforeUrl()).into(holder.before);
        Picasso.with(context).load(info.getAfterUrl()).into(holder.after);
        holder.namegenderage.setText(info.getName() + " (" + info.getGender() + ", 현재 " + getAge(info.getBirth()) + "세)");

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 mm월 dd일");
        holder.birth.setText(format.format(info.getTimeOfMissing().getTime()) + "(당시 만 " + (getAge(info.getBirth()) - getAge(info.getTimeOfMissing())) + "세)");
        holder.aword.setText(info.getAword());

        if(User.INSTANCE.getUserLikeList().indexOf(((String)((List)missingPersonInfos.keySet()).get(position))) != -1) {
            holder.like.setImageResource(R.drawable.icon_heart_full);
            holder.like.setOnClickListener(new FullListener(holder.like, (String)((List)missingPersonInfos.keySet()).get(position)));
        }
        else {
            holder.like.setImageResource(R.drawable.icon_heart_empty);
            holder.like.setOnClickListener(new EmptyListener(holder.like, (String)((List)missingPersonInfos.keySet()).get(position)));
        }
    }

    private class EmptyListener implements View.OnClickListener {
        private ImageButton like;
        private String key;

        public EmptyListener(ImageButton like, String key) {
            this.like = like;
            this.key = key;
        }

        @Override
        public void onClick(View view) {
            if(User.INSTANCE.getUserLikeList().indexOf(key) == -1) {
                User.INSTANCE.getUserLikeList().add(key);
                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userLikeList").setValue(User.INSTANCE.getUserLikeList());
            }
            like.setImageResource(R.drawable.icon_heart_full);
            view.setOnClickListener(new FullListener(like, key));
        }
    };

    private class FullListener implements View.OnClickListener {
        private ImageButton like;
        private String key;

        public FullListener(ImageButton like, String key) {
            this.like = like;
            this.key = key;
        }

        @Override
        public void onClick(View view) {
            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userLikeList").child(key).removeValue();
            like.setImageResource(R.drawable.icon_heart_empty);
            view.setOnClickListener(new EmptyListener(like, key));
        }
    };

    private int getAge(Calendar calendar) {
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR))
            age--;
        Integer ageInt = new Integer(age);

        return ageInt;
    }

    @Override
    public int getItemCount() {
        return missingPersonInfos.size();
    }

    public final static class ListHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wrapper;
        ImageView before;
        ImageView after;
        TextView namegenderage;
        ImageButton like;
        TextView birth;
        TextView address;
        TextView aword;

        public ListHolder(View itemView) {
            super(itemView);
            wrapper = (ConstraintLayout)itemView.findViewById(R.id.missingperson_wrapper);
            before = (ImageView)itemView.findViewById(R.id.missingperson_before);
            after = (ImageView)itemView.findViewById(R.id.missingperson_after);
            namegenderage = (TextView)itemView.findViewById(R.id.missingperson_namegenderage);
            like = (ImageButton)itemView.findViewById(R.id.missingperson_like);
            birth = (TextView)itemView.findViewById(R.id.missingperson_birth);
            address = (TextView)itemView.findViewById(R.id.missingperson_address);
            aword = (TextView)itemView.findViewById(R.id.missingperson_aword);
        }
    }
}
