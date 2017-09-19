package rocks.teagantotally.blinkercarbrowser.datastore.json;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleQuery;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.di.components.DaggerApplicationComponent;
import rocks.teagantotally.blinkercarbrowser.di.modules.TestDataStoreModule;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by tglenn on 9/19/17.
 */


public class VehicleJsonDataStoreTest {
    static final int testVehicleCount = 2;

    VehicleDataStore vehicleDataStore;

    @Before
    public void setup() {
        DaggerApplicationComponent.Builder builder =
                  DaggerApplicationComponent.builder()
                                            .dataStoreModule(new TestDataStoreModule());

        Injector.initialize(builder);
        vehicleDataStore = Injector.get()
                                   .getVehicleDataStore();
    }

    @Test
    public void getVehicles() {
        try {
            List<Vehicle> results = vehicleDataStore.getVehicles(0,
                                                                 10);
            assertNotNull(results);
            assertEquals(testVehicleCount,
                         results.size());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void getVehiclesInvalidOffset() {
        try {
            vehicleDataStore.getVehicles(-1,
                                         10);
            fail("An exception should be thrown");
        } catch (Exception e) {
            assertTrue("Exception should be an IllegalArgumentException, was " + e.getClass()
                                                                                  .getSimpleName(),
                       e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void getVehiclesInvalidLimit() {
        try {
            vehicleDataStore.getVehicles(1,
                                         -1);
            fail("An exception should be thrown");
        } catch (Exception e) {
            assertTrue("Exception should be an IllegalArgumentException, was " + e.getClass()
                                                                                  .getSimpleName(),
                       e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void getVehiclesPastDataSetSize() {
        try {
            List<Vehicle> results = vehicleDataStore.getVehicles(100,
                                                                 10);
            assertNotNull(results);
            assertTrue(results.isEmpty());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void queryVehicles() {
        try {
            VehicleQuery query = new VehicleQuery();
            query.make = "Subaru";
            List<Vehicle> results =
                      vehicleDataStore.queryVehicles(query,
                                                     0,
                                                     10);
            assertNotNull(results);
            assertEquals(1,
                         results.size());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void queryVehiclesNullQuery() {
        try {
            vehicleDataStore.queryVehicles(null,
                                           -1,
                                           10);
            fail("An exception should be thrown");
        } catch (Exception e) {
            assertTrue("Exception should be an NullPointerException, was " + e.getClass()
                                                                              .getSimpleName(),
                       e instanceof NullPointerException);
        }
    }

    @Test
    public void queryVehiclesInvalidOffset() {
        try {
            VehicleQuery query = new VehicleQuery();
            query.make = "Subaru";
            vehicleDataStore.queryVehicles(query,
                                           -1,
                                           10);
            fail("An exception should be thrown");
        } catch (Exception e) {
            assertTrue("Exception should be an IllegalArgumentException, was " + e.getClass()
                                                                                  .getSimpleName(),
                       e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void queryVehiclesInvalidLimit() {
        try {
            VehicleQuery query = new VehicleQuery();
            query.make = "Subaru";
            vehicleDataStore.queryVehicles(query,
                                           1,
                                           -1);
            fail("An exception should be thrown");
        } catch (Exception e) {
            assertTrue("Exception should be an IllegalArgumentException, was " + e.getClass()
                                                                                  .getSimpleName(),
                       e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void queryVehiclesPastDataSetSize() {
        try {
            VehicleQuery query = new VehicleQuery();
            query.make = "Subaru";
            List<Vehicle> results =
                      vehicleDataStore.queryVehicles(query,
                                                     100,
                                                     10);
            assertNotNull(results);
            assertTrue(results.isEmpty());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void queryVehiclesNoMatch() {
        try {
            VehicleQuery query = new VehicleQuery();
            query.make = "Jimbo";
            List<Vehicle> results =
                      vehicleDataStore.queryVehicles(query,
                                                     0,
                                                     10);
            assertNotNull(results);
            assertTrue(results.isEmpty());
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }
}
