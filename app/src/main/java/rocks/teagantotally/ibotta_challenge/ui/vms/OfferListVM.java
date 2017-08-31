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

import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.CancelEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.OffersRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveOffersEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferListVM
          extends BaseListVM<BaseObservable> {

    private Retailer retailer;
    private Context context;
    private List<OfferVM> offers = new ArrayList<>();
    private ProgressDialogNotificationEvent progressDialogNotificationEvent;
    private RetrieveOffersEvent retrieveOffersEvent;

    public OfferListVM(Retailer retailer,
                       Context context,
                       EventBus eventBus) {
        this.retailer = retailer;
        this.context = context;

        eventBus.register(this);

        setLayoutManager(new LinearLayoutManager(context));
        setItemBinder(new CompositeItemBinder<BaseObservable>(
                  OfferVM.itemBinder
        ));

        progressDialogNotificationEvent = new ProgressDialogNotificationEvent("Loading offers");
        retrieveOffersEvent = new RetrieveOffersEvent(retailer.id);
        progressDialogNotificationEvent.post();
        retrieveOffersEvent.post();
    }

    private void dismissProgressDialog() {
        if (progressDialogNotificationEvent == null) {
            return;
        }

        new CancelEvent(progressDialogNotificationEvent).post();
        progressDialogNotificationEvent = null;
    }

    public String retailerName() {
        return retailer.name;
    }

    public List<OfferVM> getOffers() {
        return offers;
    }

    public int getEmptyStateVisibility() {
        return offers.isEmpty()
               ? View.VISIBLE
               : View.GONE;
    }

    public int getListVisibility() {
        return offers.isEmpty()
               ? View.GONE
               : View.VISIBLE;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(OffersRetrievedEvent event) {
        dismissProgressDialog();

        for (Offer offer : event.getOffers()) {
            offers.add(new OfferVM(retailer,
                                   offer));
        }

        notifyChange();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(CancelEvent event) {
        dismissProgressDialog();
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return null;
    }
}
