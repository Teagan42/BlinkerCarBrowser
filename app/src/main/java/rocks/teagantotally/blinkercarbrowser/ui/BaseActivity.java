package rocks.teagantotally.blinkercarbrowser.ui;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import rocks.teagantotally.blinkercarbrowser.BuildConfig;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.databinding.ActivityContainerBinding;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.events.BaseEvent;
import rocks.teagantotally.blinkercarbrowser.events.CancelEvent;
import rocks.teagantotally.blinkercarbrowser.events.DialogEvent;
import rocks.teagantotally.blinkercarbrowser.events.NavigationEvent;
import rocks.teagantotally.blinkercarbrowser.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.blinkercarbrowser.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.blinkercarbrowser.routing.Router;
import rocks.teagantotally.blinkercarbrowser.ui.fragments.BaseFragment;
import rocks.teagantotally.blinkercarbrowser.ui.fragments.BindableDialogFragment;
import rocks.teagantotally.blinkercarbrowser.ui.fragments.VehicleListFragment;
import rocks.teagantotally.blinkercarbrowser.ui.utils.KeyboardUtil;
import rocks.teagantotally.blinkercarbrowser.ui.vms.BaseVM;

/**
 * Created by tglenn on 8/30/17.
 */

public abstract class BaseActivity
          extends AppCompatActivity {
    private static final Class defaultFragment = VehicleListFragment.class;

    /**
     * @return The top most activity
     */
    public static BaseActivity getTopActivity() {
        return topActivity;
    }

    private static final String TAG = "BaseActivity";
    private static BaseActivity topActivity;

    protected EventBus eventBus;
    protected KeyboardUtil keyboardUtil;
    private boolean isRunning;
    private ActionBarDrawerToggle drawerToggle;
    private ActivityContainerBinding binding;
    private Map<ProgressDialogNotificationEvent, ProgressDialog> progressDialogMap = new HashMap<>();

    protected String getPermissionReason() {
        return null;
    }

    @IntRange(from = 0)
    protected int getPermissionRequestCode() {
        return 0;
    }

    protected String[] getOptionalPermissions() {
        return new String[0];
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this,
                                                 R.layout.activity_container);

        eventBus = Injector.get()
                           .eventBus();
        try {
            eventBus.register(this);
        } catch (EventBusException e) {
            Log.e(TAG,
                  "onCreate: Unable to register",
                  e);
        } catch (NullPointerException e) {
            Log.e(TAG,
                  "onCreate: Bus was not injected",
                  e);
        }

        keyboardUtil = new KeyboardUtil(this,
                                        binding.coordinatorLayout);

        Bundle args = getIntent().getExtras();
        if (args == null) {
            args = new Bundle();
            args.putSerializable(Router.FRAGMENT,
                                 defaultFragment);
        }
        if (args.containsKey(Router.FRAGMENT) && args.get(Router.FRAGMENT) != null) {
            String tag = (String) args.get(Router.KEY);
            Class fragment = (Class) args.get(Router.FRAGMENT);
            try {
                BaseFragment baseFragment = (BaseFragment) fragment.newInstance();
                baseFragment.setArguments(args.getBundle(Router.EXTRAS));
                FragmentTransaction transaction =
                          getSupportFragmentManager().beginTransaction()
                                                     .replace(R.id.container,
                                                              baseFragment,
                                                              tag);
                if (args.getBoolean(Router.BACKSTACK,
                                    true)) {
                    transaction.addToBackStack(tag);
                }
                transaction.commit();
                if (baseFragment.shouldEnableKeyboardLayoutListener()) {
                    keyboardUtil.enable();
                } else {
                    keyboardUtil.disable();
                }
            } catch (Exception e) {
                Log.e(TAG,
                      "onCreate: Error adding fragment",
                      e
                );
                new SnackbarNotificationEvent("Error adding fragment",
                                              SnackbarNotificationEvent.Length.SHORT);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        syncDrawer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle == null) {
            return;
        }
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle == null) {
            return super.onOptionsItemSelected(item);
        }

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the drawer
     *
     * @param drawerLayoutResourceId Resource of the layout to inflate
     * @param bindingVariableId      The binding variable id
     * @param drawerViewModel        The view model for the drawer
     */
    public void setupDrawer(@LayoutRes int drawerLayoutResourceId,
                            final int bindingVariableId,
                            final BaseVM drawerViewModel) {
        drawerToggle = new ActionBarDrawerToggle(this,
                                                 binding.drawerLayout,
                                                 R.string.open_drawer,
                                                 R.string.close_drawer);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        syncDrawer();

        binding.drawerView.getViewStub()
                          .setLayoutResource(drawerLayoutResourceId);
        binding.drawerView.setOnInflateListener(
                  new ViewStub.OnInflateListener() {
                      @Override
                      public void onInflate(ViewStub viewStub,
                                            View view) {
                          ViewDataBinding binding = DataBindingUtil.bind(view);
                          binding.setVariable(bindingVariableId,
                                              drawerViewModel);
                      }
                  }
        );
        binding.drawerView.getViewStub()
                          .inflate();
    }

    public void tearDownDrawer() {
        binding.drawerView.setOnInflateListener(null);
        binding.drawerLayout.removeDrawerListener(drawerToggle);
        drawerToggle = null;
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void syncDrawer() {
        if (drawerToggle == null) {
            return;
        }

        drawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        topActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (eventBus != null && !eventBus.isRegistered(this)) {
            try {
                eventBus.register(this);
            } catch (EventBusException e) {
                Log.i(TAG,
                      "onStart: no subscribed methods");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onDestroy() {
        if (eventBus != null && eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(DialogEvent event) {
        Fragment previousDialog =
                  getSupportFragmentManager().findFragmentByTag(BindableDialogFragment.TAG);
        FragmentTransaction transaction =
                  getSupportFragmentManager().beginTransaction();
        if (previousDialog != null) {
            transaction.remove(previousDialog);
        }

        transaction.addToBackStack(null);
        BindableDialogFragment newDialog =
                  new BindableDialogFragment(event.getLayoutResourceId(),
                                             event.getBindingVariableId(),
                                             event.getViewModel());
        newDialog.show(getSupportFragmentManager(),
                       BindableDialogFragment.TAG);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProgressDialogNotificationEvent event) {
        if (progressDialogMap.containsKey(event)) {
            return;
        }

        progressDialogMap.put(event,
                              ProgressDialog.show(this,
                                                  event.getTitle(),
                                                  event.getMessage(),
                                                  true,
                                                  false));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancelEvent event) {
        BaseEvent eventToCancel = event.getEventToCancel();
        if (!(eventToCancel instanceof ProgressDialogNotificationEvent) ||
            !progressDialogMap.containsKey(eventToCancel)) {
            return;
        }
        progressDialogMap.remove(eventToCancel)
                         .dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SnackbarNotificationEvent event) {
        CoordinatorLayout coordinatorLayout = binding.coordinatorLayout;
        if (coordinatorLayout != null) {
            int length = event.getLength() == SnackbarNotificationEvent.Length.LONG
                         ? Snackbar.LENGTH_LONG
                         : Snackbar.LENGTH_SHORT;
            Snackbar snackbar = Snackbar.make(coordinatorLayout,
                                              event.getMessage(),
                                              length);

            if (event.getActionText() != null && event.getActionClickListener() != null) {
                snackbar.setAction(event.getActionText()
                                        .toUpperCase(),
                                   event.getActionClickListener());
            }
            snackbar.show();
        } else {
            int length = event.getLength() == SnackbarNotificationEvent.Length.LONG
                         ? Toast.LENGTH_LONG
                         : Toast.LENGTH_SHORT;
            Toast.makeText(this,
                           event.getMessage(),
                           length)
                 .show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NavigationEvent event) {
        if (isRunning) {
            Router.navigateTo(this,
                              event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SubscriberExceptionEvent event) {
        Log.e(TAG,
              "onEvent: Error dispatching event",
              event.throwable);
        if (BuildConfig.DEBUG) {
            new SnackbarNotificationEvent(event.throwable.getMessage(),
                                          SnackbarNotificationEvent.Length.SHORT);
        }
    }

    protected void navigateTo(String to) {
        new NavigationEvent(to);
    }

    protected void navigateTo(Uri to) {
        navigateTo(to.toString());
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0) {
            super.onBackPressed();
        }

        Fragment topMostFragment =
                  getSupportFragmentManager().findFragmentByTag(
                            getSupportFragmentManager().getBackStackEntryAt(backStackCount - 1)
                                                       .getName());
        if (backStackCount == 1) {
            finish();
        }

        super.onBackPressed();
    }
}
