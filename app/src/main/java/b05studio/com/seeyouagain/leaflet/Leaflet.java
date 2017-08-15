package b05studio.com.seeyouagain.leaflet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        Picasso.with(activity).load(info.getBeforeUrl()).into(before);
        Picasso.with(activity).load(info.getAfterUrl()).into(after);
        name.setText(info.getName());
        age.setText(Integer.toString(info.getAge()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 mm월 dd일 a hh:mm:ss");
        date.setText(format.format(info.getTimeOfMissing()));
        region.setText(info.getAddress());
        circum.setText(info.getCircumstanceOfOccurance());
        physic.setText(info.getPhysicalCharacteristics());
        dress.setText(info.getDressMarks());
        etc.setText(info.getEtc());
    }

    public Bitmap infoToLeafletBitmap() {
        leaflet.setDrawingCacheEnabled(true);
        leaflet.buildDrawingCache();
        return leaflet.getDrawingCache();
    }
}