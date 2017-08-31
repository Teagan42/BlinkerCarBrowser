package rocks.teagantotally.ibotta_challenge.ui.handlers;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ViewDetachedFromWindowHandler<T> {
    /**
     * Handle the event when {@param item} is detached from the window
     *
     * @param item The item being detached
     */
    void onViewDetachedFromWindow(T item);
}
