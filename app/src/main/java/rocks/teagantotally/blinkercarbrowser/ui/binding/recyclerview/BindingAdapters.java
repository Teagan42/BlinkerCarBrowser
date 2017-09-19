package rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

import rocks.teagantotally.blinkercarbrowser.ui.handlers.ClickHandler;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.LongClickHandler;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.ScrollHandler;

/**
 * Created by tglenn on 3/22/17.
 */

public abstract class BindingAdapters {
    private static final int KEY_ITEMS = -123;
    private static final int KEY_CLICK_HANDLER = -124;
    private static final int KEY_LONG_CLICK_HANDLER = -125;

    /**
     * Bind a collection of {@param items} to a {@param recycler view}
     *
     * @param recyclerView Recycler view to bind items to
     * @param items        The items to bind
     * @param <T>          The type of item being bound
     */
    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView,
                                    Collection<T> items) {
        BindingRecyclerViewAdapter<T> adapter =
                  (BindingRecyclerViewAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(items);
        } else {
            recyclerView.setTag(KEY_ITEMS,
                                items);
        }
    }

    /**
     * Bind an {@itemViewMapper} to a recycler view, this tells it which layout to inflate and
     * which binding variable to bind to
     *
     * @param recyclerView   The recycler view being bound
     * @param itemViewMapper The mapping of layout and binding variable ids
     * @param <T>            The type of item being bound
     */
    @BindingAdapter("itemViewBinder")
    public static <T> void setItemViewBinder(RecyclerView recyclerView,
                                             IItemBinder<T> itemViewMapper) {
        Collection<T> items =
                  (Collection<T>) recyclerView.getTag(KEY_ITEMS);
        ClickHandler<T> clickHandler =
                  (ClickHandler<T>) recyclerView.getTag(KEY_CLICK_HANDLER);
        LongClickHandler<T> longClickHandler =
                  (LongClickHandler<T>) recyclerView.getTag(KEY_LONG_CLICK_HANDLER);

        BindingRecyclerViewAdapter<T> adapter =
                  (BindingRecyclerViewAdapter<T>) recyclerView.getAdapter();

        if (adapter == null && itemViewMapper != null) {
            // No adapter defined yet, attach to recycler view
            adapter = new BindingRecyclerViewAdapter<>(itemViewMapper,
                                                       items);
            recyclerView.setAdapter(adapter);
        } else if (adapter == null) {
            return;
        }

        // Attach handlers
        if (clickHandler != null) {
            adapter.setClickHandler(clickHandler);
        }
        if (longClickHandler != null) {
            adapter.setLongClickHandler(longClickHandler);
        }
    }

    /**
     * Bind event {@param handler} for clicking on an item
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The click handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("clickHandler")
    public static <ItemType> void setHandler(RecyclerView recyclerView,
                                             ClickHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();

        if (adapter != null) {
            adapter.setClickHandler(handler);
        } else {
            // No adapter defined yet, store in view tag for later
            recyclerView.setTag(KEY_CLICK_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {@param handler} for long clicking on an item
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The long click handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("longClickHandler")
    public static <ItemType> void setHandler(RecyclerView recyclerView,
                                             LongClickHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setLongClickHandler(handler);
        } else {
            // No adapter defined yet, store in view tag for later
            recyclerView.setTag(KEY_LONG_CLICK_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {#param handler} for scrolling the {@param recyclerView}
     *
     * @param recyclerView  Recycler view to bind to
     * @param scrollHandler The scroll handler
     */
    @BindingAdapter("scrollHandler")
    public static void setScrollHandler(RecyclerView recyclerView,
                                        final ScrollHandler scrollHandler) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx,
                                   int dy) {
                super.onScrolled(recyclerView,
                                 dx,
                                 dy);
                if (scrollHandler != null) {
                    scrollHandler.onScroll(recyclerView,
                                           dx,
                                           dy);
                }
            }
        });
    }

    /**
     * Bind the {@param layoutManger} to the recycler view
     *
     * @param recyclerView  The recycler view to bind to
     * @param layoutManager The layout manager instance
     */
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
                                        RecyclerView.LayoutManager layoutManager) {
        if (recyclerView.getLayoutManager() == null
            || !recyclerView.getLayoutManager()
                            .equals(layoutManager)) {
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
