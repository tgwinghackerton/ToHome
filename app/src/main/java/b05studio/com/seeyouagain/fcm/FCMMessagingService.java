package b05studio.com.seeyouagain.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import b05studio.com.seeyouagain.AlarmActivity;
import b05studio.com.seeyouagain.AlarmAdapter;
import b05studio.com.seeyouagain.DetailActivity;
import b05studio.com.seeyouagain.GreenUmDetailActivity;
import b05studio.com.seeyouagain.LoginActivity;
import b05studio.com.seeyouagain.R;
import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;

/**
 * Created by mansu on 2017-08-14.
 */

public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int NOTIFICATION_ID = 2229393;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        //data works background and foreground both
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("message"));
        }

        // Check if message contains a notification payload.
        //notification only work when foreground
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getBody());
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String messageBody) {
        try {
            JSONObject jsonObj = new JSONObject(messageBody);
            boolean check = false;
            try {
                jsonObj.getString("beforeUrl");
                check = true;
            } catch(JSONException e) {
                check = false;
            }
            if (check) {
                Intent intent;
                if (isAppOnForeground(getApplicationContext()))
                    intent = new Intent(this, GreenUmDetailActivity.class);
                else
                    intent = new Intent(this, GreenUmDetailActivity.class);
                final GreenUmInfo greenUmInfo = new GreenUmInfo();
                greenUmInfo.setAddress(jsonObj.getString("address"));
                greenUmInfo.setAge(jsonObj.getString("age"));
                greenUmInfo.setBeforeUrl(jsonObj.getString("beforeUrl"));
                greenUmInfo.setGender(jsonObj.getString("gender"));
                greenUmInfo.setName(jsonObj.getString("name"));
                greenUmInfo.setPhysicalCharacteristics(jsonObj.getString("physicalCharacteristics"));
                greenUmInfo.setTimeOfMissing(jsonObj.getString("timeOfMissing"));
                intent.putExtra("info", greenUmInfo);
                intent.putExtra("key", jsonObj.getString("key"));

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(getApplicationContext()).load(greenUmInfo.getBeforeUrl()).asBitmap().into(100, 100).get();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
                    try {
                        Date date = format.parse(greenUmInfo.getTimeOfMissing());
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(FCMMessagingService.this)
                                .setSmallIcon(R.drawable.icon_alarm_test)
                                .setLargeIcon(bitmap)
                                .setContentTitle(greenUmInfo.getName() + " (" + greenUmInfo.getGender() + ", 현재 " + Utils.getAge(date.getTime(), Integer.parseInt(greenUmInfo.getAge())) + "세)")
                                .setContentText("주소 : " + greenUmInfo.getAddress() + ", 신체특징 : " + greenUmInfo.getPhysicalCharacteristics())
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);
                        Notification n = notificationBuilder.build();
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(null, NOTIFICATION_ID /* ID of notification */, n);
                        FirebaseDatabase.getInstance().getReference().child("user").child(jsonObj.getString("userKey")).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                user.setPoint(user.getPoint()+10);
                                dataSnapshot.getRef().setValue(user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                Intent intent;
                if (isAppOnForeground(getApplicationContext()))
                    intent = new Intent(this, AlarmActivity.class);
                else
                    intent = new Intent(this, AlarmActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon_alarm_test)
                        .setContentTitle(jsonObj.getString("name") + "님이 메시지를 보내셨습니다.")
                        .setContentText(jsonObj.getString("content"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                Notification n = notificationBuilder.build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(null, NOTIFICATION_ID /* ID of notification */, n);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
