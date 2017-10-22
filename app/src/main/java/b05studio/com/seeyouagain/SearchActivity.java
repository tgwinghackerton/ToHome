package b05studio.com.seeyouagain;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.SearchInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by mansu on 2017-08-10.
 */

public class SearchActivity extends DialogActivity {
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_text)
    EditText searchText;

    @BindView(R.id.search_recyclerview)
    RecyclerView recyclerView;

    private SearchAdapter adapter;
    private List<SearchInfo> searchInfos = Collections.synchronizedList(new ArrayList<SearchInfo>());
    private boolean mpiSearchEnd = false;
    private boolean missingChildSearchEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        adapter = new SearchAdapter(this);
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
                showProgressDialog();
                String queryString = searchText.getText().toString();
                getSearchInfo(adapter, queryString);
                break;
            default:
                return false;
        }
        return true;
    }

    public void getSearchInfo(final SearchAdapter adapter, final String searchText) {
        searchInfos.clear();
        mpiSearchEnd = false;
        missingChildSearchEnd = false;
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, MissingPersonInfo> missingPersonInfos = new HashMap<String, MissingPersonInfo>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue로 클래스 지정안해주면 맵으로받아올때 그 클래스인지몰라서 변경안시켜줘서 문제생긴다든데?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MissingPersonInfo info = dataSnapshot1.getValue(MissingPersonInfo.class);
                    if(info.getName().compareTo(searchText) == 0 && info.isAccepted()) {
                        searchInfos.add(new SearchInfo(dataSnapshot.getKey(), 0, info, null));
                    }
                }
                mpiSearchEnd = true;
                check(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mpiSearchEnd = true;
                check(adapter);
            }
        });
        FirebaseDatabase.getInstance().getReference().child("missingChilds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, GreenUmInfo> missingPersonInfos = new HashMap<String, GreenUmInfo>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue로 클래스 지정안해주면 맵으로받아올때 그 클래스인지몰라서 변경안시켜줘서 문제생긴다든데?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    GreenUmInfo info = dataSnapshot1.getValue(GreenUmInfo.class);
                    if(info.getName().compareTo(searchText) == 0) {
                        searchInfos.add(new SearchInfo(dataSnapshot.getKey(), 1, null, info));
                    }
                }
                missingChildSearchEnd = true;
                check(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                missingChildSearchEnd = true;
                check(adapter);
            }
        });
    }

    private void check(final SearchAdapter adapter) {
        if(mpiSearchEnd && missingChildSearchEnd) {
            adapter.setSearchInfo(searchInfos);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    adapter.notifyDataSetChanged();
                    hideProgressDialog();
                }
            });
        }
    }
}