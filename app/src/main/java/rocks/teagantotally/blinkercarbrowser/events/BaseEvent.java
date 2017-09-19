package rocks.teagantotally.blinkercarbrowser.events;

import rocks.teagantotally.blinkercarbrowser.di.Injector;

/**
 * Created by tglenn on 8/30/17.
 */

public class BaseEvent {
    /**
     * Post this event to the event bus
     */
    public BaseEvent post() {
        Injector.get()
                .eventBus()
                .post(this);

        return this;
    }
}
