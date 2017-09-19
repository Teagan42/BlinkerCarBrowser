package rocks.teagantotally.blinkercarbrowser.ui.vms;

import android.databinding.BaseObservable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

import rocks.teagantotally.blinkercarbrowser.di.Injector;

/**
 * Created by tglenn on 9/14/17.
 */

public class BaseVM
          extends BaseObservable {
    private static final String TAG = "BaseVM";

    protected EventBus eventBus;
    private boolean shouldNotify;

    /**
     * Create a new view model instance
     *
     * @param eventBus Event bus to register with
     */
    protected BaseVM(EventBus eventBus) {
        this.eventBus = eventBus;

        try {
            eventBus.register(this);
        } catch (EventBusException e) {
            Log.w(TAG,
                  "BaseVM: No subscriber methods");
        }

        // Reset view model component lifecycle
        Injector.clearViewModels();
    }

    /**
     * Unregisters this view model from the event bus
     */
    public void unsubscribe() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    /**
     * Set whether to notify listeners
     *
     * @param shouldNotify Whether to notify listeners
     */
    public void setShouldNotify(boolean shouldNotify) {
        this.shouldNotify = shouldNotify;
        if (shouldNotify) {
            notifyChange();
        }
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Override
    public void notifyChange() {
        if (!shouldNotify) {
            return;
        }
        super.notifyChange();
    }
}
