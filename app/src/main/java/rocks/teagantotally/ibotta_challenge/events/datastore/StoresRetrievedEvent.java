package rocks.teagantotally.ibotta_challenge.events.datastore;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class StoresRetrievedEvent
          extends BaseEvent {
    private RetrieveStoresEvent retrieveEvent;
    private List<Store> stores;

    public StoresRetrievedEvent(@NonNull RetrieveStoresEvent retrieveEvent,
                                @NonNull List<Store> stores) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieve event cannot be null");
        Objects.requireNonNull(stores,
                               "List of stores cannot be null");
        this.retrieveEvent = retrieveEvent;
        this.stores = stores;
    }

    /**
     * @return The event that caused the retrieval
     */
    public RetrieveStoresEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    /**
     * @return The stores returned by the query
     */
    public List<Store> getStores() {
        return stores;
    }
}
