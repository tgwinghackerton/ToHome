package b05studio.com.seeyouagain.leaflet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;

import b05studio.com.seeyouagain.R;
import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-10-22.
 */

public class GreenUmLeaflet {
    @BindView(R.id.greenum_leaflet_before)
    ImageView before;
    @BindView(R.id.greenum_leaflet_name)
    TextView name;
    @BindView(R.id.greenum_leaflet_age)
    TextView age;
    @BindView(R.id.greenum_leaflet_date)
    TextView date;
    @BindView(R.id.greenum_leaflet_region)
    TextView region;
    @BindView(R.id.greenum_leaflet_physic)
    TextView physic;

    private View leaflet;

    public GreenUmLeaflet(Activity activity, GreenUmInfo info) {
        leaflet = activity.getLayoutInflater().inflate(R.layout.view_greenum_leaflet, null);
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

        name.setText(info.getName());
        age.setText(info.getAge());
        date.setText(info.getTimeOfMissing());
        region.setText(info.getAddress());
        physic.setText(info.getPhysicalCharacteristics());

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
