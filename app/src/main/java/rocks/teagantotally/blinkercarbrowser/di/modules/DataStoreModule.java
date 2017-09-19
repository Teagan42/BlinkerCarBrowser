package rocks.teagantotally.blinkercarbrowser.di.modules;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.json.VehicleJsonDataStore;

/**
 * Created by tglenn on 8/30/17.
 */

@Module
public class DataStoreModule {
    @Provides
    @Singleton
    public VehicleDataStore getVehicleDataStore(Context context,
                                                EventBus eventBus) {
        return new VehicleJsonDataStore(context,
                                        eventBus);
    }
}
