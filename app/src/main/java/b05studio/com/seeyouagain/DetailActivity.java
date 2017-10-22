package b05studio.com.seeyouagain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.leaflet.Leaflet;
import b05studio.com.seeyouagain.listener.EmptyListener;
import b05studio.com.seeyouagain.listener.FullListener;
import b05studio.com.seeyouagain.model.LikeInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import b05studio.com.seeyouagain.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class DetailActivity extends DialogActivity {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_like)
    ImageButton like;
    @BindView(R.id.detail_before)
    ImageView before;
    @BindView(R.id.detail_after)
    ImageView after;
    @BindView(R.id.detail_namegenderage)
    TextView namegenderage;
    @BindView(R.id.detail_birth)
    TextView birth;
    @BindView(R.id.detail_address)
    TextView address;
    @BindView(R.id.detail_circumstance_of_occurance)
    TextView circumstanceOfOccurance;
    @BindView(R.id.detail_physical_characteristics)
    TextView physicalCharacteristics;
    @BindView(R.id.detail_dress_marks)
    TextView dressMarks;
    @BindView(R.id.detail_etc)
    TextView etc;

    private MissingPersonInfo info;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        info = (MissingPersonInfo)getIntent().getSerializableExtra("info");
        key = getIntent().getStringExtra("key");

        Picasso.with(this).load(info.getBeforeUrl()).into(before);
        Picasso.with(this).load(info.getAfterUrl()).into(after);
        namegenderage.setText(info.getName() + " (" + info.getGender() + ", 현재 " + Utils.getAge(info.getTimeOfMissing(), info.getAge()) + "세)");

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 mm월 dd일");
        birth.setText(format.format(info.getTimeOfMissing()) + "(당시 만 " + info.getAge() + "세)");
        address.setText(info.getAddress());
        //aword.setText(info.getAword());

        boolean isEqual = false;
        LikeInfo likeInfo1 = new LikeInfo(0, key);
        for(LikeInfo likeInfo2 : User.getUserInstance().getUserLikeList()) {
            if(likeInfo2.equals(likeInfo1)) {
                isEqual = true;
            }
        }
        if (isEqual) {
            like.setImageResource(R.drawable.icon_heart_full);
            like.setOnClickListener(new FullListener(this, like, 0, key));
        } else {
            like.setImageResource(R.drawable.icon_heart_empty);
            like.setOnClickListener(new EmptyListener(this, like, 0, key));
        }

        circumstanceOfOccurance.setText(info.getCircumstanceOfOccurance());
        physicalCharacteristics.setText(info.getPhysicalCharacteristics());
        dressMarks.setText(info.getDressMarks());
        etc.setText(info.getEtc());
    }

    @OnClick(R.id.detail_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.detail_fab)
    public void fabClick(View view) {
        Intent intent = new Intent(DetailActivity.this, ReportActivity.class);
        intent.putExtra("info", info);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @OnClick(R.id.detail_share)
    public void shareClick(View view) {
        showProgressDialog();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference myRef = storage.getReference().child(key).child("leaflet.jpg");
        myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(uri)
                        .build();
                ShareDialog shareDialog = new ShareDialog(DetailActivity.this);
                shareDialog.show(linkContent, ShareDialog.Mode.AUTOMATIC);
                DetailActivity.this.hideProgressDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Leaflet leaflet = new Leaflet(DetailActivity.this, info);
                Bitmap leafletBitmap = leaflet.infoToLeafletBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                leafletBitmap.compress( Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] imageData = stream.toByteArray();
                StorageReference myRef = storage.getReference().child(key).child("leaflet.jpg");
                final UploadTask uploadTask = myRef.putBytes(imageData);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DetailActivity.this.hideProgressDialog();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentUrl(downloadUri)
                                .build();
                        ShareDialog shareDialog = new ShareDialog(DetailActivity.this);
                        shareDialog.show(linkContent, ShareDialog.Mode.AUTOMATIC);
                        DetailActivity.this.hideProgressDialog();
                        FirebaseDatabase.getInstance().getReference().child("user").child(User.getUserInstance().getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    }
                });

            }
        });
    }
}