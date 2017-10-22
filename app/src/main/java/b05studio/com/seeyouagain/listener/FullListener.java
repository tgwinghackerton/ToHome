package b05studio.com.seeyouagain.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import b05studio.com.seeyouagain.R;
import b05studio.com.seeyouagain.model.LikeInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;

/**
 * Created by mansu on 2017-08-12.
 */

public class FullListener implements View.OnClickListener {
    private Context context;
    private ImageButton like;
    private int type;
    private String key;

    public FullListener(Context context, ImageButton like, int type, String key) {
        this.context = context;
        this.like = like;
        this.type = type;
        this.key = key;
    }

    @Override
    public void onClick(final View view) {
        final List<LikeInfo> userLikeList = (List<LikeInfo>)((ArrayList)User.getUserInstance().getUserLikeList()).clone();
        userLikeList.remove(new LikeInfo(type, key));
        FirebaseDatabase.getInstance().getReference().child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("userLikeList").setValue(userLikeList, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(final DatabaseError databaseError, DatabaseReference databaseReference) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(databaseError != null) {
                            Toast.makeText(context, "좋아요 기능을 사용할 수 없습니다. 네트워크를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            User.getUserInstance().setUserLikeList(userLikeList);
                            like.setImageResource(R.drawable.icon_heart_empty);
                            view.setOnClickListener(new EmptyListener(context, like, type, key));
                        }
                    }
                });
            }
        });
    }
};