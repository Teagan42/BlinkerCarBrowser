package rocks.teagantotally.blinkercarbrowser.events.vehicles.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;

/**
 * Created by tglenn on 9/14/17.
 */

public class RetrieveVehicleListResultEvent
          extends RetrieveResultEvent<RetrieveVehicleListEvent> {
    private List<Vehicle> results;

    /**
     * Create a new retrieve result event
     *
     * @param retrieveEvent The retrieval event
     * @param results       Records returned by the query
     */
    public RetrieveVehicleListResultEvent(@NonNull RetrieveVehicleListEvent retrieveEvent,
                                          @NonNull List<Vehicle> results) {
        super(retrieveEvent,
              Result.SUCCESS,
              null);
        Objects.requireNonNull(results,
                               "Results cannot be null");
        this.results = results;
    }

    /**
     * Create a new retrieve result event
     *
     * @param retrieveEvent The retrieval event
     * @param result        Result of the retrieve event
     * @param errorReason   The reason for the error
     */
    public RetrieveVehicleListResultEvent(@NonNull RetrieveVehicleListEvent retrieveEvent,
                                          @NonNull Result result,
                                          @Nullable String errorReason) {
        super(retrieveEvent,
              result,
              errorReason);
    }

    /**
     * @return The vehicles returned by the query
     */
    public List<Vehicle> getResults() {
        return results;
    }
}
