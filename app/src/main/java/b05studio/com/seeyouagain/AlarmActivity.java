package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.HashMap;

import b05studio.com.seeyouagain.model.AlarmInfo;
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
        HashMap<String,AlarmInfo> infos = new HashMap<>();
        infos.put("1", new AlarmInfo("1", "최재열", Calendar.getInstance(), "제가 오늘 학교앞에서 비슷한 아이를 발견했습니다. 사진 첨부해드립니다."));
        infos.put("2", new AlarmInfo("1", "최재열", Calendar.getInstance(), "제가 오늘 학교앞에서 비슷한 아이를 발견했습니다. 사진 첨부해드립니다."));
        adapter.setAlarmInfos(infos);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.alarm_back)
    void backClick(View view) {
        finish();
    }

    public void updateAlarmInfos(final AlarmAdapter adapter) {
        /*
        Query query = FirebaseDatabase.getInstance().getReference().child("mpi").
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
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
`
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO 2017-08-10: 실패했을때 메시지 어떻게 띄울것인지
            }
        });
        */
    }
}
