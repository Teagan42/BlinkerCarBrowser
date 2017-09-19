package rocks.teagantotally.blinkercarbrowser.di.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
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
public interface ApplicationContextComponent {
    /**
     * @return The application object
     */
    Application application();

    /**
     * @return The applciation context
     */
    Context applicationContext();
}
