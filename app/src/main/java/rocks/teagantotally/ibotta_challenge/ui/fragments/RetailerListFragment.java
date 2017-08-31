package rocks.teagantotally.ibotta_challenge.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.databinding.FragmentRetailerListBinding;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.StoreDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.di.Injector;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetailersRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveRetailersEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.RetrieveStoresEvent;
import rocks.teagantotally.ibotta_challenge.events.datastore.StoresRetrievedEvent;
import rocks.teagantotally.ibotta_challenge.ui.vms.RetailerListVM;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailerListFragment
          extends BaseFragment {
    public static final String ROUTE = "ibotta://retailers";

    private FragmentRetailerListBinding binding;

    @Inject
    RetailerDataStore retailerDataStore;
    @Inject
    StoreDataStore storeDataStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (binding != null) {
            return binding.getRoot();
        }

        getBaseActivity().setTitle("Your Retailers");

        Injector.get()
                .inject(this);

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_retailer_list,
                                          container,
                                          false);

        binding.setVm(new RetailerListVM(eventBus,
                                         getContext()));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseActivity().setTitle("Your Retailers");
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveRetailersEvent event) {
        List<Retailer> retailers =
                  retailerDataStore.getRetailers(event.getOffset(),
                                                 event.getLimit());

        if (retailers != null) {
            new RetailersRetrievedEvent(event,
                                        retailers).post();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(RetrieveStoresEvent event) {
        List<Store> stores =
                  storeDataStore.getStoresByRetailer(event.getRetailerId());

        if (stores != null) {
            new StoresRetrievedEvent(event,
                                     stores).post();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ErrorEvent event) {
        if (!isTop()) {
            return;
        }
        Toast.makeText(getContext(),
                       event.getThrowable()
                            .getLocalizedMessage(),
                       Toast.LENGTH_SHORT)
             .show();
        getActivity().finish();
    }
}
