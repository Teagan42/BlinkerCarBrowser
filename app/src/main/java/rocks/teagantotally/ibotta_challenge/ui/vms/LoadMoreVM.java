package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.databinding.BaseObservable;

import rocks.teagantotally.ibotta_challenge.BR;
import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.ConditionalItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.handlers.AttachedToWindowHandler;

/**
 * Created by tglenn on 8/30/17.
 */

public class LoadMoreVM
          extends BaseObservable
          implements AttachedToWindowHandler {
    public static final ConditionalItemBinder<BaseObservable> itemBinder =
              new ConditionalItemBinder<BaseObservable>(BR.vm,
                                                        R.layout.item_load_more) {
                  @Override
                  public boolean canBind(BaseObservable item) {
                      return item instanceof LoadMoreVM;
                  }
              };

    private BaseEvent event;

    public LoadMoreVM(BaseEvent eventToPost) {
        this.event = eventToPost;
    }

    @Override
    public void onViewAttachedToWindow() {
        event.post();
    }
}
