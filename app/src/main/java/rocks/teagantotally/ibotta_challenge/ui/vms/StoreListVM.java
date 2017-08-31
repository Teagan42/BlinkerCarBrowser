package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.events.CancelEvent;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveStoresEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.StoresRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;

/**
 * Created by tglenn on 8/30/17.
 */

public class StoreListVM
          extends BaseListVM<BaseObservable> {
    private static final int LIMIT = 100;

    private List<StoreVM> stores = new ArrayList<>();
    private Retailer retailer;
    private ProgressDialogNotificationEvent progressDialogNotificationEvent;

    public StoreListVM(Retailer retailer,
                       Context context,
                       EventBus eventBus) {
        eventBus.register(this);
        this.retailer = retailer;
        setItemBinder(new CompositeItemBinder<BaseObservable>(
                  StoreVM.itemBinder
        ));
        setLayoutManager(new LinearLayoutManager(context));

        progressDialogNotificationEvent = new ProgressDialogNotificationEvent("Loading stores");
        new RetrieveStoresEvent(retailer.id).post();
        progressDialogNotificationEvent.post();
    }

    public List<StoreVM> getStores() {
        return stores;
    }

    public void setStores(List<StoreVM> stores) {
        if (Objects.equals(this.stores,
                           stores)) {
            return;
        }
        this.stores = stores;
        notifyChange();
    }

    public String getRetailerName() {
        return retailer.name;
    }

    public int getListVisibility() {
        return stores.isEmpty()
               ? View.GONE
               : View.VISIBLE;
    }

    public int getEmptyStateVisibility() {
        return stores.isEmpty()
               ? View.VISIBLE
               : View.GONE;
    }

    private void dismissProgressDialog() {
        if (progressDialogNotificationEvent != null) {
            new CancelEvent(progressDialogNotificationEvent).post();
            progressDialogNotificationEvent = null;
        }
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(StoresRetrievedEvent event) {
        dismissProgressDialog();
        if (stores == null) {
            stores = new ArrayList<>();
        }
        for (Store store : event.getStores()) {
            stores.add(new StoreVM(retailer,
                                   store));
        }

        notifyChange();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ErrorEvent event) {
        dismissProgressDialog();
        new SnackbarNotificationEvent(event.getThrowable()
                                           .getLocalizedMessage()).post();
    }
}
