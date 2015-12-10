package i2.application.bisonfute.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * @author De Oliveira Romain
 */
public final class AnimUtils {


    public static void finishActivityWithAnimation(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.finish();
            activity.overridePendingTransition(R.anim.left_in, R.anim.right_out);
        } else {
            ActivityCompat.finishAfterTransition(activity);
        }
    }

    public static void startActivityWithAnimation(Activity activityFrom, Class activityTo) {
        Intent i = new Intent(activityFrom, activityTo);
        // inside your activity (if you did not enable transitions in your theme)
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // set an exit transition
        //getWindow().setExitTransition(new Explode());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activityFrom.startActivity(i);
            activityFrom.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else {
            ActivityCompat.startActivity(activityFrom, i,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activityFrom).toBundle());
        }
    }
}
