package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonListActivity extends AppCompatActivity {
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
                Intent intent = new Intent(MissingPersonListActivity.this, InterestActivity.class);
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
                Intent intent = new Intent(MissingPersonListActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingPersonListActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        MissingPersonListAdapter adapter = new MissingPersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        updateMissingPersonInfos(adapter);
    }

    public void updateMissingPersonInfos(final MissingPersonListAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, MissingPersonInfo> missingPersonInfos = (HashMap<String, MissingPersonInfo>)dataSnapshot.getValue();
                adapter.setMissingPersonInfos(missingPersonInfos);
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
