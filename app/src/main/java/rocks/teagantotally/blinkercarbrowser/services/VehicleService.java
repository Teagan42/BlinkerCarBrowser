package rocks.teagantotally.blinkercarbrowser.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleQuery;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.RetrieveVehicleResultEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListResultEvent;

/**
 * Created by tglenn on 9/14/17.
 */

public class VehicleService
          extends Service {
    public static class Controller {
        private static final String TAG = "Controller";

        private EventBus eventBus;
        private VehicleDataStore vehicleDataStore;

        @Inject
        public Controller(EventBus eventBus,
                          VehicleDataStore vehicleDataStore) {
            this.eventBus = eventBus;
            this.vehicleDataStore = vehicleDataStore;
            eventBus.register(this);
        }

        public void unsubscribe() {
            if (eventBus.isRegistered(this)) {
                eventBus.unregister(this);
            }
        }

        @Subscribe(threadMode = ThreadMode.BACKGROUND)
        public void onEvent(RetrieveVehicleListEvent event) {
            try {
                List<Vehicle> results;
                if (event.getQueryTypes()
                         .containsAll(EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class))
                    && TextUtils.isEmpty(event.getQuery())) {

                    results = vehicleDataStore.getVehicles(event.getOffset(),
                                                           event.getLimit());
                } else {
                    VehicleQuery query = new VehicleQuery();
                    if (event.getQueryTypes()
                             .contains(RetrieveVehicleListEvent.QueryType.MAKE)) {
                        query.make = event.getQuery();
                    }
                    if (event.getQueryTypes()
                             .contains(RetrieveVehicleListEvent.QueryType.MODEL)) {
                        query.model = event.getQuery();
                    }
                    if (event.getQueryTypes()
                             .contains(RetrieveVehicleListEvent.QueryType.YEAR)) {
                        query.year = event.getQuery();
                    }

                    results = vehicleDataStore.queryVehicles(query,
                                                             event.getOffset(),
                                                             event.getLimit());
                }

                // Notify any subscribers to update
                for (Vehicle vehicle : results) {
                    eventBus.post(new RetrieveVehicleResultEvent(event,
                                                                 vehicle));
                }
                eventBus.post(new RetrieveVehicleListResultEvent(event,
                                                                 results));
            } catch (IOException e) {
                Log.e(TAG,
                      "onEvent: Error retrieving vehicles",
                      e);
                eventBus.post(new RetrieveVehicleListResultEvent(event,
                                                                 RetrieveResultEvent.Result.ERROR,
                                                                 e.getLocalizedMessage()));
            }
        }
    }

    @Inject
    EventBus eventBus;
    Controller controller;

    @Override
    public void onCreate() {
        Injector.get()
                .inject(this);
        controller = Injector.getServiceControllers()
                             .getVehicle();
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (controller != null) {
            controller.unsubscribe();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
