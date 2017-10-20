package b05studio.com.seeyouagain.leaflet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.R;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-08-15.
 */

public class Leaflet {
    @BindView(R.id.leaflet_before)
    ImageView before;
    @BindView(R.id.leaflet_after)
    ImageView after;
    @BindView(R.id.leaflet_name)
    TextView name;
    @BindView(R.id.leaflet_age)
    TextView age;
    @BindView(R.id.leaflet_date)
    TextView date;
    @BindView(R.id.leaflet_region)
    TextView region;
    @BindView(R.id.leaflet_circum)
    TextView circum;
    @BindView(R.id.leaflet_physic)
    TextView physic;
    @BindView(R.id.leaflet_dress)
    TextView dress;
    @BindView(R.id.leaflet_etc)
    TextView etc;

    private View leaflet;

    public Leaflet(Activity activity, MissingPersonInfo info) {
        leaflet = activity.getLayoutInflater().inflate(R.layout.view_leaflet, null);
        ButterKnife.bind(this, leaflet);

        Picasso.with(activity).load(info.getBeforeUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                before.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Picasso.with(activity).load(info.getAfterUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                after.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        name.setText(info.getName());
        age.setText(Integer.toString(info.getAge()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 mm월 dd일 a hh:mm:ss");
        date.setText(format.format(info.getTimeOfMissing()));
        region.setText(info.getAddress());
        circum.setText(info.getCircumstanceOfOccurance());
        physic.setText(info.getPhysicalCharacteristics());
        dress.setText(info.getDressMarks());
        etc.setText(info.getEtc());

        leaflet.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        leaflet.layout(0, 0, leaflet.getMeasuredWidth(), leaflet.getMeasuredHeight());
    }

    public Bitmap infoToLeafletBitmap() {
        final Bitmap clusterBitmap = Bitmap.createBitmap(leaflet.getMeasuredWidth(),
                leaflet.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(clusterBitmap);
        leaflet.draw(canvas);
        return clusterBitmap;
    }
}