package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ScreenService.class);
        startService(intent);

        Intent intent2 = new Intent(this, LoginActivity.class);
        startService(intent2);
    }
}
