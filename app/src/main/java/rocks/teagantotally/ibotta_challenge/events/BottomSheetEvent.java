package rocks.teagantotally.ibotta_challenge.events;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

/**
 * Created by teaganglenn on 7/10/17.
 */

public class BottomSheetEvent
          extends BaseEvent {
    @LayoutRes int layoutResourceIdentifier;
    int bindingVariableIdentifier;
    Object viewModel;

    public BottomSheetEvent(@LayoutRes int layoutResourceIdentifier,
                            int bindingVariableIdentifier,
                            @NonNull Object viewModel) {
        Objects.requireNonNull(viewModel,
                               "View model cannot be null");
        this.layoutResourceIdentifier = layoutResourceIdentifier;
        this.bindingVariableIdentifier = bindingVariableIdentifier;
        this.viewModel = viewModel;
    }

    @LayoutRes
    public int getLayoutResourceIdentifier() {
        return layoutResourceIdentifier;
    }

    public int getBindingVariableIdentifier() {
        return bindingVariableIdentifier;
    }

    public Object getViewModel() {
        return viewModel;
    }
}
