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
        adapter.setAlarmInfos(User.getUserInstance().getAlarmInfos());
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.alarm_back)
    void backClick(View view) {
        finish();
    }
}
