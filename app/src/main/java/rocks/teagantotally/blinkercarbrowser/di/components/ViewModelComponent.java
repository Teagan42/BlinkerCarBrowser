package rocks.teagantotally.blinkercarbrowser.di.components;

import dagger.Subcomponent;
import rocks.teagantotally.blinkercarbrowser.di.modules.ViewModelModule;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.ui.vms.DividerVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.LoadMoreVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.SearchOptionsVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.VehicleListVM;
import rocks.teagantotally.blinkercarbrowser.ui.vms.VehicleVM;

/**
 * Created by tglenn on 9/14/17.
 */

@ViewScope
@Subcomponent(modules = {ViewModelModule.class})
public interface ViewModelComponent {
    /**
     * @return A new vehicle list view model
     */
    VehicleListVM getVehicleList();

    /**
     * @return A new vehicle view model
     */
    VehicleVM getVehicle();

    /**
     * @return A new load more view model
     */
    LoadMoreVM getLoadMore();

    /**
     * @return A new divider view model
     */
    DividerVM getDivider();

    /**
     * @return A new search options view model
     */
    SearchOptionsVM getSearchOptions();
}
