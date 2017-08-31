package rocks.teagantotally.ibotta_challenge;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import rocks.teagantotally.ibotta_challenge.di.Injector;

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
