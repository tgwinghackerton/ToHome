package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b05studio.com.seeyouagain.model.AlarmInfo;
import b05studio.com.seeyouagain.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class AlarmActivity extends AppCompatActivity {
    @BindView(R.id.alarm_toolbar)
    Toolbar toolbar;

    @BindView(R.id.alarm_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        AlarmAdapter adapter = new AlarmAdapter(this);
        recyclerView.setAdapter(adapter);
        updateAlarmInfos(adapter);
    }

    public void updateAlarmInfos(final AlarmAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("alarmInfos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AlarmInfo> alarmInfos = new ArrayList<>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue로 클래스 지정안해주면 맵으로받아올때 그 클래스인지몰라서 변경안시켜줘서 문제생긴다든데?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AlarmInfo info = dataSnapshot1.getValue(AlarmInfo.class);
                    alarmInfos.add(info);
                }
                Collections.sort(alarmInfos, new Comparator<AlarmInfo>() {
                    @Override
                    public int compare(AlarmInfo t1, AlarmInfo t2) {
                        if(t1.getDate() > t2.getDate())
                            return -1;
                        else
                            return 1;
                    }
                });
                adapter.setAlarmInfos(alarmInfos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @OnClick(R.id.alarm_back)
    void backClick(View view) {
        finish();
    }
}
