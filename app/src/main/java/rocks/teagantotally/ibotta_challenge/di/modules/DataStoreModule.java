package rocks.teagantotally.ibotta_challenge.di.modules;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.teagantotally.ibotta_challenge.datastore.OfferDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.StoreDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.json.OfferJsonDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.json.RetailerJsonDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.json.StoreJsonDataStore;

/**
 * Created by tglenn on 8/30/17.
 */

@Module
public class DataStoreModule {
    @Provides
    @Singleton
    public StoreDataStore getStoreDataStore(Context context,
                                            EventBus eventBus) {
        return new StoreJsonDataStore(context,
                                      eventBus);
    }

    @Provides
    @Singleton
    public RetailerDataStore getRetailerDataStore(Context context,
                                                  EventBus eventBus) {
        return new RetailerJsonDataStore(context,
                                         eventBus);
    }

    @Provides
    @Singleton
    public OfferDataStore getOfferDataStore(Context context,
                                            EventBus eventBus) {
        return new OfferJsonDataStore(context,
                                      eventBus);
    }
}
