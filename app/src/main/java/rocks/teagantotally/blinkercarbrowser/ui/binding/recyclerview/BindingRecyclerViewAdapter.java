package rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;

import rocks.teagantotally.blinkercarbrowser.ui.handlers.AttachedToWindowHandler;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.ClickHandler;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.LongClickHandler;


/**
 * Created by tglenn on 3/21/17.
 */

public class BindingRecyclerViewAdapter<ItemType>
          extends RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder>
          implements View.OnClickListener,
                     View.OnLongClickListener {
    public static final int ITEM_MODEL = -124;
    private static final String TAG = "BindingRecyclerViewAdap";
    private final IItemBinder<ItemType> itemBinder;
    // UI Event handlers
    private ClickHandler<ItemType> clickHandler;
    private LongClickHandler<ItemType> longClickHandler;
    private LayoutInflater inflater;
    private ObservableList<ItemType> items;
    private WeakReferenceOnListChangedCallback onListChangedCallback;

    public BindingRecyclerViewAdapter(IItemBinder<ItemType> itemBinder,
                                      @Nullable Collection<ItemType> items) {
        this.itemBinder = itemBinder;
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback(this);
        setItems(items);
    }

    /**
     * Return the collection of items in the recycler view
     *
     * @return
     */
    public ObservableList<ItemType> getItems() {
        return items;
    }

    /**
     * Sets the items in the recycler view
     *
     * @param items
     */
    public void setItems(@Nullable Collection<ItemType> items) {
        if (this.items != null && (items == null || items.size() == 0)) {
            this.items.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0,
                                   this.items.size());
            this.items.clear();
            if (items == null) {
                this.items = null;
            }
        } else {
            if (this.items == null) {
                this.items = new ObservableArrayList<>();
                this.items.addOnListChangedCallback(onListChangedCallback);
            }

            this.items.clear();

            if (items != null && items.size() > 0) {
                this.items.addAll(items);
            }

            notifyDataSetChanged();
        }
    }

    /**
     * Set the handler for clicking on an item in the recycler view
     *
     * @param clickHandler Event handler
     */
    public void setClickHandler(ClickHandler<ItemType> clickHandler) {
        this.clickHandler = clickHandler;
    }

    /**
     * Set the handler for long clicking on an item in the recycler view
     *
     * @param clickHandler Event handler
     */
    public void setLongClickHandler(LongClickHandler<ItemType> clickHandler) {
        this.longClickHandler = clickHandler;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param layoutId The layotut resource id
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int layoutId) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding = DataBindingUtil.inflate(inflater,
                                                          layoutId,
                                                          parent,
                                                          false);
        return new ViewHolder(binding);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder,
                                 int position) {
        final ItemType item = items.get(position);
        final ViewDataBinding binding = holder.binding;

        binding.setVariable(itemBinder.getBindingVariableId(item),
                            item);
        binding.getRoot()
               .setTag(ITEM_MODEL,
                       item);
        binding.getRoot()
               .setOnClickListener(this);
        binding.getRoot()
               .setOnLongClickListener(this);
        binding.executePendingBindings();
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        return itemBinder.getLayoutResourceId(items.get(position));
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items == null
               ? 0
               : items.size();
    }

    /**
     * Called by RecyclerView when it stops observing this Adapter.
     *
     * @param view The RecyclerView instance which stopped observing this adapter.
     * @see #onAttachedToRecyclerView(RecyclerView)
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView view) {
        if (items != null) {
            items.removeOnListChangedCallback(onListChangedCallback);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (clickHandler != null) {
            ItemType item = (ItemType) v.getTag(ITEM_MODEL);
            clickHandler.onClick(item);
        }
    }

    /**
     * Called when a view has been long clicked.
     *
     * @param v The view that was long clicked.
     */
    @Override
    public boolean onLongClick(View v) {
        if (longClickHandler != null) {
            ItemType item = (ItemType) v.getTag(ITEM_MODEL);
            longClickHandler.onLongClick(item);
            return true;
        }

        return false;
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * <p>
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * {@link #onViewDetachedFromWindow(RecyclerView.ViewHolder) onViewDetachedFromWindow}
     * those resources should be restored here.</p>
     *
     * @param viewHolder Holder of the view being attached
     */
    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        ItemType item = (ItemType) viewHolder.binding.getRoot()
                                                     .getTag(ITEM_MODEL);
        if (item instanceof AttachedToWindowHandler) {
            ((AttachedToWindowHandler) item).onViewAttachedToWindow();
        }

        super.onViewAttachedToWindow(viewHolder);
    }

    static class ViewHolder
              extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class WeakReferenceOnListChangedCallback<ItemType>
              extends ObservableList.OnListChangedCallback {

        private final WeakReference<BindingRecyclerViewAdapter<ItemType>> adapterReference;

        WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter<ItemType>
                                                     bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender,
                                       int positionStart,
                                       int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart,
                                               itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender,
                                        int positionStart,
                                        int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart,
                                                itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender,
                                     int fromPosition,
                                     int toPosition,
                                     int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition,
                                        toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender,
                                       int positionStart,
                                       int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart,
                                               itemCount);
            }
        }
    }
}
