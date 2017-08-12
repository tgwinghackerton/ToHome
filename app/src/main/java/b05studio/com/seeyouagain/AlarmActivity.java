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

import java.util.HashMap;

import b05studio.com.seeyouagain.model.AlarmInfo;
import butterknife.BindView;

/**
 * Created by mansu on 2017-07-05.
 */

public class AlarmActivity extends AppCompatActivity {
    @BindView(R.id.alarm_toolbar)
    Toolbar toolbar;

    @BindView(R.id.alarm_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.alarm_back)
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setSupportActionBar(toolbar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AlarmAdapter adapter = new AlarmAdapter(this);
        recyclerView.setAdapter(adapter);
        updateAlarmInfos(adapter);
    }

    public void updateAlarmInfos(final AlarmAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("alarmInfos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, AlarmInfo> alarmInfos = (HashMap<String, AlarmInfo>)dataSnapshot.getValue();
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
                //TODO 2017-08-10: 실패했을때 메시지 어떻게 띄울것인지
            }
        });
    }
}