package rocks.teagantotally.blinkercarbrowser.events.vehicles.list;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.EnumSet;
import java.util.Objects;

import rocks.teagantotally.blinkercarbrowser.events.BaseEvent;

/**
 * Created by tglenn on 9/14/17.
 */

public class RetrieveVehicleListEvent
          extends BaseEvent {
    public enum QueryType {
        MAKE,
        MODEL,
        YEAR
    }

    private int offset;
    private int limit;
    private EnumSet<QueryType> queryTypes = EnumSet.allOf(QueryType.class);
    private String query;

    /**
     * Create a new retrieve vehicle list event
     *
     * @param offset Number of records to skip
     * @param limit  Maximum number of records to return
     */
    public RetrieveVehicleListEvent(@IntRange(from = 0) int offset,
                                    @IntRange(from = 1) int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be greater than or equal to 0");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be a positive integer");
        }

        this.offset = offset;
        this.limit = limit;
    }

    /**
     * Create a new retrieve vehicle list event
     *
     * @param queryTypes Query types
     * @param query      Query value
     * @param offset     Number of records to skip
     * @param limit      Maximum number of records to return
     */
    public RetrieveVehicleListEvent(@NonNull EnumSet<QueryType> queryTypes,
                                    String query,
                                    @IntRange(from = 0) int offset,
                                    @IntRange(from = 1) int limit) {
        this(offset,
             limit);
        Objects.requireNonNull(queryTypes,
                               "Query type cannot be null");
        this.queryTypes = queryTypes;
        this.query = query;
    }

    /**
     * @return The number of records to skip
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return The maximum number of records to return
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @return The query types
     */
    public EnumSet<QueryType> getQueryTypes() {
        return queryTypes;
    }

    /**
     * @return The query value
     */
    public String getQuery() {
        return query;
    }
}
