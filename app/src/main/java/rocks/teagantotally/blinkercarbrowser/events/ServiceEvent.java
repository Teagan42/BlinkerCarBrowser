package rocks.teagantotally.blinkercarbrowser.events;

import android.app.Service;
import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by tglenn on 9/18/17.
 */

public class ServiceEvent<ServiceType extends Service>
          extends BaseEvent {
    public enum Status {
        STARTED,
        STOPPED
    }

    private Status status;
    private Class<ServiceType> serviceTypeClass;

    public ServiceEvent(@NonNull Status status,
                        @NonNull Class<ServiceType> serviceTypeClass) {
        Objects.requireNonNull(status,
                               "Service status cannot be null");
        Objects.requireNonNull(serviceTypeClass,
                               "Service class type cannot be null");
        this.status = status;
        this.serviceTypeClass = serviceTypeClass;
    }

    /**
     * @return The status of the service
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The service class
     */
    public Class<ServiceType> getServiceTypeClass() {
        return serviceTypeClass;
    }
}
