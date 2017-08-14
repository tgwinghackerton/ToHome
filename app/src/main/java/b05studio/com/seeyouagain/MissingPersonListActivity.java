package b05studio.com.seeyouagain;

import android.content.Intent;
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
import java.util.Map;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonListActivity extends AppCompatActivity {
    @BindView(R.id.list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        MissingPersonListAdapter adapter = new MissingPersonListAdapter(this);
        HashMap<String, MissingPersonInfo> exampleInfos = new HashMap<>();
         adapter.setMissingPersonInfos(exampleInfos);
        recyclerView.setAdapter(adapter);
        getMissingPersonInfos(adapter);
    }

    public void getMissingPersonInfos(final MissingPersonListAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, MissingPersonInfo> missingPersonInfos = new HashMap<String, MissingPersonInfo>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue로 클래스 지정안해주면 맵으로받아올때 그 클래스인지몰라서 변경안시켜줘서 문제생긴다든데?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MissingPersonInfo info = dataSnapshot1.getValue(MissingPersonInfo.class);
                    if(info.isAccepted())
                        missingPersonInfos.put(dataSnapshot1.getKey(), info);
                }
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

    @OnClick(R.id.list_alarm)
    public void alarmClick(View view) {
        Intent intent = new Intent(MissingPersonListActivity.this, AlarmActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_search)
    public void searchClick(View view) {
        Intent intent = new Intent(MissingPersonListActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_fab)
    public void fabClick(View view) {
        Intent intent = new Intent(MissingPersonListActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_interests)
    public void interestClick(View view) {
        Intent intent = new Intent(MissingPersonListActivity.this, InterestActivity.class);
        startActivity(intent);
    }
}
