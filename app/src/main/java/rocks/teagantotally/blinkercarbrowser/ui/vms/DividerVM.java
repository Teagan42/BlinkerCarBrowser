package rocks.teagantotally.blinkercarbrowser.ui.vms;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.BR;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.ConditionalItemBinder;

/**
 * Created by tglenn on 9/16/17.
 */

@ViewScope
public class DividerVM
          extends BaseVM {

    public static final ConditionalItemBinder<BaseVM> itemBinder =
              new ConditionalItemBinder<BaseVM>(BR.vm,
                                                R.layout.item_year_divider) {
                  @Override
                  public boolean canBind(BaseVM item) {
                      return item instanceof DividerVM;
                  }
              };

    private String title;

    /**
     * Create a new view model instance
     *
     * @param eventBus Event bus to register with
     */
    @Inject
    public DividerVM(EventBus eventBus) {
        super(eventBus);
    }

    /**
     * Set the title
     *
     * @param title Year text
     * @return This view model
     */
    public DividerVM setTitle(String title) {
        if (TextUtils.equals(this.title,
                             title)) {
            return this;
        }
        this.title = title;
        notifyChange();
        return this;
    }

    /**
     * @return The title text
     */
    public String getTitle() {
        return title;
    }
}
