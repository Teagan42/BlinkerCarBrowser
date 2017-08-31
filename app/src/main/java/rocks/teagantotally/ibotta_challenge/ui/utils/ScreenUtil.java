package rocks.teagantotally.ibotta_challenge.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by tglenn on 8/31/17.
 */

public abstract class ScreenUtil {

    public static int getScreenWidth(Context context) {
        return context.getResources()
                      .getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources()
                      .getDisplayMetrics().heightPixels;
    }

    public static int convertPxToDp(Context context,
                                    int pixels) {
        DisplayMetrics displayMetrics = context.getResources()
                                               .getDisplayMetrics();
        return Math.round(pixels / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPx(Context context,
                                    int dip) {
        DisplayMetrics displayMetrics = context.getResources()
                                               .getDisplayMetrics();
        return Math.round(dip * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
