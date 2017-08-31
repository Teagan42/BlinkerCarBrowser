package rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tglenn on 3/21/17.
 */

public class CompositeItemBinder<ItemType>
          implements IItemBinder<ItemType> {
    private final List<ConditionalItemBinder<ItemType>> itemBinders = new ArrayList<>();

    public CompositeItemBinder(ConditionalItemBinder<ItemType>... itemBinders) {
        this.itemBinders.addAll(Lists.newArrayList(itemBinders));
    }

    public void addItemBinder(ConditionalItemBinder<ItemType> itemBinder) {
        itemBinders.add(itemBinder);
    }

    /**
     * Returns the layout resource id for {@param model}
     *
     * @param model Model to retrieve layout resource id for
     * @return The id of the layout resource
     */
    @Override
    public int getLayoutResourceId(ItemType model) {
        for (ConditionalItemBinder<ItemType> dataBinder : itemBinders) {
            if (dataBinder.canBind(model)) {
                return dataBinder.getLayoutResourceId(model);
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Returns the binding variable id for {@param model}
     *
     * @param model Model to retrieve the binding variable id for
     * @return The id of the binding variable
     */
    @Override
    public int getBindingVariableId(ItemType model) {
        for (ConditionalItemBinder<ItemType> dataBinder : itemBinders) {
            if (dataBinder.canBind(model)) {
                return dataBinder.getBindingVariableId(model);
            }
        }

        throw new IllegalStateException();
    }
}
