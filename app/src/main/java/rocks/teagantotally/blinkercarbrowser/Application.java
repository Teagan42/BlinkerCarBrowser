package rocks.teagantotally.blinkercarbrowser;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import rocks.teagantotally.blinkercarbrowser.di.Injector;

/**
 * Created by tglenn on 8/30/17.
 */

public class Application
          extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Injector.initialize(this);
    }
}
