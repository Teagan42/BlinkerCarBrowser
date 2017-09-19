package rocks.teagantotally.blinkercarbrowser.events;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.Objects;

import rocks.teagantotally.blinkercarbrowser.ui.vms.BaseVM;

/**
 * Created by tglenn on 9/18/17.
 */

public class DialogEvent
          extends BaseEvent {
    @LayoutRes
    private int layoutResourceId;
    private int bindingVariableId;
    private BaseVM viewModel;

    public DialogEvent(@LayoutRes int layoutResourceId,
                       int bindingVariableId,
                       @NonNull BaseVM viewModel) {
        Objects.requireNonNull(viewModel,
                               "View model cannot be null");
        if (layoutResourceId == 0) {
            throw new IllegalArgumentException("Layout resource id must be a positive integer");
        }
        if (bindingVariableId == 0) {
            throw new IllegalArgumentException("Bind variable id must be a positive integer");
        }
        this.layoutResourceId = layoutResourceId;
        this.bindingVariableId = bindingVariableId;
        this.viewModel = viewModel;
    }

    /**
     * @return The resource id of the layout to inflate
     */
    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    /**
     * @return The variable to bind to
     */
    public int getBindingVariableId() {
        return bindingVariableId;
    }

    /**
     * @return The view model
     */
    public BaseVM getViewModel() {
        return viewModel;
    }
}
