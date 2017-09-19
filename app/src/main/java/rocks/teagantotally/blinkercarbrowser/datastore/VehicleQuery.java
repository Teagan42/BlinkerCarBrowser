package rocks.teagantotally.blinkercarbrowser.datastore;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by tglenn on 9/14/17.
 */

public class VehicleQuery {
    private static final String TAG = "VehicleQuery";
    private static final Field[] fields = VehicleQuery.class.getDeclaredFields();

    // THESE FIELDS MUST MATCH FIELDS IN MODEL

    public String year;
    public String make;
    public String model;

    /**
     * @return Whether any field is set
     */
    public boolean hasQuery() {
        for (Field field : fields) {
            String fieldValue;

            try {
                fieldValue = (String) field.get(this);
            } catch (IllegalAccessException e) {
                Log.w(TAG,
                      "hasQuery: Unable to retrieve field value " + field.getName(),
                      e);
                return true;
            }

            if (!TextUtils.isEmpty(fieldValue)) {
                return true;
            }
        }

        return false;
    }
}
