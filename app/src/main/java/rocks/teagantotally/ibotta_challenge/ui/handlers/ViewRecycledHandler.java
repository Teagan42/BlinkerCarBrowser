package rocks.teagantotally.ibotta_challenge.ui.handlers;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ViewRecycledHandler<T> {
    /**
     * Handle the event of {@param item} being recycled
     *
     * @param item The item being recycled
     */
    void onViewRecycled(T item);
}
