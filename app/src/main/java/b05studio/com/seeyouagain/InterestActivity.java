package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class InterestActivity extends AppCompatActivity {
    @BindView(R.id.interest_toolbar)
    Toolbar toolbar;

    @BindView(R.id.interest_recyclerview)
    RecyclerView recyclerView;

    @OnClick(R.id.interest_back)
    public void  backClick(View view) {
        finish();
    }

    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        count = 0;
        MissingPersonListAdapter adapter = new MissingPersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        getInterestInfos(adapter);
    }

    public void getInterestInfos(final MissingPersonListAdapter adapter) {
        final List<String> likeList = User.getUserInstance().getUserLikeList();
        final HashMap<String, MissingPersonInfo> infos = new HashMap<>();
        for(int i=0; i<likeList.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("mpi").child(likeList.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    infos.put(dataSnapshot.getKey(), dataSnapshot.getValue(MissingPersonInfo.class));
                    count++;
                    if(count == likeList.size()) {
                        adapter.setMissingPersonInfos(infos);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //TODO 2017-08-10: 실패했을때 메시지 어떻게 띄울것인지
                    count++;
                    if(count == likeList.size()) {
                        adapter.setMissingPersonInfos(infos);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }
    }
}