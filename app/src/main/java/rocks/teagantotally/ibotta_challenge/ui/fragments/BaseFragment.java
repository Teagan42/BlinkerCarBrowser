package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

import rocks.teagantotally.ibotta_challenge.di.Injector;
import rocks.teagantotally.ibotta_challenge.events.NavigationEvent;
import rocks.teagantotally.ibotta_challenge.ui.BaseActivity;
import rocks.teagantotally.ibotta_challenge.ui.ScreenView;
import rocks.teagantotally.ibotta_challenge.ui.utils.KeyboardUtil;
import rocks.teagantotally.ibotta_challenge.ui.vms.ToolbarVM;

/**
 * Created by tglenn on 8/30/17.
 */

public abstract class BaseFragment
          extends Fragment
          implements ScreenView {
    private static final String TAG = "BaseFragment";

    protected static final LifecycleEvent DEFAULT_UNREGISTER_LIFECYCLE_EVENT = LifecycleEvent.ONSTOP;

    protected EventBus eventBus;
    private boolean isPaused;

    protected enum LifecycleEvent {
        ONSTOP,
        ONDESTROY
    }

    /**
     * @return The view model for the toolbar
     */
    protected ToolbarVM getToolbarViewModel() {
        return null;
    }

    protected boolean isPaused() {
        return isPaused;
    }

    protected final void applyToolbars() {
        if (!isTop()) {
            return;
        }
        BaseActivity activity = getBaseActivity();
        if (activity != null) {
            activity.setToolbar(getToolbarViewModel());
        }
    }

    protected void goBack() {
        KeyboardUtil.hideKeyboard(getActivity());
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyToolbars();
        isPaused = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus = Injector.get()
                           .eventBus();
        try {
            if (eventBus != null && !eventBus.isRegistered(this)) {
                eventBus.register(this);
            }
        } catch (EventBusException e) {
            Log.i(TAG,
                  "onCreate: No subscription methods");
        } catch (NullPointerException e) {
            Log.e(TAG,
                  "onCreate: Bus was not injected",
                  e);
        }

        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,
                            savedInstanceState);

        applyToolbars();
    }

    protected LifecycleEvent getUnregisterLifecycleEvent() {
        return DEFAULT_UNREGISTER_LIFECYCLE_EVENT;
    }

    @Override
    public void onStop() {
        if (getUnregisterLifecycleEvent() == LifecycleEvent.ONSTOP) {
            unregisterEventBus();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (getUnregisterLifecycleEvent() == LifecycleEvent.ONDESTROY) {
            unregisterEventBus();
        }
        super.onDestroy();
    }


    private void unregisterEventBus() {
        if (eventBus != null && eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    protected void navigateTo(String to) {
        new NavigationEvent(this,
                            to);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public boolean isTop() {
        FragmentManager fragmentManager;
        String tag;
        if (getActivity() != null) {
            fragmentManager = getActivity().getSupportFragmentManager();
            tag = getTag();
        } else if (getParentFragment() != null) {
            fragmentManager = getParentFragment().getActivity()
                                                 .getSupportFragmentManager();
            tag = getParentFragment().getTag();
        } else {
            return false;
        }

        boolean hasFragment = fragmentManager.getBackStackEntryCount() > 0;
        return hasFragment
               && fragmentManager.getBackStackEntryAt(
                  fragmentManager.getBackStackEntryCount() - 1)
                                 .getName()
                                 .equals(tag);
    }
}
