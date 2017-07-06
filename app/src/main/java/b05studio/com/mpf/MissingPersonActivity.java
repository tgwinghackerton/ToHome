package b05studio.com.mpf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonActivity extends AppCompatActivity {
    @BindView(R.id.list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_interests)
    ImageButton interestsBtn;

    @BindView(R.id.list_search)
    ImageButton searchBtn;

    @BindView(R.id.list_alarm)
    ImageButton alarmBtn;

    @BindView(R.id.list_fab)
    FloatingActionButton fab;

    @BindView(R.id.list_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setSupportActionBar(toolbar);

        interestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingPersonActivity.this, InterestActivity.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingPersonActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingPersonActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        MissingPersonAdapter adapter = new MissingPersonAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
