package rocks.teagantotally.ibotta_challenge.events.datastore;

import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class RetrieveOffersEvent
          extends BaseEvent {
    private long retailerId;

    public RetrieveOffersEvent(long retailerId) {
        this.retailerId = retailerId;
    }

    public long getRetailerId() {
        return retailerId;
    }
}
