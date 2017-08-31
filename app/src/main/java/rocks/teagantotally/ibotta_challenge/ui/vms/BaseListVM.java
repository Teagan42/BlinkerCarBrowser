package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.IItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.handlers.ClickHandler;

/**
 * Created by tglenn on 8/30/17.
 */

public abstract class BaseListVM<ItemType>
          extends BaseObservable {

    private List<ItemType> items = new ArrayList<>();
    private IItemBinder<ItemType> itemBinder;
    private ClickHandler<ItemType> itemClickHandler;
    private boolean isRefreshing;
    private RecyclerView.LayoutManager layoutManager;

    public List<ItemType> getItems() {
        return items;
    }

    public void setItems(List<ItemType> items) {
        this.items = items;
        notifyChange();
    }

    public IItemBinder<ItemType> getItemBinder() {
        return itemBinder;
    }

    public void setItemBinder(CompositeItemBinder<ItemType> itemBinder) {
        this.itemBinder = itemBinder;
        notifyChange();
    }

    public ClickHandler<ItemType> getItemClickHandler() {
        return itemClickHandler;
    }

    public void setItemClickHandler(ClickHandler<ItemType> itemClickHandler) {
        if (Objects.equals(this.itemClickHandler,
                           itemClickHandler)) {
            return;
        }
        this.itemClickHandler = itemClickHandler;
        notifyChange();
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        if (isRefreshing == refreshing) {
            return;
        }
        isRefreshing = refreshing;
        notifyChange();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyChange();
    }

    public abstract SwipeRefreshLayout.OnRefreshListener getRefreshListener();

    public int getSize() {
        return items.size();
    }

    public ItemType remove(int index) {
        ItemType item = items.remove(index);
        notifyChange();
        return item;
    }

    public void addItemAt(ItemType post,
                          int index) {
        items.add(index,
                  post);
        notifyChange();
    }

    public void addItem(ItemType post) {
        items.add(post);
        notifyChange();
    }

    public void removeItem(ItemType post) {
        items.remove(post);
        notifyChange();
    }

    protected int getIndexOfId(Object id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i)
                     .equals(id)) {
                return i;
            }
        }

        return -1;
    }
}
