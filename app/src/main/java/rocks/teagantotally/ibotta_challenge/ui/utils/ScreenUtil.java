package rocks.teagantotally.ibotta_challenge.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by tglenn on 8/31/17.
 */

public abstract class ScreenUtil {

    /**
     * Get the screen width in pixels
     *
     * @param context Context
     * @return Width in pixels
     */
    public static int getScreenWidth(Context context) {
        return context.getResources()
                      .getDisplayMetrics().widthPixels;
    }

    /**
     * Get the screen height in pixels
     *
     * @param context Context
     * @return Height in pixesl
     */
    public static int getScreenHeight(Context context) {
        return context.getResources()
                      .getDisplayMetrics().heightPixels;
    }

    /**
     * Converts pixels to device independent pixels
     *
     * @param context Context
     * @param pixels  Pixels
     * @return Device independent pixels
     */
    public static int convertPxToDp(Context context,
                                    int pixels) {
        DisplayMetrics displayMetrics = context.getResources()
                                               .getDisplayMetrics();
        return Math.round(pixels / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Converts device independent pixesl to device pixels
     *
     * @param context Context
     * @param dip     Device independent pixels
     * @return Device pixels
     */
    public static int convertDpToPx(Context context,
                                    int dip) {
        DisplayMetrics displayMetrics = context.getResources()
                                               .getDisplayMetrics();
        return Math.round(dip * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
