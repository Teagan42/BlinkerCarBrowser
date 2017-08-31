package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import rocks.teagantotally.ibotta_challenge.BR;
import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.events.BottomSheetEvent;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.ConditionalItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.fragments.OffersListFragment;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailerVM
          extends BaseObservable {
    public static final ConditionalItemBinder<BaseObservable> itemBinder =
              new ConditionalItemBinder<BaseObservable>(BR.vm,
                                                        R.layout.item_retailer) {
                  @Override
                  public boolean canBind(BaseObservable item) {
                      return item instanceof RetailerVM;
                  }
              };

    private Retailer retailer;
    private EventBus eventBus;
    private Context context;
    private View.OnClickListener locationsClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      StoreListVM bottmSheetVM = new StoreListVM(retailer,
                                                                 context,
                                                                 eventBus);
                      new BottomSheetEvent(R.layout.fragment_retailer_locations,
                                           BR.vm,
                                           bottmSheetVM).post();
                  }
              };
    private View.OnClickListener offersClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      OffersListFragment.navigateTo(retailer);
                  }
              };

    public RetailerVM(Retailer retailer,
                      Context context,
                      EventBus eventBus) {
        this.retailer = retailer;
        this.eventBus = eventBus;
        this.context = context;
    }

    public String getName() {
        return retailer.name;
    }

    public View.OnClickListener getLocationsClickListener() {
        return locationsClickListener;
    }

    public View.OnClickListener getOffersClickListener() {
        return offersClickListener;
    }

    public String getIconUrl() {
        return retailer.iconUrl;
    }
}
