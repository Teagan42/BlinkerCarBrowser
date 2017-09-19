package rocks.teagantotally.blinkercarbrowser.services;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

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
}
