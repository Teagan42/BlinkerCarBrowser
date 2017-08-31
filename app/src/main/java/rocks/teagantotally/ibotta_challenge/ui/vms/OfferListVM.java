package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.CancelEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.OffersRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveOffersEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.handlers.ClickHandler;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferListVM
          extends BaseListVM<BaseObservable> {

    private Retailer retailer;
    private Context context;
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
        setItemClickHandler(new ClickHandler<BaseObservable>() {
            @Override
            public void onClick(BaseObservable item) {
                if (item instanceof OfferVM) {
                    OfferVM vm = (OfferVM) item;
                    vm.setExpanded(!vm.isExpanded());
                }
            }
        });

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

    /**
     * @return The retailer's name
     */
    public String retailerName() {
        return retailer.name;
    }

    /**
     * @return Visibility of the empty state view
     */
    public int getEmptyStateVisibility() {
        return getItems().isEmpty()
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return Visibility of the list
     */
    public int getListVisibility() {
        return getItems().isEmpty()
               ? View.GONE
               : View.VISIBLE;
    }

    /**
     * Event subscription for offers retrieved event
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(OffersRetrievedEvent event) {
        dismissProgressDialog();

        for (Offer offer : event.getOffers()) {
            addItem(new OfferVM(offer,
                                context));
        }

    }

    /**
     * Event subscription for when an event is cancelled
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(CancelEvent event) {
        if (!Objects.equals(event.getEventToCancel(),
                            progressDialogNotificationEvent)) {
            return;
        }
        dismissProgressDialog();
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return null;
    }
}
