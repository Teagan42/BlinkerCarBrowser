package rocks.teagantotally.blinkercarbrowser.datastore;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;

/**
 * Created by tglenn on 9/14/17.
 */

public interface VehicleDataStore {
    /**
     * QueryType vehicles
     *
     * @param offset Number of records to skip
     * @param limit  Maximum number of records to return
     * @return The result of the query
     */
    List<Vehicle> getVehicles(@IntRange(from = 0) int offset,
                              @IntRange(from = 1) int limit) throws
                                                             IOException;

    /**
     * Queries vehicles
     *
     * @param query  Values to query against
     * @param offset Number of records to skip
     * @param limit  Maximum number of records to return
     * @return The result of the query
     */
    List<Vehicle> queryVehicles(@NonNull VehicleQuery query,
                                @IntRange(from = 0) int offset,
                                @IntRange(from = 1) int limit) throws
                                                               IOException;


}
