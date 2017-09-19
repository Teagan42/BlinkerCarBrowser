package rocks.teagantotally.blinkercarbrowser.ui.vms;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.CompositeItemBinder;
import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.IItemBinder;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.ClickHandler;

/**
 * Created by tglenn on 8/30/17.
 */

public abstract class BaseListVM<ItemType>
          extends BaseVM {

    private List<ItemType> items = new ArrayList<>();
    private IItemBinder<ItemType> itemBinder;
    private ClickHandler<ItemType> itemClickHandler;
    private boolean isRefreshing;
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Create a new list view model
     *
     * @param eventBus Event bus to subscribe and post events to
     */
    protected BaseListVM(EventBus eventBus) {
        super(eventBus);
    }

    public List<ItemType> getItems() {
        return items;
    }

    /**
     * Sets the items in the list
     *
     * @param items Items in the list
     */
    public void setItems(List<ItemType> items) {
        this.items = new ArrayList<>(items);
        notifyChange();
    }

    /**
     * @return The item binder for the recycler view
     */
    public IItemBinder<ItemType> getItemBinder() {
        return itemBinder;
    }

    /**
     * Sets the item binder for the recycler view
     *
     * @param itemBinder Composite item binder
     */
    public void setItemBinder(CompositeItemBinder<ItemType> itemBinder) {
        this.itemBinder = itemBinder;
        notifyChange();
    }

    /**
     * @return The click handler for items in the recycler view
     */
    public ClickHandler<ItemType> getItemClickHandler() {
        return itemClickHandler;
    }

    /**
     * Sets the click handler for the items in the recycler view
     *
     * @param itemClickHandler Click handler
     */
    public void setItemClickHandler(ClickHandler<ItemType> itemClickHandler) {
        if (Objects.equals(this.itemClickHandler,
                           itemClickHandler)) {
            return;
        }
        this.itemClickHandler = itemClickHandler;
        notifyChange();
    }

    /**
     * @return Whether the list is refreshing
     */
    public boolean isRefreshing() {
        return isRefreshing;
    }

    /**
     * Sets whether the list is refreshing
     *
     * @param refreshing Is refreshing
     */
    public void setRefreshing(boolean refreshing) {
        if (isRefreshing == refreshing) {
            return;
        }
        isRefreshing = refreshing;
        notifyChange();
    }

    /**
     * @return The layout manager bound to the recycler view
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * Set the layout manager bound to the recycler view
     *
     * @param layoutManager The recycler view's layout manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyChange();
    }

    /**
     * @return The refresh listener
     */
    public abstract SwipeRefreshLayout.OnRefreshListener getRefreshListener();

    /**
     * @return The number of items in the list
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Removes an item from the list
     *
     * @param index Index of the item to remove
     * @return The item that was removed
     */
    public ItemType remove(int index) {
        ItemType item = items.remove(index);
        notifyChange();
        return item;
    }

    /**
     * Adds an item to the list
     *
     * @param item  Item to add
     * @param index Index to insert item at
     */
    public void addItemAt(ItemType item,
                          int index) {
        items.add(index,
                  item);
        notifyChange();
    }

    /**
     * Adds a collection of items to the end of the list
     *
     * @param itemsToAdd Items to add
     */
    public void addItems(Collection<ItemType> itemsToAdd) {
        items.addAll(itemsToAdd);
        notifyChange();
    }

    /**
     * Adds an item to the end of the list
     *
     * @param item The item to add
     */
    public void addItem(ItemType item) {
        items.add(item);
        notifyChange();
    }

    /**
     * Removes an item from the list
     *
     * @param item The item to remove
     */
    public void removeItem(ItemType item) {
        items.remove(item);
        notifyChange();
    }
}
