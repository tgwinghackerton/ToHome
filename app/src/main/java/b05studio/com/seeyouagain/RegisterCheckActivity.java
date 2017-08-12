package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-08-13.
 */

public class RegisterCheckActivity extends AppCompatActivity {
    @BindView(R.id.register_check_toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_check_name)
    TextView name;
    @BindView(R.id.register_check_gender)
    TextView gender;
    @BindView(R.id.register_check_age)
    TextView age;
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

        name.setText(info.getName());
        gender.setText(info.getGender());
        address.setText(info.getAddress());
        circumstanceOfOccurance.setText(info.getCircumstanceOfOccurance());
        physicalCharacteristics.setText(info.getPhysicalCharacteristics());
        dressMarks.setText(info.getDressMarks());
        etc.setText(info.getEtc());
    }

    @OnClick(R.id.register_check_register)
    public void registerClick(View view) {
        FirebaseDatabase.getInstance().getReference().child("mpi").push().setValue(info, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null) {
                }
                else {
                }
            }
        });
    }
}