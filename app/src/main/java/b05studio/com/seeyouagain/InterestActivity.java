package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;

/**
 * Created by mansu on 2017-07-05.
 */

public class InterestActivity extends AppCompatActivity {
    @BindView(R.id.interest_toolbar)
    Toolbar toolbar;

    @BindView(R.id.interest_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        setSupportActionBar(toolbar);

        InterestAdapter adapter = new InterestAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}