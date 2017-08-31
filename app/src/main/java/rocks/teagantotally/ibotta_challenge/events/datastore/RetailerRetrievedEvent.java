package rocks.teagantotally.ibotta_challenge.events.datastore;

import android.support.annotation.NonNull;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class RetailerRetrievedEvent
          extends BaseEvent {
    private RetrieveRetailerEvent retrieveEvent;
    private Retailer retailer;

    public RetailerRetrievedEvent(@NonNull RetrieveRetailerEvent retrieveEvent,
                                  @NonNull Retailer retailer) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieve event cannot be null");
        Objects.requireNonNull(retailer,
                               "Retailer cannot be null");
        this.retrieveEvent = retrieveEvent;
        this.retailer = retailer;
    }

    public RetrieveRetailerEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    public Retailer getRetailer() {
        return retailer;
    }
}
