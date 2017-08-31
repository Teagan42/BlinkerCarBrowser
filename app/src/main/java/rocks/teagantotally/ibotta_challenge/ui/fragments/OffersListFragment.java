package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.databinding.FragmentRetailerOffersBinding;
import rocks.teagantotally.ibotta_challenge.datastore.OfferDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.di.Injector;
import rocks.teagantotally.ibotta_challenge.events.CancelEvent;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;
import rocks.teagantotally.ibotta_challenge.events.NavigationEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.OffersRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetailerRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveOffersEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveRetailerEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.ibotta_challenge.ui.vms.OfferListVM;

/**
 * Created by tglenn on 8/31/17.
 */

public class OffersListFragment
          extends BaseFragment {
    public static final String ARG_RETAILER_ID = "retailerId";
    public static final String ROUTE = "ibotta://offers/:" + ARG_RETAILER_ID;

    public static void navigateTo(Retailer retailer) {
        String route = ROUTE.replace(":" + ARG_RETAILER_ID,
                                     String.valueOf(retailer.id));
        new NavigationEvent(route).post();
    }

    private FragmentRetailerOffersBinding binding;
    private RetrieveRetailerEvent retrieveRetailerEvent;
    private ProgressDialogNotificationEvent progressDialogNotificationEvent;
    private String title = "Offers";

    @Inject
    OfferDataStore offerDataStore;
    @Inject
    RetailerDataStore retailerDataStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (binding != null) {
            return binding.getRoot();
        }

        Injector.get()
                .inject(this);

        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_RETAILER_ID)) {
            throw new IllegalStateException(
                      String.format("Required arguments are missing: %s",
                                    ARG_RETAILER_ID));
        }

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_retailer_offers,
                                          container,
                                          false);

        final String retailerId = String.valueOf(args.get(ARG_RETAILER_ID));
        retrieveRetailerEvent =
                  new RetrieveRetailerEvent(Long.valueOf(retailerId));
        progressDialogNotificationEvent =
                  new ProgressDialogNotificationEvent("Loading retailer");

        retrieveRetailerEvent.post();
        progressDialogNotificationEvent.post();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    private void setTitle() {
        getBaseActivity().setTitle(title);
    }

    private void dismissProgressDialog() {
        if (progressDialogNotificationEvent == null) {
            return;
        }

        new CancelEvent(progressDialogNotificationEvent).post();
        progressDialogNotificationEvent = null;
    }

    /**
     * Event subscription for retailer retrieval event
     *
     * @param event Event data
     * @// TODO: 8/31/17 This should be moved to a service
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveRetailerEvent event) {
        Retailer retailer = retailerDataStore.getRetailerById(event.getRetailerId());

        if (retailer != null) {
            new RetailerRetrievedEvent(event,
                                       retailer).post();
        }
    }

    /**
     * Event subscription to handle when a retailer has been retrieved
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetailerRetrievedEvent event) {
        dismissProgressDialog();

        if (!Objects.equals(retrieveRetailerEvent,
                            event.getRetrieveEvent())) {
            return;
        }

        binding.setVm(new OfferListVM(event.getRetailer(),
                                      getContext(),
                                      eventBus));
        title = "Offers for " + event.getRetailer().name;
        setTitle();
    }

    /**
     * Event subscription to for offer retrieval event
     *
     * @param event Event data
     * @// TODO: 8/31/17 This should be moved to a service
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveOffersEvent event) {
        List<Offer> offers = offerDataStore.getOffersByRetailerId(event.getRetailerId());

        if (offers != null) {
            new OffersRetrievedEvent(event,
                                     offers).post();
        }
    }

    /**
     * Event subscription for data store errors
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ErrorEvent event) {
        if (!isTop()) {
            return;
        }

        dismissProgressDialog();
        new SnackbarNotificationEvent(event.getThrowable()
                                           .getMessage()).post();
        goBack();
    }
}
