package rocks.teagantotally.ibotta_challenge.ui.handlers;

import android.view.View;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ScrollHandler {
    /**
     * Handle scroll event for {@param view}
     *
     * @param view The ui view that is being scrolled
     * @param dx   The distance scrolled along the x-axis
     * @param dy   The distance scrolled along the y-axis
     */
    void onScroll(View view,
                  int dx,
                  int dy);
}
