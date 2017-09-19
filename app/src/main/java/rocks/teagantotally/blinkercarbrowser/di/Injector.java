package rocks.teagantotally.blinkercarbrowser.di;

import android.support.multidex.MultiDex;

import rocks.teagantotally.blinkercarbrowser.Application;
import rocks.teagantotally.blinkercarbrowser.di.components.ApplicationComponent;
import rocks.teagantotally.blinkercarbrowser.di.components.DaggerApplicationComponent;
import rocks.teagantotally.blinkercarbrowser.di.components.ServiceControllerComponent;
import rocks.teagantotally.blinkercarbrowser.di.components.ViewModelComponent;
import rocks.teagantotally.blinkercarbrowser.di.modules.ApplicationContextModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.ServiceControllerModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.ViewModelModule;

/**
 * Created by tglenn on 8/30/17.
 */

public enum Injector {
    INSTANCE;

    ApplicationComponent applicationComponent;
    ServiceControllerComponent serviceControllerComponent;
    ViewModelComponent viewModelComponent;
    Application application;

    Injector() {

    }

    /**
     * Initialize with a custom build dagger component builder
     *
     * @param builder Component builder
     */
    public static void initialize(DaggerApplicationComponent.Builder builder) {
        ApplicationContextModule appContext = new ApplicationContextModule(INSTANCE.application);
        builder.applicationContextModule(appContext);

        INSTANCE.applicationComponent = builder.build();
    }

    /**
     * Initialize the singleton instance
     *
     * @param application The application object
     */
    public static void initialize(Application application) {
        if (INSTANCE.applicationComponent != null) {
            throw new IllegalStateException("The injector has already been initialized");
        }

        INSTANCE.application = application;

        MultiDex.install(application);

        initialize(DaggerApplicationComponent.builder());
    }

    /**
     * @return The application component
     */
    public static ApplicationComponent get() {
        return INSTANCE.applicationComponent;
    }

    /**
     * @return The view model component
     */
    public static ViewModelComponent getViewModels() {
        // Lazy load view scoped component
        if (INSTANCE.viewModelComponent == null) {
            INSTANCE.viewModelComponent =
                      get().addViewModels(new ViewModelModule());
        }
        return INSTANCE.viewModelComponent;
    }

    /**
     * Clears the view model component lifecycle
     */
    public static void clearViewModels() {
        INSTANCE.viewModelComponent = null;
    }

    /**
     * @return The service controller component
     */
    public static ServiceControllerComponent getServiceControllers() {
        // Lazy load service scoped component
        if (INSTANCE.serviceControllerComponent == null) {
            INSTANCE.serviceControllerComponent =
                      get().addServiceControllers(new ServiceControllerModule());
        }
        return INSTANCE.serviceControllerComponent;
    }

    /**
     * Clears the service controller component lifecycle
     */
    public static void clearServiceControllers() {
        INSTANCE.serviceControllerComponent = null;
    }
}
