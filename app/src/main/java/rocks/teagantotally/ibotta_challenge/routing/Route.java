package rocks.teagantotally.ibotta_challenge.routing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.ui.BaseActivity;
import rocks.teagantotally.ibotta_challenge.ui.fragments.BaseFragment;

/**
 * Created by tglenn on 8/30/17.
 */

public class Route {
    private Class activity;
    private Class fragment;
    private Bundle extras;

    Route(@NonNull Class activity,
          @NonNull Class fragment,
          @Nullable Bundle extras) {
        Objects.requireNonNull(activity,
                               "Route activity cannot be null");
        Objects.requireNonNull(fragment,
                               "Route fragment cannot be null");
        this.activity = activity;
        this.fragment = fragment;
        this.extras = extras;
    }

    Route(@NonNull Class activity,
          @NonNull Class fragment) {
        this(activity,
             fragment,
             null);
    }

    /**
     * @return The activity to load
     */
    public Class<BaseActivity> getActivity() {
        return activity;
    }

    /**
     * @return The fragment to add to the stack
     */
    public Class<BaseFragment> getFragment() {
        return fragment;
    }

    /**
     * @return Any extra options to pass through
     */
    public Bundle getExtras() {
        return extras;
    }
}
