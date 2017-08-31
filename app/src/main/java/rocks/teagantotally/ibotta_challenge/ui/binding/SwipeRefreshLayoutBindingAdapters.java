package rocks.teagantotally.ibotta_challenge.ui.binding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by tglenn on 8/31/17.
 */


public abstract class SwipeRefreshLayoutBindingAdapters {
    @BindingAdapter({"refreshListener"})
    public static void setRefreshListener(SwipeRefreshLayout view,
                                          SwipeRefreshLayout.OnRefreshListener listener) {
        view.setOnRefreshListener(listener);
    }

    @BindingAdapter({"refreshing"})
    public static void setRefreshing(SwipeRefreshLayout view,
                                     boolean isRefreshing) {
        view.setRefreshing(isRefreshing);
    }
}
