package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;

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
        setSupportActionBar(toolbar);

        AlarmAdapter adapter = new AlarmAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
