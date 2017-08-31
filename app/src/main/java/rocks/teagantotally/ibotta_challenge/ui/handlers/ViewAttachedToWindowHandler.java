package rocks.teagantotally.ibotta_challenge.ui.handlers;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ViewAttachedToWindowHandler<T> {
    /**
     * Handle when the {@param item} is attached to the window
     *
     * @param item The item being attached to the window
     */
    void onViewAttachedToWindow(T item);
}
