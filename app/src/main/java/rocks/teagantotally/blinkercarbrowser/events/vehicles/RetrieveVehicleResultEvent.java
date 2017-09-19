package rocks.teagantotally.blinkercarbrowser.events.vehicles;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.events.BaseEvent;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;

/**
 * Created by tglenn on 9/14/17.
 */

public class RetrieveVehicleResultEvent
          extends RetrieveResultEvent<BaseEvent> {

    private Vehicle vehicle;

    /**
     * Create a new retrieve result event
     *
     * @param retrieveEvent Retrieve event
     * @param vehicle       The result vehicle model
     */
    public RetrieveVehicleResultEvent(@NonNull BaseEvent retrieveEvent,
                                      @NonNull Vehicle vehicle) {
        super(retrieveEvent,
              Result.SUCCESS,
              null);

        this.vehicle = vehicle;
    }

    /**
     * Create a new retrieve result event
     *
     * @param retrieveEvent Retrieve event
     * @param result        Result of the retrieve event
     * @param errorReason   The reason for the error
     */
    public RetrieveVehicleResultEvent(@NonNull BaseEvent retrieveEvent,
                                      @NonNull Result result,
                                      @Nullable String errorReason) {
        super(retrieveEvent,
              result,
              errorReason);
    }

    /**
     * @return The vehicle returned
     */
    public Vehicle getVehicle() {
        return vehicle;
    }
}
