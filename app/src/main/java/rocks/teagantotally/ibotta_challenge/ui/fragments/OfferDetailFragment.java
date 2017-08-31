package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.databinding.FragmentOfferDetailBinding;
import rocks.teagantotally.ibotta_challenge.datastore.OfferDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.di.Injector;
import rocks.teagantotally.ibotta_challenge.events.BaseEvent;
import rocks.teagantotally.ibotta_challenge.events.CancelEvent;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;
import rocks.teagantotally.ibotta_challenge.events.NavigationEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.OfferRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveOfferEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.ProgressDialogNotificationEvent;
import rocks.teagantotally.ibotta_challenge.events.notifications.SnackbarNotificationEvent;
import rocks.teagantotally.ibotta_challenge.ui.vms.OfferVM;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferDetailFragment
          extends BaseFragment {
    public static final String ARG_OFFER_ID = "offerId";
    public static final String ROUTE = "ibotta://offers/:" + ARG_OFFER_ID;

    public static void navigateTo(Offer offer) {
        String route = ROUTE.replace(":" + ARG_OFFER_ID,
                                     String.valueOf(offer.id));
        new NavigationEvent(route).post();
    }

    public class SetTitleEvent
              extends BaseEvent {
    }

    private FragmentOfferDetailBinding binding;
    private RetrieveOfferEvent retrieveOfferEvent;
    private ProgressDialogNotificationEvent progressDialogNotificationEvent;
    private String title;

    @Inject
    OfferDataStore offerDataStore;

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
        if (args == null || !args.containsKey(ARG_OFFER_ID)) {
            throw new IllegalStateException(
                      String.format("Required arguments are missing: %s",
                                    ARG_OFFER_ID));
        }

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_offer_detail,
                                          container,
                                          false);

        final String offerId = String.valueOf(args.get(ARG_OFFER_ID));
        retrieveOfferEvent =
                  new RetrieveOfferEvent(Long.valueOf(offerId));
        progressDialogNotificationEvent =
                  new ProgressDialogNotificationEvent("Loading retailer");

        retrieveOfferEvent.post();
        progressDialogNotificationEvent.post();

        return binding.getRoot();
    }

    private void setTitle() {
        getBaseActivity().setTitle(title);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    private void dismissProgressDialog() {
        if (progressDialogNotificationEvent == null) {
            return;
        }

        new CancelEvent(progressDialogNotificationEvent).post();
        progressDialogNotificationEvent = null;
    }

    /**
     * Event subscription for offer retrieval request event
     *
     * @param event Event data
     * @// TODO: 8/31/17 This should be moved to a service
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveOfferEvent event) {
        Offer offer = offerDataStore.getOfferById(event.getOfferId());

        if (offer == null) {
            // TODO : Handle not found vs error
            return;
        }

        new OfferRetrievedEvent(event,
                                offer).post();
    }

    /**
     * Event subscription for when an offer is retrieved
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(OfferRetrievedEvent event) {
        if (!Objects.equals(this.retrieveOfferEvent,
                            event.getRetrieveEvent())) {
            return;
        }

        dismissProgressDialog();

        binding.setVm(new OfferVM(event.getOffer(),
                                  getContext()));
        title = event.getOffer().name;
        new SetTitleEvent().post();
    }

    /**
     * Event subscription for data store error event
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
                                           .getLocalizedMessage()).post();
    }

    /**
     * Event subscription for setting title
     *
     * @param event Event data
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetTitleEvent event) {
        setTitle();
    }
}
