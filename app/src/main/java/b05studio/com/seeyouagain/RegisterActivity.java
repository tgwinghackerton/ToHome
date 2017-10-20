package b05studio.com.seeyouagain;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-07-05.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_scrollview)
    ScrollView scrollView;

    @BindView(R.id.register_imageview)
    ImageView imageView;

    @BindView(R.id.register_touchview)
    TouchView touchView;

    @BindView(R.id.register_aword)
    EditText awordEditText;

    private static int SELECT_PICTURE = 1;
    public static MissingPersonInfo info = new MissingPersonInfo();
    public static Bitmap cropBitmap = null;
    private Bitmap scaledBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        info = new MissingPersonInfo();
        touchView.setScrollView(scrollView);
    }

    @OnClick(R.id.registerNextButton)
    public void onNextClick(View view) {
        String aword = awordEditText.getText().toString();

        if (aword.length() > 0) {
            if(scaledBitmap != null) {
                TouchView.Coord coord = touchView.getCoord();
                cropBitmap = Bitmap.createBitmap(scaledBitmap, (int) coord.prex, (int) coord.prey, (int) (coord.postx - coord.prex), (int) (coord.posty - coord.prey));
            }
            //System.out.println("decode jpeg data");
            RegisterActivity.info.setAword(aword);
            Intent intent = new Intent(this,RegisterFirstActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "아직 입력하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.register_back)
    public void backClick(View view) {
        finish();
    }

    @OnClick(R.id.register_imageview)
    public void imageClick(View view) {
        if(scaledBitmap == null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
        else {
            scaledBitmap = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE && data != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    float ratio = (float)bitmap.getHeight() / bitmap.getWidth();
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)imageView.getLayoutParams();
                    params.height = (int)(params.width * ratio);
                    imageView.setLayoutParams(params);

                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, params.width, params.height, true);

                    imageView.setImageBitmap(scaledBitmap);
                    touchView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}