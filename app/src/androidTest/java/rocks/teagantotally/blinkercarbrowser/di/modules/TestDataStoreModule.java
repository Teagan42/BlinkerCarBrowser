package rocks.teagantotally.blinkercarbrowser.di.modules;

import rocks.teagantotally.blinkercarbrowser.R;

/**
 * Created by tglenn on 9/19/17.
 */

public class TestDataStoreModule
          extends DataStoreModule {
    /**
     * @return The resource identifier for vehicle json file
     */
    @Override
    public int vehicleDataJsonFileResourceIdentifer() {
        return R.raw.test_vehicles;
    }
}
