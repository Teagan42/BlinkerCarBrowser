package rocks.teagantotally.blinkercarbrowser;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.services.VehicleService;

/**
 * Created by tglenn on 8/30/17.
 */

public class Application
          extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Injector.initialize(this);

        startServices();
    }

    private void startServices() {
        Intent vehicleServiceIntent = new Intent(this,
                                                 VehicleService.class);
        this.startService(vehicleServiceIntent);
    }
}
