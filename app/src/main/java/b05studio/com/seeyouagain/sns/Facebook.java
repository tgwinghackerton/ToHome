package b05studio.com.seeyouagain.sns;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by mansu on 2017-08-15.
 */

public class Facebook {
    public static void shareFacebook(Activity activity, Bitmap image) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        ShareContent shareContent = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
    }
}