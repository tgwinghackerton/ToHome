package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

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
        exampleInfos.put("1", new MissingPersonInfo("1", "1", "김성주", "남", Calendar.getInstance(), Calendar.getInstance(), "경기도 수원 영통동 중앙상가", "우리 아이 찾아주세요\n" +
                "꼭 찾고 싶습니다!!", "학교 끝나고 집에 오는 길에 없어짐", "키 120cm, 쌍커풀, 이마에 희미한 흉터,\n" +
                "동글넙적한 얼굴, 말이 좀 어눌함, 눈 밑에 꿰맨 자국", "흰색 티셔츠, 주황색 칠부바지, 하늘색 샌들", "고개를 끄덕이는 틱 장애가 있습니다.\n" +
                "사례금 100만원.  "));
        exampleInfos.put("2", new MissingPersonInfo("1", "1", "김성주", "남", Calendar.getInstance(), Calendar.getInstance(), "경기도 수원 영통동 중앙상가", "우리 아이 찾아주세요\n" +
                "꼭 찾고 싶습니다!!", "학교 끝나고 집에 오는 길에 없어짐", "키 120cm, 쌍커풀, 이마에 희미한 흉터,\n" +
                "동글넙적한 얼굴, 말이 좀 어눌함, 눈 밑에 꿰맨 자국", "흰색 티셔츠, 주황색 칠부바지, 하늘색 샌들", "고개를 끄덕이는 틱 장애가 있습니다.\n" +
                "사례금 100만원.  "));
         adapter.setMissingPersonInfos(exampleInfos);
        recyclerView.setAdapter(adapter);
        //updateMissingPersonInfos(adapter);
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
