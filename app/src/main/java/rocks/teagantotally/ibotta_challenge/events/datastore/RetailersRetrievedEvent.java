package rocks.teagantotally.ibotta_challenge.events.datastore;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailersRetrievedEvent
          extends BaseEvent {
    private RetrieveRetailersEvent retrieveEvent;
    private List<Retailer> retailers;

    public RetailersRetrievedEvent(@NonNull RetrieveRetailersEvent retrieveEvent,
                                   @NonNull List<Retailer> retailers) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieval event cannot be null");
        Objects.requireNonNull(retailers,
                               "List of retailers cannot be null");
        this.retrieveEvent = retrieveEvent;
        this.retailers = retailers;
    }

    public RetrieveRetailersEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    public List<Retailer> getRetailers() {
        return retailers;
    }
}
