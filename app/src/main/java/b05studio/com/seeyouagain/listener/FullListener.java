package b05studio.com.seeyouagain.listener;

import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import b05studio.com.seeyouagain.R;
import b05studio.com.seeyouagain.model.User;

/**
 * Created by mansu on 2017-08-12.
 */

public class FullListener implements View.OnClickListener {
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