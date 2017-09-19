package rocks.teagantotally.blinkercarbrowser.di.modules;

import android.content.Context;
import android.support.annotation.RawRes;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.json.VehicleJsonDataStore;

/**
 * Created by tglenn on 8/30/17.
 */

@Module
public class DataStoreModule {
    /**
     * Get the vehicle data store
     *
     * @param context  Context
     * @param eventBus Event bus to subscribe and post events to
     * @return The vehicle data store
     */
    @Provides
    @Singleton
    public VehicleDataStore getVehicleDataStore(Context context,
                                                EventBus eventBus,
                                                @Named("Vehicles")
                                                @RawRes int vehicleDataFile) {
        return new VehicleJsonDataStore(context,
                                        eventBus,
                                        vehicleDataFile);
    }

    /**
     * @return The resource identifier for vehicle json file
     */
    @Named("Vehicles")
    @Provides
    @Singleton
    @RawRes
    public int vehicleDataJsonFileResourceIdentifer() {
        return R.raw.vehicles;
    }
}
