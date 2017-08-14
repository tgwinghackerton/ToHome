package b05studio.com.seeyouagain;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by mansu on 2017-08-14.
 */

class TouchView extends View {

    public class Coord {
        public float prex;
        public float prey;
        public float postx;
        public float posty;
    }

    private Coord coord;
    private Paint paint;
    private boolean isDrawing;
    private Bitmap sub = null;
    private boolean isSub;
    private ScrollView scrollView;

    public TouchView(Context context) {
        super(context);
        initialize();
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public void initialize() {
        coord = new Coord();
        coord.prex = 0;
        coord.prey = 0;
        coord.postx = 0;
        coord.posty = 0;

        this.isDrawing = false;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.RED);
    }

    public Coord getCoord() {
        return coord;
    }

    public void clear() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawing) {
            //if(posty)
            canvas.drawRect(coord.prex, coord.prey, coord.postx, coord.posty, paint);
        }
        if (isSub == true) {
            float leftX = coord.prex > coord.postx ? coord.postx : coord.prex;
            float leftY = coord.prey > coord.posty ? coord.posty : coord.prey;
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
                if(scrollView != null)
                    scrollView.requestDisallowInterceptTouchEvent(true);
                coord.prex = event.getX();
                coord.prey = event.getY();
                coord.postx = coord.prex + 1;
                coord.posty = coord.prey + 1;
                isDrawing = true;
                break;
            case MotionEvent.ACTION_MOVE:
                coord.postx = event.getX();
                coord.posty = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(scrollView != null)
                    scrollView.requestDisallowInterceptTouchEvent(false);
                break;
        }
        invalidate();
        return true;
    }
}