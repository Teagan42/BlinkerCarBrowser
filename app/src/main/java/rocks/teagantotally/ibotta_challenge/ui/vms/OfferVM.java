package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.databinding.BaseObservable;
import android.icu.text.NumberFormat;
import android.view.View;

import rocks.teagantotally.ibotta_challenge.BR;
import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.ConditionalItemBinder;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferVM
          extends BaseObservable {
    public static final ConditionalItemBinder<BaseObservable> itemBinder =
              new ConditionalItemBinder<BaseObservable>(BR.vm,
                                                        R.layout.item_offer) {
                  @Override
                  public boolean canBind(BaseObservable item) {
                      return item instanceof OfferVM;
                  }
              };

    private Retailer retailer;
    private Offer offer;

    public OfferVM(Retailer retailer,
                   Offer offer) {
        this.retailer = retailer;
        this.offer = offer;
    }

    /**
     * @return Whether the offer is valid
     */
    public boolean isValid() {
        return offer.active && !offer.expired;
    }

    /**
     * @return The name of the offer
     */
    public String getName() {
        return offer.name;
    }

    /**
     * @return The description of the offer
     */
    public String getDescription() {
        return offer.description;
    }

    /**
     * @return The visibiliity of the inactive reason view
     */
    public int getInactiveReasonVisibility() {
        return isValid()
               ? View.GONE
               : View.VISIBLE;
    }

    /**
     * @return The reason for being inactive
     */
    public String getInactiveReason() {
        if (offer.expired) {
            return "EXPIRED";
        } else if (!offer.active) {
            return "INACTIVE";
        }

        return null;
    }

    /**
     * @return The offer amount
     */
    public String getAmount() {
        return NumberFormat.getCurrencyInstance()
                           .format(offer.amount);
    }
}
