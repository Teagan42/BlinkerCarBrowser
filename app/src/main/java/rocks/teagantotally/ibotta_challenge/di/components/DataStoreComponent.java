package rocks.teagantotally.ibotta_challenge.di.components;

import javax.inject.Singleton;

import dagger.Component;
import rocks.teagantotally.ibotta_challenge.datastore.OfferDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.StoreDataStore;
import rocks.teagantotally.ibotta_challenge.di.modules.ApplicationContextModule;
import rocks.teagantotally.ibotta_challenge.di.modules.DataStoreModule;
import rocks.teagantotally.ibotta_challenge.di.modules.EventBusModule;

/**
 * Created by tglenn on 8/30/17.
 */

@Singleton
@Component(modules = {
          ApplicationContextModule.class,
          DataStoreModule.class,
          EventBusModule.class
})
public interface DataStoreComponent {
    /**
     * @return The current implementation of the store data store
     */
    StoreDataStore getStoreDataStore();

    /**
     * @return The current implementation of the retailer data store
     */
    RetailerDataStore getRetailerDataStore();

    /**
     * @return The current implementation of the offer data store
     */
    OfferDataStore getOfferDataStore();
}
