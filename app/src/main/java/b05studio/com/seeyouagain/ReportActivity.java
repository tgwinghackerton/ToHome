package b05studio.com.seeyouagain;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import b05studio.com.seeyouagain.model.AlarmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;

/**
 * Created by mansu on 2017-08-12.
 */

public class ReportActivity extends AppCompatActivity {
    private MissingPersonInfo info;
    private String key;

    private void report() {
        FirebaseDatabase.getInstance().getReference().child("user").child(key).child("alarmInfos").push().setValue(
                new AlarmInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(), "1", "1", Calendar.getInstance(),  "1")
                , new DatabaseReference.CompletionListener() {
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
