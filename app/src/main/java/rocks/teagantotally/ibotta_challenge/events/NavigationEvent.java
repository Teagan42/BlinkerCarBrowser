package rocks.teagantotally.ibotta_challenge.events;

import android.support.annotation.NonNull;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.ui.ScreenView;

/**
 * Created by tglenn on 8/30/17.
 */

public class NavigationEvent {
    private ScreenView from;
    private String to;
    private boolean addToBackstack = true;
    private Integer flags;

    public NavigationEvent(@NonNull ScreenView from,
                           @NonNull String to) {
        this(from,
             to,
             true);
    }

    public NavigationEvent(@NonNull ScreenView from,
                           @NonNull String to,
                           boolean addToBackstack) {
        Objects.requireNonNull(from,
                               "From cannot be null");
        Objects.requireNonNull(to,
                               "To cannot be null");

        this.from = from;
        this.to = to;
        this.addToBackstack = addToBackstack;
    }

    public NavigationEvent(@NonNull ScreenView from,
                           @NonNull String to,
                           int flags) {
        this(from,
             to,
             false);
        this.flags = flags;
    }

    /**
     * @return The screen we are navigating from
     */
    public ScreenView getFrom() {
        return from;
    }

    /**
     * @return The screen we are navigating to
     */
    public String getTo() {
        return to;
    }

    /**
     * @return Whether to add to backstack
     */
    public boolean shouldAddToBackstack() {
        return addToBackstack;
    }

    /**
     * @return Intent flags
     */
    public Integer getFlags() {
        return flags;
    }
}
