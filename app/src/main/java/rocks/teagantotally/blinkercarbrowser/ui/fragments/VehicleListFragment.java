package rocks.teagantotally.blinkercarbrowser.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import rocks.teagantotally.blinkercarbrowser.BR;
import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.databinding.FragmentVehicleListBinding;
import rocks.teagantotally.blinkercarbrowser.datastore.VehicleDataStore;
import rocks.teagantotally.blinkercarbrowser.di.Injector;
import rocks.teagantotally.blinkercarbrowser.events.ErrorEvent;
import rocks.teagantotally.blinkercarbrowser.ui.vms.VehicleListVM;

/**
 * Created by tglenn on 8/30/17.
 */

public class VehicleListFragment
          extends BaseFragment {
    public static final String ROUTE = "blinker://vehicles";

    private FragmentVehicleListBinding binding;

    @Inject
    VehicleDataStore vehicleDataStore;

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

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_vehicle_list,
                                          container,
                                          false);
        VehicleListVM vm = Injector.getViewModels()
                                   .getVehicleList()
                                   .setSearchOptions(
                                             Injector.getViewModels()
                                                     .getSearchOptions()
                                   );
        binding.setVm(vm);

        getBaseActivity().setupDrawer(R.layout.drawer_search_options,
                                      BR.vm,
                                      vm.getSearchOptionsVM());

        return binding.getRoot();
    }

    @Override
    public boolean shouldEnableKeyboardLayoutListener() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseActivity().setTitle(getString(R.string.vehicles));
    }

    /**
     * Event subscription for data store error events
     *
     * @param event Event data
     */
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
