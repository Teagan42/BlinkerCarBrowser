package rocks.teagantotally.ibotta_challenge.ui.vms;

import android.content.Context;
import android.databinding.BaseObservable;
import android.icu.text.NumberFormat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.Arrays;

import rocks.teagantotally.ibotta_challenge.BR;
import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.CompositeItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.binding.recyclerview.ConditionalItemBinder;
import rocks.teagantotally.ibotta_challenge.ui.fragments.OfferDetailFragment;
import rocks.teagantotally.ibotta_challenge.util.GsonUtil;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferVM
          extends BaseListVM<Offer.Category> {
    public static final ConditionalItemBinder<BaseObservable> itemBinder =
              new ConditionalItemBinder<BaseObservable>(BR.vm,
                                                        R.layout.item_offer) {
                  @Override
                  public boolean canBind(BaseObservable item) {
                      return item instanceof OfferVM;
                  }
              };

    private Offer offer;
    private boolean expanded;
    private View.OnClickListener moreDetailsClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      OfferDetailFragment.navigateTo(offer);
                  }
              };

    public OfferVM(Offer offer,
                   Context context) {
        this.offer = offer;
        setItems(Arrays.asList(offer.categories));
        setLayoutManager(new GridLayoutManager(context,
                                               2));
        setItemBinder(new CompositeItemBinder<Offer.Category>(
                  new ConditionalItemBinder<Offer.Category>(BR.vm,
                                                            R.layout.item_category) {
                      @Override
                      public boolean canBind(Offer.Category item) {
                          return true;
                      }
                  }
        ));
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

    /**
     * @return The url to the icon
     */
    public String iconUrl() {
        return offer.url;
    }

    /**
     * @return Whether the view is expanded
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Sets whether the view is expanded
     *
     * @param expanded Expanded
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        notifyChange();
    }

    /**
     * @return The visibility of more details
     */
    public int getMoreDetailsVisibility() {
        return isExpanded()
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The start date of the offer
     */
    public String getStartDate() {
        return GsonUtil.getDateFormatter()
                       .format(offer.launchedAt);
    }

    /**
     * @return The expiration date of the offer
     */
    public String getExpirationDate() {
        return GsonUtil.getDateFormatter()
                       .format(offer.expiration);
    }

    /**
     * @return Click listener for more details button
     */
    public View.OnClickListener getMoreDetailsClickListener() {
        return moreDetailsClickListener;
    }

    /**
     * @return The image url
     */
    public String getImageUrl() {
        return offer.largeUrl;
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return null;
    }
}
