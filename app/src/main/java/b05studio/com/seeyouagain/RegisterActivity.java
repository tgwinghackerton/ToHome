package b05studio.com.seeyouagain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;

/**
 * Created by mansu on 2017-07-05.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_imageview)
    ImageView imageView;

    public class ImageInfo {
        // Bitmap image
        private String path;
        private TouchEvent event;
        // 이미지 클릭시 실행할 OnClickListener
        private FrameLayout.OnClickListener onClick;

        /**
         * Constructor
         *
         * @param path
         */
        public ImageInfo(String path) {
            this.path = path;
            onClick = onClickListener;
        }

        public void setTouchEvent(TouchEvent m_event) {
            event = m_event;
        }

        public View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TEST
                //System.out.println("onClick!!");
                imageView.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeFile(path)));   // 수정필요
                event.clear();
            }
        };

        public String getImagePath() {
            return path;
        }

        public RelativeLayout.OnClickListener getOnClick() {
            return onClick;
        }
    }

    class TouchEvent extends View {

        private float prex;
        private float prey;
        private float postx;
        private float posty;
        private Paint paint;
        private boolean isDrawing;
        private Bitmap sub = null;
        private boolean isSub;

        public TouchEvent(Context context) {
            super(context);
            initialize();
        }

        public void initialize() {
            this.prex = 0;
            this.prey = 0;
            this.postx = 0;
            this.posty = 0;

            this.isDrawing = false;

            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.RED);
        }

        public void clear() {
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (isDrawing) {
                //if(posty)
                canvas.drawRect(prex, prey, postx, posty, paint);
            }
            if (isSub == true) {
                float leftX = prex > postx ? postx : prex;
                float leftY = prey > posty ? posty : prey;
                canvas.drawBitmap(sub, leftX, leftY, paint);
                isSub = false;
                sub = null;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    prex = event.getX();
                    prey = event.getY();
                    postx = prex + 1;
                    posty = prey + 1;
                    isDrawing = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    postx = event.getX();
                    posty = event.getY();
                    break;
                /*
                case MotionEvent.ACTION_UP:
                    isDrawing = false;
                    performClick();
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(prex < 1)
                                prex = 1;
                            if(postx < 1)
                                postx = 1;
                            if(prey < 1)
                                prey = 1;
                            if(posty < 1)
                                posty = 1;
                            if(prex > Config.videoWidth - 1)
                                prex = Config.videoWidth - 1;
                            if(postx > Config.videoWidth - 1)
                                postx = Config.videoWidth - 1;
                            if(prey > Config.videoHeight - 1)
                                prey = Config.videoHeight - 1;
                            if(posty > Config.videoHeight - 1)
                                posty = Config.videoHeight - 1;

                            byte[] base64Jpg = viewThread.requestImg(currentImgTS, (int)prex, (int)prey, (int)postx, (int)posty);
                            if(base64Jpg != null) {
                                byte[] jpg = Base64.decode(base64Jpg, Base64.DEFAULT);
                                try {
                                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp.jpg");
                                    FileOutputStream fout = new FileOutputStream(file);
                                    fout.write(jpg);
                                    fout.flush();
                                    fout.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                sub = BitmapFactory.decodeByteArray(jpg, 0, jpg.length);
                                if(sub != null) {
                                    //sub = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tmp.jpg");
                                    isSub = true;
                                }
                                else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FRActivity.this, "Invalid JPG Data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(FRActivity.this, "Access fail", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            //base64jpg exception
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    invalidate();
                                }
                            });
                            dialog.dismiss();
                        }
                    }).start();
                    break;
                    */
            }
            invalidate();
            return true;
        }
    }
}