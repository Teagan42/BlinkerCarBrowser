package rocks.teagantotally.blinkercarbrowser.di.components;

import javax.inject.Singleton;

import dagger.Component;
import rocks.teagantotally.blinkercarbrowser.di.modules.ApplicationContextModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.DataStoreModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.EventBusModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.ServiceControllerModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.ViewModelModule;
import rocks.teagantotally.blinkercarbrowser.services.VehicleService;
import rocks.teagantotally.blinkercarbrowser.ui.fragments.VehicleListFragment;

/**
 * Created by tglenn on 8/30/17.
 */

@Singleton
@Component(modules = {
          ApplicationContextModule.class,
          DataStoreModule.class,
          EventBusModule.class
})
public interface ApplicationComponent
          extends ApplicationContextComponent,
                  DataStoreComponent,
                  EventBusComponent {
    ServiceControllerComponent addServiceControllers(ServiceControllerModule controllerModule);

    ViewModelComponent addViewModels(ViewModelModule viewModelModule);

    void inject(VehicleListFragment fragment);

    void inject(VehicleService service);
}
