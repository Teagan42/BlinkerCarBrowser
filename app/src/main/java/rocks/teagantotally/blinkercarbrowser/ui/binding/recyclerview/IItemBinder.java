package rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview;

/**
 * Created by tglenn on 3/21/17.
 */

public interface IItemBinder<ItemType> {
    /**
     * Returns the layout resource id for {@param model}
     *
     * @param model Model to retrieve layout resource id for
     * @return The id of the layout resource
     */
    int getLayoutResourceId(ItemType model);

    /**
     * Returns the binding variable id for {@param model}
     *
     * @param model Model to retrieve the binding variable id for
     * @return The id of the binding variable
     */
    int getBindingVariableId(ItemType model);
}
