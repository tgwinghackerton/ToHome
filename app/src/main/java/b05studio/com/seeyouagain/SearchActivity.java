package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
import butterknife.OnEditorAction;

/**
 * Created by mansu on 2017-08-10.
 */

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_text)
    EditText searchText;

    @BindView(R.id.search_recyclerview)
    RecyclerView recyclerView;

    private MissingPersonListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        adapter = new MissingPersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        //updateSearchInfo(adapter);
    }

    @OnClick(R.id.search_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.search_cancel)
    public void cancelClick(View view) {
        searchText.setText("");
    }

    @OnEditorAction(R.id.search_text)
    public boolean searchText(TextView v, int actionId, android.view.KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                String queryString = searchText.getText().toString();
                getSearchInfo(adapter, queryString);
                break;
            default:
                return false;
        }
        return true;
    }

    public void getSearchInfo(final MissingPersonListAdapter adapter, final String searchText) {
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, MissingPersonInfo> missingPersonInfos = new HashMap<String, MissingPersonInfo>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue로 클래스 지정안해주면 맵으로받아올때 그 클래스인지몰라서 변경안시켜줘서 문제생긴다든데?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MissingPersonInfo info = dataSnapshot1.getValue(MissingPersonInfo.class);
                    if(info.getName().indexOf(searchText) != -1 && info.isAccepted())
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

}