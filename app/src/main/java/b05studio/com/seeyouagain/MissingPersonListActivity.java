package b05studio.com.seeyouagain;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonListActivity extends AppCompatActivity {
    @BindView(R.id.list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_tab)
    TabLayout tabLayout;

    @BindView(R.id.list_viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("See You Again"));
        tabLayout.addTab(tabLayout.newTab().setText("초록우산"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(0xFFFFFFFF, 0xFFFFFFFF);
        LinearLayout layout = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tabLayout.getTabAt(0).getPosition());
        TextView tabTextView = (TextView) layout.getChildAt(1);
        tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);

        MissingPersonListAdapter adapter = new MissingPersonListAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                LinearLayout layout = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) layout.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout layout = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) layout.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    @OnClick(R.id.list_interests)
    public void interestClick(View view) {
        Intent intent = new Intent(MissingPersonListActivity.this, InterestActivity.class);
        startActivity(intent);
    }
}
