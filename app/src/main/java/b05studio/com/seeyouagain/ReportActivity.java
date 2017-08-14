package b05studio.com.seeyouagain;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import b05studio.com.seeyouagain.fcm.FCMMessagingService;
import b05studio.com.seeyouagain.fcm.FCMWebServerConnector;
import b05studio.com.seeyouagain.model.AlarmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-08-12.
 */

public class ReportActivity extends DialogActivity {
    @BindView(R.id.report_content)
    EditText content;
    @BindView(R.id.report_add_image)
    ImageButton imageButton;

    private Bitmap bitmap;
    private final int SELECT_PICTURE = 1;
    private MissingPersonInfo missingPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        bitmap = null;
        missingPersonInfo = (MissingPersonInfo)getIntent().getSerializableExtra("info");
    }

    @OnClick(R.id.report_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.report_add_image)
    public void addImageClick(View view) {
        if(bitmap == null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
        else {
            bitmap = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE && data != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, imageButton.getWidth(), imageButton.getHeight(), true);
                    imageButton.setImageBitmap(scaledBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.report_report)
    public void reportClick(View view) {
        showProgressDialog();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(missingPersonInfo.getWriterKey()).child("alarmInfos").push();
        final AlarmInfo alarmInfo = new AlarmInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(), "", User.getUserInstance().getName(), Calendar.getInstance().getTimeInMillis(), content.getText().toString());
        reference.setValue(
                alarmInfo
                , new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        hideProgressDialog();
                        if(databaseError != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReportActivity.this, "네트워크 상태가 좋지 않습니다. 네트워크를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            FirebaseDatabase.getInstance().getReference().child("user").child(missingPersonInfo.getWriterKey()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    FCMWebServerConnector connector = new FCMWebServerConnector();
                                    connector.sendAlarmPush(alarmInfo, dataSnapshot.getValue(String.class));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            if(bitmap != null)
                                uploadImageToServer(reference, reference.getKey(), bitmap);
                            Toast.makeText(ReportActivity.this, "제보가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void uploadImageToServer(final DatabaseReference reference, final String alarmKey, final Bitmap bitmap) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (bitmap != null) {
                        ByteArrayOutputStream jpegOut = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, jpegOut);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference myRef = storage.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(alarmKey + ".jpg");

                        if (myRef.getName() == null || myRef.getName() != "") {
                            byte[] imageData = jpegOut.toByteArray();
                            final UploadTask uploadTask = myRef.putBytes(imageData);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ReportActivity.this, "네트워크 상태가 좋아 이미지 업로드에 실패하였습니다. 네트워크를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //??
                                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    reference.child("imageUrl").setValue(downloadUri.toString());
                                    Log.i("FirebaseClient", "이미지 업로드 완료");
                                }
                            });
                        } else {
                            Log.i("FirebaseClient", " 이미지 업로드 실패");
                            // 파일이름 존재하지않을때
                        }
                    }
                }
            }).start();
    }

}
