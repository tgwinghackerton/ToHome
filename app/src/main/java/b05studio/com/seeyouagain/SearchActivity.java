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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        MissingPersonListAdapter adapter = new MissingPersonListAdapter(this);
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
                break;
            default:
                return false;
        }
        return true;
    }

    public void updateSearchInfo(final MissingPersonListAdapter adapter) {
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