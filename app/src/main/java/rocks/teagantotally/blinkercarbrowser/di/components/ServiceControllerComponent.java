package rocks.teagantotally.blinkercarbrowser.di.components;

import dagger.Subcomponent;
import rocks.teagantotally.blinkercarbrowser.di.modules.ServiceControllerModule;
import rocks.teagantotally.blinkercarbrowser.di.scopes.ServiceScope;
import rocks.teagantotally.blinkercarbrowser.services.VehicleService;

/**
 * Created by tglenn on 9/14/17.
 */

@ServiceScope
@Subcomponent(modules = {ServiceControllerModule.class})
public interface ServiceControllerComponent {
    /**
     * @return The vehicle service controller
     */
    VehicleService.Controller getVehicle();
}
