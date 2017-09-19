package rocks.teagantotally.blinkercarbrowser.di.components;

import android.support.annotation.RawRes;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.di.modules.ApplicationContextModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.DataStoreModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.EventBusModule;

/**
 * Created by tglenn on 8/30/17.
 */

@Singleton
@Component(modules = {
          ApplicationContextModule.class,
          DataStoreModule.class,
          EventBusModule.class
})
public interface DataStoreComponent {
    /**
     * @return The current implementation of the vehicle data store
     */
    VehicleDataStore getVehicleDataStore();

    /**
     * @return The resource identifier for vehicle json file
     */
    @Named("Vehicles")
    @RawRes
    int vehicleDataJsonFileResourceIdentifer();
}
