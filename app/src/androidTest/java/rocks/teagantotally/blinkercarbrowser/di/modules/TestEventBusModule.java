package rocks.teagantotally.blinkercarbrowser.di.modules;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tglenn on 9/19/17.
 */

public class TestEventBusModule
          extends EventBusModule {
    private EventBus eventBus;

    public TestEventBusModule(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Provide the event bus to use in the application
     *
     * @return
     */
    @Override
    public EventBus eventBus() {
        return eventBus;
    }
}
