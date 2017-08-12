package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.listener.EmptyListener;
import b05studio.com.seeyouagain.listener.FullListener;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_like)
    ImageButton like;
    @BindView(R.id.detail_before)
    ImageView before;
    @BindView(R.id.detail_after)
    ImageView after;
    @BindView(R.id.detail_namegenderage)
    TextView namegenderage;
    @BindView(R.id.detail_birth)
    TextView birth;
    @BindView(R.id.detail_address)
    TextView address;
    @BindView(R.id.detail_circumstance_of_occurance)
    TextView circumstanceOfOccurance;
    @BindView(R.id.detail_physical_characteristics)
    TextView physicalCharacteristics;
    @BindView(R.id.detail_dress_marks)
    TextView dressMarks;
    @BindView(R.id.detail_etc)
    TextView etc;

    private MissingPersonInfo info;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        info = (MissingPersonInfo)getIntent().getSerializableExtra("info");
        key = getIntent().getStringExtra("key");

        Picasso.with(this).load(info.getBeforeUrl()).into(before);
        Picasso.with(this).load(info.getAfterUrl()).into(after);
        namegenderage.setText(info.getName() + " (" + info.getGender() + ", 현재 " + Utils.getAge(info.getBirth()) + "세)");

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 mm월 dd일");
        birth.setText(format.format(info.getTimeOfMissing().getTime()) + "(당시 만 " + (Utils.getAge(info.getBirth()) - Utils.getAge(info.getTimeOfMissing())) + "세)");
        address.setText(info.getAddress());
        //aword.setText(info.getAword());

        if(User.getUserInstance().getUserLikeList().indexOf(key) != -1) {
            like.setImageResource(R.drawable.icon_heart_full);
            like.setOnClickListener(new FullListener(like, key));
        }
        else {
            like.setImageResource(R.drawable.icon_heart_empty);
            like.setOnClickListener(new EmptyListener(like, key));
        }

        circumstanceOfOccurance.setText(info.getCircumstanceOfOccurance());
        physicalCharacteristics.setText(info.getPhysicalCharacteristics());
        dressMarks.setText(info.getDressMarks());
        etc.setText(info.getEtc());
    }

    @OnClick(R.id.detail_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.detail_fab)
    public void fabClick(View view) {
        Intent intent = new Intent(DetailActivity.this, ReportActivity.class);
        intent.putExtra("info", info);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}