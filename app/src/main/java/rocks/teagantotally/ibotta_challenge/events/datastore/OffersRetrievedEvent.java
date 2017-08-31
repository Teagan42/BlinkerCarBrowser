package rocks.teagantotally.ibotta_challenge.events.datastore;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class OffersRetrievedEvent
          extends BaseEvent {
    private RetrieveOffersEvent retrieveEvent;
    private List<Offer> offers;

    public OffersRetrievedEvent(@NonNull RetrieveOffersEvent retrieveEvent,
                                @NonNull List<Offer> offers) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieve event cannot be null");
        Objects.requireNonNull(offers,
                               "List of offers cannot be null");
        this.retrieveEvent = retrieveEvent;
        this.offers = offers;
    }

    public RetrieveOffersEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
