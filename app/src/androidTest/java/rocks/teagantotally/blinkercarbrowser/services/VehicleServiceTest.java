package rocks.teagantotally.blinkercarbrowser.services;

import android.support.annotation.IntRange;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.json.VehicleJsonDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.di.components.DaggerApplicationComponent;
import rocks.teagantotally.blinkercarbrowser.di.modules.TestDataStoreModule;
import rocks.teagantotally.blinkercarbrowser.di.modules.TestEventBusModule;
import rocks.teagantotally.blinkercarbrowser.events.RetrieveResultEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListEvent;
import rocks.teagantotally.blinkercarbrowser.events.vehicles.list.RetrieveVehicleListResultEvent;

import static junit.framework.Assert.assertEquals;

/**
 * Created by tglenn on 9/19/17.
 */

public class VehicleServiceTest {
    private class ExpectedResults {
        RetrieveVehicleListResultEvent.Result result;
        int count;
        String[] makes;

        ExpectedResults(RetrieveVehicleListResultEvent.Result result,
                        int count,
                        String[] makes) {
            this.result = result;
            this.count = count;
            this.makes = makes;
        }
    }

    private class TestEventBus
              extends EventBus {

        private ExpectedResults expectedResults = null;

        void setExpectedResults(ExpectedResults expectedResults) {
            this.expectedResults = expectedResults;
        }

        private void validateEvent(RetrieveVehicleListResultEvent event) {
            assertEquals(this.expectedResults.result,
                         event.getResult());
            if (this.expectedResults.result == RetrieveResultEvent.Result.ERROR) {
                return;
            }
            assertEquals(this.expectedResults.count,
                         event.getResults()
                              .size());
            for (int i = 0; i < this.expectedResults.count; i++) {
                assertEquals(this.expectedResults.makes[i],
                             event.getResults()
                                  .get(i).make);
            }
        }

        /**
         * Posts the given event to the event bus.
         *
         * @param event Event to be posted
         */
        @Override
        public void post(Object event) {
            if (event instanceof RetrieveVehicleListResultEvent) {
                validateEvent((RetrieveVehicleListResultEvent) event);
            }
        }
    }

    private VehicleService.Controller controller;
    private TestEventBus eventBus;

    @Before
    public void setup() {
        DaggerApplicationComponent.Builder builder =
                  DaggerApplicationComponent.builder()
                                            .dataStoreModule(new TestDataStoreModule())
                                            .eventBusModule(new TestEventBusModule(new TestEventBus()));

        Injector.initialize(builder);

        eventBus = (TestEventBus) Injector.get()
                                          .eventBus();
        // Service controllers are singletons - should not be injected or there will be cross test
        // effects
        controller = new VehicleService.Controller(eventBus,
                                                   Injector.get()
                                                           .getVehicleDataStore());
    }

    @Test
    public void retrieveFirstVehicle() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(0,
                                               1);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      1,
                                      new String[]{
                                                "Subaru"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void retrieveAllVehicles() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(0,
                                               1000);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      2,
                                      new String[]{
                                                "Subaru",
                                                "Honda"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void retrieveSecondVehicle() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(1,
                                               1);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      1,
                                      new String[]{
                                                "Honda"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void queryForSubaru() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class),
                                               "subaru",
                                               0,
                                               1);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      1,
                                      new String[]{
                                                "Subaru"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void queryForHonda() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class),
                                               "honda",
                                               0,
                                               1);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      1,
                                      new String[]{
                                                "Honda"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void queryByYear() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class),
                                               "2011",
                                               0,
                                               1);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      1,
                                      new String[]{
                                                "Subaru"
                                      });
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void queryWithNoResults() {
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(EnumSet.allOf(RetrieveVehicleListEvent.QueryType.class),
                                               "2049",
                                               0,
                                               10);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.SUCCESS,
                                      0,
                                      new String[0]);
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }

    @Test
    public void retrieveWithError() {
        VehicleJsonDataStore dataStore =
                  new VehicleJsonDataStore(Injector.get()
                                                   .applicationContext(),
                                           eventBus,
                                           R.raw.test_vehicles) {
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
                          throw new IOException("Network Error");
                      }
                  };
        controller = new VehicleService.Controller(eventBus,
                                                   dataStore);
        RetrieveVehicleListEvent event =
                  new RetrieveVehicleListEvent(0,
                                               10);
        ExpectedResults expectedResults =
                  new ExpectedResults(RetrieveResultEvent.Result.ERROR,
                                      0,
                                      new String[0]);
        eventBus.setExpectedResults(expectedResults);
        controller.onEvent(event);
    }
}
