package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import rocks.teagantotally.ibotta_challenge.BR;
import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Store;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.ConditionalItemBinder;

/**
 * Created by tglenn on 8/30/17.
 */

public class StoreVM
          extends BaseObservable {
    public static final ConditionalItemBinder<BaseObservable> itemBinder =
              new ConditionalItemBinder<BaseObservable>(BR.vm,
                                                        R.layout.item_store) {
                  @Override
                  public boolean canBind(BaseObservable item) {
                      return item instanceof StoreVM;
                  }
              };

    private String retailerName;
    private String latitude;
    private String longitude;

    public StoreVM(Retailer retailer,
                   Store store) {
        retailerName = retailer.name;
        latitude = String.valueOf(store.latitude);
        longitude = String.valueOf(store.longitude);
    }

    public void update(Retailer retailer) {
        setRetailerName(retailer.name);
    }

    public void update(Store store) {
        setLatitude(String.valueOf(store.latitude));
        setLongitude(String.valueOf(store.longitude));
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        if (TextUtils.equals(this.retailerName,
                             retailerName)) {
            return;
        }
        this.retailerName = retailerName;
        notifyChange();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (TextUtils.equals(this.latitude,
                             latitude)) {
            return;
        }
        this.latitude = latitude;
        notifyChange();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        if (TextUtils.equals(this.longitude,
                             longitude)) {
            return;
        }
        this.longitude = longitude;
        notifyChange();
    }
}
