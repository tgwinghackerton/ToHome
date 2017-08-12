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

public class RegisterSecondActivity extends AppCompatActivity {

    @BindView(R.id.register2SiDoEditText)
    EditText register2SiDoEditText;

    @BindView(R.id.register2SigunguAddressEditText)
    EditText register2SigunguAddressEditText;

    @BindView(R.id.register2DetailAddressEditText)
    EditText register2DetailAddressEditText;

    @OnClick(R.id.registerBackButton)
    public void onBackButtonClick(View view) {
        finish();
    }

    @OnClick(R.id.registerNextButton)
    public void onNextClick(View view) {
        String sido = register2SiDoEditText.getText().toString();
        String sigungu = register2SigunguAddressEditText.getText().toString();
        String detailadress = register2DetailAddressEditText.getText().toString();

        if (sido.length() > 0 && sigungu.length() > 0 && detailadress.length() > 0) {
            RegisterActivity.info.setAddress(sido + " " + sigungu + " " + detailadress);
            Intent intent = new Intent(this,RegisterThirdActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "아직 입력하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ButterKnife.bind(this);
    }


}
