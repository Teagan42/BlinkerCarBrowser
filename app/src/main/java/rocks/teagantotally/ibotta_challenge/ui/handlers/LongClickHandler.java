package rocks.teagantotally.ibotta_challenge.ui.handlers;

/**
 * Created by tglenn on 8/30/17.
 */

public interface LongClickHandler<T> {
    /**
     * Handle on long click event for {@param item}
     *
     * @param item
     */
    void onLongClick(T item);
}
