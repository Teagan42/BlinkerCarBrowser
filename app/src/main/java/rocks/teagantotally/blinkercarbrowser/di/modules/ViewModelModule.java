package rocks.teagantotally.blinkercarbrowser.di.modules;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.ui.vms.LoadMoreVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.SearchOptionsVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.VehicleListVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.VehicleVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.YearDividerVM;

/**
 * Created by tglenn on 9/14/17.
 */

@Module
public class ViewModelModule {
    /**
     * Creates a new vehicle list view model
     *
     * @param context  Context
     * @param eventBus Event bus to subscribe and post events to
     * @return A new vehicle list view model
     */
    @Provides
    @ViewScope
    public VehicleListVM getVehicleList(Context context,
                                        EventBus eventBus) {
        return new VehicleListVM(context,
                                 eventBus);
    }

    /**
     * Creates a new vehicle view model
     *
     * @param context  Context
     * @param eventBus Event bus to subscribe and post events to
     * @return A new vehicle view model
     */
    @Provides
    @ViewScope
    public VehicleVM getVehicle(Context context,
                                EventBus eventBus) {
        return new VehicleVM(context,
                             eventBus);
    }

    /**
     * Creates a new load more view model
     *
     * @param eventBus Event bus to subscribe and post events to
     * @return A new load more view model
     */
    @Provides
    @ViewScope
    public LoadMoreVM getLoadMore(EventBus eventBus) {
        return new LoadMoreVM(eventBus);
    }

    /**
     * Creates a new year divider view model
     *
     * @param eventBus Event bus to subscribe and post events to
     * @return A new year divider view model
     */
    @Provides
    @ViewScope
    public YearDividerVM getYearDivider(EventBus eventBus) {
        return new YearDividerVM(eventBus);
    }

    /**
     * Creates a new search options view model
     *
     * @param eventBus Event bus to subscribe and post events to
     * @return A new search options view model
     */
    @Provides
    @ViewScope
    public SearchOptionsVM getSearchOptions(EventBus eventBus) {
        return new SearchOptionsVM(eventBus);
    }
}
