package b05studio.com.seeyouagain.fcm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import b05studio.com.seeyouagain.model.AlarmInfo;
import b05studio.com.seeyouagain.model.User;

/**
 * Created by mansu on 2017-08-15.
 */

public class FCMWebServerConnector {
    private Gson gson;
    private static String pushServerURL;

    public FCMWebServerConnector() {
        gson = new GsonBuilder().create();
    }

    public static void setPushServerURL(String pushServerURL) {
        FCMWebServerConnector.pushServerURL = pushServerURL;
    }

    public void sendAlarmPush(AlarmInfo info, String token) {
        try {
            Log.i("pushServerURL",FCMWebServerConnector.pushServerURL);
            JSONObject obj = new JSONObject();
            obj.put("token", token);
            obj.put("name", User.getUserInstance().getName());
            obj.put("content", info.getContent());

            String response = new FCMHttpHandler().execute(FCMWebServerConnector.pushServerURL,"POST",gson.toJson(obj)).get();
            Log.i("sendLikePush", response);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
