package b05studio.com.seeyouagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterThirdActivity extends AppCompatActivity {

    @BindView(R.id.register3AcctidentEditText)
    EditText register3AcctidentEditText;

    @BindView(R.id.register3BodyEditText)
    EditText register3BodyEditText;

    @BindView(R.id.register3WearEditText)
    EditText register3WearEditText;

    @OnClick(R.id.registerBackButton)
    public void onBackButtonClick(View view) {
        finish();
    }

    @OnClick(R.id.registerNextButton)
    public void onNextClick(View view) {
        String accident = register3AcctidentEditText.getText().toString();
        String body = register3BodyEditText.getText().toString();
        String wear = register3WearEditText.getText().toString();

        if (accident.length() > 0 && body.length() > 0 && wear.length() > 0) {
            RegisterActivity.info.setCircumstanceOfOccurance(accident);
            RegisterActivity.info.setPhysicalCharacteristics(body);
            RegisterActivity.info.setDressMarks(wear);
            Intent intent = new Intent(this,RegisterFourthActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "아직 입력하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_third);
        ButterKnife.bind(this);
    }
}
