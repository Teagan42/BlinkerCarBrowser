package rocks.teagantotally.blinkercarbrowser.datastore.json;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleQuery;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;

/**
 * Created by tglenn on 9/14/17.
 */

public class VehicleJsonDataStore
          implements VehicleDataStore {
    private static final String TAG = "VehicleJsonDataStore";
    private static final Field[] queryFields = VehicleQuery.class.getDeclaredFields();

    private Context context;
    private EventBus eventBus;

    @Inject
    public VehicleJsonDataStore(Context context,
                                EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    private List<Vehicle> loadJson() throws
                                     IOException {
        Vehicle[] vehicles = JsonHelper.loadJson(context,
                                                 R.raw.vehicles,
                                                 Vehicle[].class);
        return new LinkedList<>(Arrays.asList(vehicles));
    }

    /**
     * QueryType vehicles
     *
     * @param offset Number of records to skip
     * @param limit  Maximum number of records to return
     * @return The result of the query
     */
    @Override
    public List<Vehicle> getVehicles(@IntRange(from = 0) int offset,
                                     @IntRange(from = 1) int limit) throws
                                                                    IOException {
        List<Vehicle> vehicles = loadJson();

        return vehicles.subList(Math.min(vehicles.size(),
                                         offset),
                                Math.min(vehicles.size(),
                                         offset + limit));
    }

    /**
     * Queries vehicles
     *
     * @param query  Values to query against
     * @param offset Number of records to skip
     * @param limit  Maximum number of records to return
     * @return The result of the query
     */
    @Override
    public List<Vehicle> queryVehicles(@NonNull VehicleQuery query,
                                       @IntRange(from = 0) int offset,
                                       @IntRange(from = 1) int limit) throws
                                                                      IOException {
        List<Vehicle> vehicles = loadJson();
        if (!query.hasQuery()) {
            return vehicles.subList(Math.min(vehicles.size(),
                                             offset),
                                    Math.min(vehicles.size(),
                                             offset + limit));
        }

        int i = 0;

        while (i < vehicles.size()) {
            boolean matchesQuery = false;
            Vehicle vehicle = vehicles.get(i);
            for (Field field : queryFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                try {
                    String queryFieldValue = (String) field.get(query);
                    String queryFieldName = field.getName();
                    Field vehicleField = Vehicle.class.getDeclaredField(queryFieldName);
                    String vehicleFieldValue = (String) vehicleField.get(vehicle);

                    if (vehicleFieldValue.toLowerCase()
                                         .trim()
                                         .contains(queryFieldValue.toLowerCase()
                                                                  .trim())) {
                        matchesQuery = true;
                        break;
                    }

                } catch (Exception e) {
                    Log.e(TAG,
                          "queryVehicles: Unable to determine if query matches",
                          e);
                }
            }

            if (!matchesQuery) {
                vehicles.remove(vehicle);
            } else {
                i++;
            }
        }

        return vehicles.subList(Math.min(vehicles.size(),
                                         offset),
                                Math.min(vehicles.size(),
                                         offset + limit));
    }
}
