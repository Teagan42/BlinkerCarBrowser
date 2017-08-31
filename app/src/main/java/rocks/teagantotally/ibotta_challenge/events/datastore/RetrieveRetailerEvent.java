package rocks.teagantotally.ibotta_challenge.events.datastore;

import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/31/17.
 */

public class RetrieveRetailerEvent
          extends BaseEvent {
    private long retailerId;

    public RetrieveRetailerEvent(long retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * @return The id of the retailer to retrieve
     */
    public long getRetailerId() {
        return retailerId;
    }
}
