package rocks.teagantotally.blinkercarbrowser.ui.vms;

import android.content.Context;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rocks.teagantotally.blinkercarbrowser.BR;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ViewScope;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;
import rocks.teagantotally.blinkercarbrowser.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.RetrieveVehicleResultEvent;
import rocks.teagantotally.blinkercarbrowser.ui.binding.recyclerview.ConditionalItemBinder;

/**
 * Created by tglenn on 9/14/17.
 */

@ViewScope
public class VehicleVM
          extends BaseVM {
    public static final ConditionalItemBinder<BaseVM> itemBinder =
              new ConditionalItemBinder<BaseVM>(BR.vm,
                                                R.layout.item_vehicle) {
                  @Override
                  public boolean canBind(BaseVM item) {
                      return item instanceof VehicleVM;
                  }
              };

    private String mileage;
    private String make;
    private String model;
    private String year;
    private String imageUrl;
    private int index;
    private View.OnClickListener moreDetailsClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      eventBus.post(new SnackbarNotificationEvent("This would take you to see more details",
                                                                  SnackbarNotificationEvent.Length.SHORT));
                  }
              };

    public VehicleVM(Context context,
                     EventBus eventBus) {
        super(eventBus);
    }

    /**
     * ]
     *
     * @return The mileage as text
     */
    public String getMileage() {
        return mileage;
    }

    /**
     * @return The vehicle make
     */
    public String getMake() {
        return make;
    }

    /**
     * @return The vehicle model
     */
    public String getModel() {
        return model;
    }

    /**
     * @return The year of manufacture
     */
    public String getYear() {
        return year;
    }

    /**
     * @return The url to the image
     */
    public String imageUrl() {
        return imageUrl;
    }

    /**
     * @return Click listener for more details
     */
    public View.OnClickListener getMoreDetailsClickListener() {
        return moreDetailsClickListener;
    }

    /**
     * Sets the data from the given model
     *
     * @param vehicle The vehicle data
     * @return This view model
     */
    public VehicleVM setModel(Vehicle vehicle) {
        this.index = vehicle.index;
        this.mileage = String.valueOf(vehicle.mileage);
        this.make = vehicle.make;
        this.model = vehicle.model;
        this.year = vehicle.year;
        this.imageUrl = vehicle.imageUrl;
        setShouldNotify(true);
        return this;
    }

    //region Event subscriptions

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveVehicleResultEvent event) {
        if (event.getResult() != RetrieveResultEvent.Result.SUCCESS) {
            // Let context handle errors
            return;
        }
        if (event.getVehicle().index != index) {
            // This is not this vehicle
            return;
        }

        setModel(event.getVehicle());
    }

    //endregion
}
