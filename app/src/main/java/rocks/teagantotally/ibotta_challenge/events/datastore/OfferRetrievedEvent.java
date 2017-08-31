package rocks.teagantotally.ibotta_challenge.events.datastore;

import android.support.annotation.NonNull;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferRetrievedEvent
          extends BaseEvent {
    private RetrieveOfferEvent retrieveEvent;
    private Offer offer;

    public OfferRetrievedEvent(@NonNull RetrieveOfferEvent retrieveEvent,
                               @NonNull Offer offer) {
        Objects.requireNonNull(retrieveEvent,
                               "Retrieve event cannot be null");
        Objects.requireNonNull(offer,
                               "Offer cannot be null");
        this.retrieveEvent = retrieveEvent;
        this.offer = offer;
    }

    /**
     * @return The event that triggered the retrieval
     */
    public RetrieveOfferEvent getRetrieveEvent() {
        return retrieveEvent;
    }

    /**
     * @return The offer that was retrieved
     */
    public Offer getOffer() {
        return offer;
    }
}
