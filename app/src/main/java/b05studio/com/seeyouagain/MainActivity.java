package b05studio.com.seeyouagain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.google.firebase.database.FirebaseDatabase;

import b05studio.com.seeyouagain.fcm.FCMWebServerConnector;
import b05studio.com.seeyouagain.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FCMWebServerConnector.setPushServerURL("http://" + getString(R.string.push_server_ip) + "/SYAPushServer.php");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}