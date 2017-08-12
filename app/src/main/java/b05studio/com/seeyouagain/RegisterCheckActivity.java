package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-08-13.
 */

public class RegisterCheckActivity extends DialogActivity {
    @BindView(R.id.register_check_toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_check_name)
    TextView name;
    @BindView(R.id.register_check_gender)
    TextView gender;
    @BindView(R.id.register_check_age)
    TextView age;
    @BindView(R.id.register_check_date)
    TextView date;
    @BindView(R.id.register_check_address)
    TextView address;
    @BindView(R.id.register_check_circumstance_of_occurance)
    TextView circumstanceOfOccurance;
    @BindView(R.id.register_check_physical_characteristics)
    TextView physicalCharacteristics;
    @BindView(R.id.register_check_dress_marks)
    TextView dressMarks;
    @BindView(R.id.register_check_etc)
    TextView etc;

    private MissingPersonInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        info = RegisterActivity.info;
        name.setText(info.getName());
        age.setText("만 " + Integer.toString(info.getAge())+"세");
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        date.setText(format.format(info.getTimeOfMissing()));
        gender.setText(info.getGender());
        address.setText(info.getAddress());
        circumstanceOfOccurance.setText(info.getCircumstanceOfOccurance());
        physicalCharacteristics.setText(info.getPhysicalCharacteristics());
        dressMarks.setText(info.getDressMarks());
        etc.setText(info.getEtc());
        info.setWriterKey(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @OnClick(R.id.register_check_register)
    public void registerClick(View view) {
        showProgressDialog();
        FirebaseDatabase.getInstance().getReference().child("mpi").push().setValue(info, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(final DatabaseError databaseError, DatabaseReference databaseReference) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(databaseError != null) {
                            Toast.makeText(RegisterCheckActivity.this, "서버상태가 좋지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterCheckActivity.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterCheckActivity.this, MissingPersonListActivity.class);
                            intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            startActivity(intent);
                            finish();
                        }
                        hideProgressDialog();
                    }
                });
            }
        });
    }
}