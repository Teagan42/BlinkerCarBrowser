package rocks.teagantotally.ibotta_challenge.events.datastore;

import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetrieveRetailersEvent
          extends BaseEvent {
    private int offset;
    private int limit;

    public RetrieveRetailersEvent(int offset,
                                  int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * @return The number of records to skip
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return The number of records to return
     */
    public int getLimit() {
        return limit;
    }
}
