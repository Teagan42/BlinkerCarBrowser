package rocks.teagantotally.ibotta_challenge.events;

import rocks.teagantotally.ibotta_challenge.di.Injector;

/**
 * Created by tglenn on 8/30/17.
 */

public class BaseEvent {
    /**
     * Post this event to the event bus
     */
    public void post() {
        Injector.get()
                .eventBus()
                .post(this);
    }
}
