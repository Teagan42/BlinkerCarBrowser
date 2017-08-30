package rocks.teagantotally.ibotta_challenge.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.ibotta_challenge.Application;

/**
 * Created by tglenn on 8/30/17.
 */

@Module
public class ApplicationContextModule {
    private android.app.Application application;

    /**
     * Creates a new application context module
     *
     * @param application
     */
    public ApplicationContextModule(Application application) {
        this.application = application;
    }

    /**
     * Provides the application dependency object
     *
     * @return
     */
    @Provides
    @Singleton
    public android.app.Application application() {
        return this.application;
    }

    /**
     * Provides the application context
     *
     * @return
     */
    @Provides
    @Singleton
    public Context applicationContext() {
        return this.application;
    }
}
