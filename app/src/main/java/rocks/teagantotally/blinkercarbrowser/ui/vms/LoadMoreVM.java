package rocks.teagantotally.blinkercarbrowser.ui.vms;

import org.greenrobot.eventbus.EventBus;

import rocks.teagantotally.blinkercarbrowser.BR;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.events.BaseEvent;
import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.ConditionalItemBinder;
import rocks.teagantotally.blinkercarbrowser.ui.handlers.AttachedToWindowHandler;

/**
 * Created by tglenn on 8/30/17.
 */

@ViewScope
public class LoadMoreVM
          extends BaseVM
          implements AttachedToWindowHandler {
    public static final ConditionalItemBinder<BaseVM> itemBinder =
              new ConditionalItemBinder<BaseVM>(BR.vm,
                                                R.layout.item_view_more) {
                  @Override
                  public boolean canBind(BaseVM item) {
                      return item instanceof LoadMoreVM;
                  }
              };

    private BaseEvent event;

    /**
     * Create a new load more view model
     *
     * @param eventBus Event bus to subscribe and post events to
     */
    public LoadMoreVM(EventBus eventBus) {
        super(eventBus);
    }

    /**
     * Sets the event to fire when loading more
     *
     * @param event Event to fire when view is attached to window
     * @return The view model object for chaining
     */
    public LoadMoreVM setLoadMoreEvent(BaseEvent event) {
        this.event = event;
        return this;
    }


    /**
     * Handle when this is attached to the window
     */
    @Override
    public void onViewAttachedToWindow() {
        eventBus.post(event);
    }
}
