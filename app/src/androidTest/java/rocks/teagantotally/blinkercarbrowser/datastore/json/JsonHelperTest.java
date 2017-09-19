package rocks.teagantotally.blinkercarbrowser.datastore.json;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonSyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle;
import rocks.teagantotally.blinkercarbrowser.di.Injector;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by tglenn on 9/19/17.
 */

@RunWith(AndroidJUnit4.class)
public class JsonHelperTest {
    private Context context;

    @Before
    public void setup() {
        context = Injector.get()
                          .applicationContext();
    }

    @After
    public void tearDown() {
        context = null;
    }

    @Test
    public void loadJson() {
        try {
            Vehicle[] vehicles = JsonHelper.loadJson(context,
                                                     R.raw.test_vehicles,
                                                     Vehicle[].class);
            assertNotNull(vehicles);
            assertEquals(2,
                         vehicles.length);
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void loadJsonWithInvalidResourceId() {
        try {
            Vehicle[] vehicles = JsonHelper.loadJson(context,
                                                     10,
                                                     Vehicle[].class);
            fail("File should not exist");
        } catch (Exception e) {
            assertTrue("Invalid resource identifier should throw a NotFoundException",
                       e instanceof Resources.NotFoundException);
        }
    }

    @Test
    public void loadJsonWithWrongClassForDeserialization() {
        try {
            Integer[] results = JsonHelper.loadJson(context,
                                                    R.raw.test_vehicles,
                                                    Integer[].class);
            fail("Deserialization should fail");
        } catch (Exception e) {
            assertTrue("Deserialization should throw JsonSyntaxException, was " + e.getClass()
                                                                                   .getSimpleName(),
                       e instanceof JsonSyntaxException);
        }
    }
}
