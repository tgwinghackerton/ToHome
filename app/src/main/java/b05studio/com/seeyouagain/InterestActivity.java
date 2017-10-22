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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.InterestInfo;
import b05studio.com.seeyouagain.model.LikeInfo;
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
        InterestAdapter adapter = new InterestAdapter(this);
        recyclerView.setAdapter(adapter);
        getInterestInfos(adapter);
    }

    public void getInterestInfos(final InterestAdapter adapter) {
        final List<LikeInfo> likeList = User.getUserInstance().getUserLikeList();
        final HashMap<LikeInfo, InterestInfo> infos = new HashMap<>();
        for(int i=0; i<likeList.size(); i++) {
            final LikeInfo likeInfo = likeList.get(i);
            String type = (likeInfo.getType() == 0) ? "mpi" : "missingChilds";
            if(likeInfo.getType() == 0) {
                FirebaseDatabase.getInstance().getReference().child("mpi").child(likeInfo.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        infos.put(likeInfo, new InterestInfo(null, dataSnapshot.getValue(MissingPersonInfo.class)));
                        count++;
                        if (count == likeList.size()) {
                            adapter.setInterestInfos(infos);
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
                        if (count == likeList.size()) {
                            adapter.setInterestInfos(infos);
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
            else {
                FirebaseDatabase.getInstance().getReference().child("missingChilds").child(likeInfo.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        infos.put(likeInfo, new InterestInfo(dataSnapshot.getValue(GreenUmInfo.class), null));
                        count++;
                        if (count == likeList.size()) {
                            adapter.setInterestInfos(infos);
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
                        if (count == likeList.size()) {
                            adapter.setInterestInfos(infos);
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
}