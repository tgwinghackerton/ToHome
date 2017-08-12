package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-07-05.
 */

public class RegisterActivity extends AppCompatActivity {
    public static MissingPersonInfo info = new MissingPersonInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info = new MissingPersonInfo();
        setContentView(R.layout.activity_register_first);
        ButterKnife.bind(this);
    }



}