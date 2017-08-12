package b05studio.com.seeyouagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFourthActivity extends AppCompatActivity {
    @BindView(R.id.register4RestEditText)
    EditText register4RestEditText;

    @OnClick(R.id.registerBackButton)
    public void onBackButtonClick(View view) {
        finish();
    }

    @OnClick(R.id.registerNextButton)
    public void onNextClick(View view) {
        String rest = register4RestEditText.getText().toString();
        Intent intent = new Intent(this,RegisterFourthActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fourth);
    }
}
