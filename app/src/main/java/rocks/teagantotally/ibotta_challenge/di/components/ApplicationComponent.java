package rocks.teagantotally.ibotta_challenge.di.components;

import javax.inject.Singleton;

import dagger.Component;
import rocks.teagantotally.ibotta_challenge.di.modules.ApplicationContextModule;
import rocks.teagantotally.ibotta_challenge.di.modules.DataStoreModule;
import rocks.teagantotally.ibotta_challenge.di.modules.EventBusModule;
import rocks.teagantotally.ibotta_challenge.ui.fragments.OffersListFragment;
import rocks.teagantotally.ibotta_challenge.ui.fragments.RetailerListFragment;

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
                  EventBusComponent {
    void inject(RetailerListFragment fragment);

    void inject(OffersListFragment fragment);
}
