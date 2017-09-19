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
public class YearDividerVM
          extends BaseVM {

    public static final ConditionalItemBinder<BaseVM> itemBinder =
              new ConditionalItemBinder<BaseVM>(BR.vm,
                                                R.layout.item_year_divider) {
                  @Override
                  public boolean canBind(BaseVM item) {
                      return item instanceof YearDividerVM;
                  }
              };

    private String year;

    /**
     * Create a new view model instance
     *
     * @param eventBus Event bus to register with
     */
    @Inject
    public YearDividerVM(EventBus eventBus) {
        super(eventBus);
    }

    /**
     * Set the year
     *
     * @param year Year text
     * @return This view model
     */
    public YearDividerVM setYear(String year) {
        if (TextUtils.equals(this.year,
                             year)) {
            return this;
        }
        this.year = year;
        notifyChange();
        return this;
    }

    /**
     * @return The year text
     */
    public String getYear() {
        return year;
    }
}
