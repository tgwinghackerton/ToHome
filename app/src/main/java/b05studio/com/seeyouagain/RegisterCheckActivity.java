package b05studio.com.seeyouagain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-08-13.
 */

public class RegisterCheckActivity extends DialogActivity {
    @BindView(R.id.register_check_toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_check_before)
    ImageView imageView;
    @BindView(R.id.register_check_name)
    TextView name;
    @BindView(R.id.register_check_gender)
    TextView gender;
    @BindView(R.id.register_check_age)
    TextView age;
    @BindView(R.id.register_check_date)
    TextView date;
    @BindView(R.id.register_check_address)
    TextView address;
    @BindView(R.id.register_check_circumstance_of_occurance)
    TextView circumstanceOfOccurance;
    @BindView(R.id.register_check_physical_characteristics)
    TextView physicalCharacteristics;
    @BindView(R.id.register_check_dress_marks)
    TextView dressMarks;
    @BindView(R.id.register_check_etc)
    TextView etc;

    private MissingPersonInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        imageView.setImageBitmap(RegisterActivity.cropBitmap);
        info = RegisterActivity.info;
        name.setText(info.getName());
        age.setText("만 " + Integer.toString(info.getAge())+"세");
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        date.setText(format.format(info.getTimeOfMissing()));
        gender.setText(info.getGender());
        address.setText(info.getAddress());
        circumstanceOfOccurance.setText(info.getCircumstanceOfOccurance());
        physicalCharacteristics.setText(info.getPhysicalCharacteristics());
        dressMarks.setText(info.getDressMarks());
        etc.setText(info.getEtc());
        info.setWriterKey(FirebaseAuth.getInstance().getCurrentUser().getUid());
        info.setAccepted(false);
    }

    @OnClick(R.id.register_check_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.register_check_register)
    public void registerClick(View view) {
        showProgressDialog();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("mpi").push();
        reference.setValue(info, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(final DatabaseError databaseError, DatabaseReference databaseReference) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(databaseError != null) {
                            Toast.makeText(RegisterCheckActivity.this, "서버상태가 좋지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterCheckActivity.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            if(RegisterActivity.cropBitmap != null)
                                uploadImageToServer(reference, reference.getKey(), RegisterActivity.cropBitmap);
                            Intent intent = new Intent(RegisterCheckActivity.this, MissingPersonListActivity.class);
                            intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            startActivity(intent);
                            finish();
                        }
                        hideProgressDialog();
                    }
                });
            }
        });
    }

    private void uploadImageToServer(final DatabaseReference reference, final String registerKey, final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    ByteArrayOutputStream jpegOut = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, jpegOut);

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference myRef = storage.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(registerKey + ".jpg");

                    if (myRef.getName() == null || myRef.getName() != "") {
                        byte[] imageData = jpegOut.toByteArray();
                        final UploadTask uploadTask = myRef.putBytes(imageData);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterCheckActivity.this, "네트워크 상태가 좋아 이미지 업로드에 실패하였습니다. 네트워크를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //??
                                @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                                reference.child("beforeUrl").setValue(downloadUri.toString());
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