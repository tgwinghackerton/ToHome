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
import b05studio.com.seeyouagain.model.User;

/**
 * Created by mansu on 2017-08-12.
 */

public class EmptyListener implements View.OnClickListener {
    private Context context;
    private ImageButton like;
    private String key;

    public EmptyListener(Context context, ImageButton like, String key) {
        this.context = context;
        this.like = like;
        this.key = key;
    }

    @Override
    public void onClick(final View view) {
        final User user = User.getUserInstance();
        if(user.getUserLikeList().indexOf(key) == -1) {
            final List<String> userLikeList = (List<String>)((ArrayList)User.getUserInstance().getUserLikeList()).clone();
            userLikeList.add(key);
            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userLikeList").setValue(userLikeList, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(final DatabaseError databaseError, DatabaseReference databaseReference) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(databaseError != null) {
                                Toast.makeText(context, "좋아요 기능을 사용할 수 없습니다. 네트워크를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                user.setUserLikeList(userLikeList);
                                like.setImageResource(R.drawable.icon_heart_full);
                                view.setOnClickListener(new FullListener(context, like, key));
                            }
                        }
                    });
                }
            });
        }
    }
};