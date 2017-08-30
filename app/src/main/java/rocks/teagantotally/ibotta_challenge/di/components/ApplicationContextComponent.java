package rocks.teagantotally.ibotta_challenge.di.components;

import android.app.Application;
import android.content.Context;

import dagger.Component;
import rocks.teagantotally.ibotta_challenge.di.modules.ApplicationContextModule;

/**
 * Created by tglenn on 8/30/17.
 */

@Component(modules = {ApplicationContextModule.class})
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
