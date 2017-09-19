package rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview;

/**
 * Created by tglenn on 3/21/17.
 */

public abstract class ConditionalItemBinder<ItemType>
          extends ItemBinder<ItemType> {
    public ConditionalItemBinder(int bindingVariableId,
                                 int layoutId) {
        super(bindingVariableId,
              layoutId);
    }

    public abstract boolean canBind(ItemType item);
}
