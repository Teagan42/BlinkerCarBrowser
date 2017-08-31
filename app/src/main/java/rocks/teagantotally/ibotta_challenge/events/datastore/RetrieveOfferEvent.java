package rocks.teagantotally.ibotta_challenge.events.datastore;

import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class RetrieveOfferEvent
          extends BaseEvent {
    private long offerId;

    public RetrieveOfferEvent(long offerId) {
        this.offerId = offerId;
    }

    /**
     * @return The id of the offer to retrieve
     */
    public long getOfferId() {
        return offerId;
    }
}
