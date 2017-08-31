package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.StoreDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.datastore.models.StoreContainer;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class StoreJsonDataStore
          implements StoreDataStore {

    @Inject
    Context context;
    @Inject
    EventBus eventBus;

    @Inject
    public StoreJsonDataStore(Context context,
                              EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    private List<Store> loadJson() throws
                                   IOException {
        StoreContainer container = JsonHelper.loadJson(context,
                                                       R.raw.stores,
                                                       StoreContainer.class);
        return Arrays.asList(container.stores);
    }

    @Override
    public List<Store> getStores(int offset,
                                 int limit) {
        List<Store> stores;
        try {
            stores = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        return stores.subList(offset,
                              offset + limit);
    }

    @Override
    public Store getStoreById(long storeId) {
        List<Store> stores;
        try {
            stores = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        for (Store store : stores) {
            if (store.id == storeId) {
                return store;
            }
        }

        return null;
    }

    @Override
    public List<Store> getStoresByRetailer(long retailerId) {
        List<Store> result = new ArrayList<>();
        List<Store> stores;

        try {
            stores = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }


        for (Store store : stores) {
            if (store.retailerId == retailerId) {
                result.add(store);
            }
        }

        return result;
    }
}
