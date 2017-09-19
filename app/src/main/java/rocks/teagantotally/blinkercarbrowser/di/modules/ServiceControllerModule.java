package rocks.teagantotally.blinkercarbrowser.di.modules;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ServiceScope;
import rocks.teagantotally.blinkercarbrowser.services.VehicleService;

/**
 * Created by tglenn on 9/14/17.
 */

@Module
public class ServiceControllerModule {
    /**
     * Provides the vehicle controller dependency
     *
     * @param eventBus         Event bus to subscribe and post events to
     * @param vehicleDataStore Data store to get vehicle data
     * @return The vehicle service controller
     */
    @Provides
    @ServiceScope
    public VehicleService.Controller getVehicle(EventBus eventBus,
                                                VehicleDataStore vehicleDataStore) {
        return new VehicleService.Controller(eventBus,
                                             vehicleDataStore);
    }
}
