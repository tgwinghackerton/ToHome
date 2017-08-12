package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

/**
 * Created by mansu on 2017-08-10.
 */

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_text)
    EditText searchText;

    @BindView(R.id.search_region)
    ImageButton regionBtn;

    @BindView(R.id.search_gender)
    ImageButton genderBtn;

    @BindView(R.id.search_age)
    ImageButton ageBtn;

    @BindView(R.id.search_date)
    ImageButton dateBtn;

    @BindView(R.id.search_cancel)
    ImageButton cancelBtn;

    @BindView(R.id.search_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(toolbar);

        regionBtn.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View view) {
                if(!isClicked) {
                    isClicked = true;
                    regionBtn.setImageResource(R.drawable.icon_search_region_click);
                }
                else {
                    isClicked = false;
                    regionBtn.setImageResource(R.drawable.icon_search_region);
                }
            }
        });
        genderBtn.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View view) {
                if(!isClicked) {
                    isClicked = true;
                    genderBtn.setImageResource(R.drawable.icon_search_gender_click);
                }
                else {
                    isClicked = false;
                    genderBtn.setImageResource(R.drawable.icon_search_gender);
                }
            }
        });
        ageBtn.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View view) {
                if(!isClicked) {
                    isClicked = true;
                    ageBtn.setImageResource(R.drawable.icon_search_age_click);
                }
                else {
                    isClicked = false;
                    ageBtn.setImageResource(R.drawable.icon_search_age);
                }
            }
        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View view) {
                if(!isClicked) {
                    isClicked = true;
                    dateBtn.setImageResource(R.drawable.icon_search_date_click);
                }
                else {
                    isClicked = false;
                    dateBtn.setImageResource(R.drawable.icon_search_date);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText.setText("");
            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, android.view.KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String queryString = searchText.getText().toString();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        MissingPersonListAdapter adapter = new MissingPersonListAdapter(this);
        recyclerView.setAdapter(adapter);
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