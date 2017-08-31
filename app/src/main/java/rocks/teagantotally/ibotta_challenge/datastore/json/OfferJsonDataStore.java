package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;

import com.google.common.collect.Sets;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.OfferDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;
import rocks.teagantotally.ibotta_challenge.datastore.models.OfferContainer;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class OfferJsonDataStore
          implements OfferDataStore {
    @Inject
    Context context;
    @Inject
    EventBus eventBus;

    @Inject
    public OfferJsonDataStore(Context context,
                              EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    private List<Offer> loadJson() throws
                                   IOException {
        OfferContainer container = JsonHelper.loadJson(context,
                                                       R.raw.offers,
                                                       OfferContainer.class);
        return Arrays.asList(container.offers);
    }

    @Override
    public List<Offer> getOffers(int offset,
                                 int limit) {
        List<Offer> offers;

        try {
            offers = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        return offers.subList(offset,
                              offset + limit);
    }

    @Override
    public List<Offer> getOffersByRetailerId(long retailerId) {
        Map<Long, List<Offer>> offerMap = getOffersByRetailerId(new long[]{retailerId});

        if (offerMap == null) {
            return null;
        }

        if (!offerMap.containsKey(retailerId)) {
            return new ArrayList<>();
        }

        return offerMap.get(retailerId);
    }

    @Override
    public Map<Long, List<Offer>> getOffersByRetailerId(long... retailerIds) {
        Map<Long, List<Offer>> result = new HashMap<>();
        Set<Long> retailerIdSet = new HashSet<>();
        List<Offer> offers;

        for (long retailerId : retailerIds) {
            retailerIdSet.add(retailerId);
        }

        try {
            offers = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        for (Offer offer : offers) {
            Set<Long> offerRetailerIdSet = new HashSet<>();

            for (long retailerId : offer.retailerIds) {
                offerRetailerIdSet.add(retailerId);
            }

            offerRetailerIdSet.retainAll(retailerIdSet);

            for (Long retailerId : offerRetailerIdSet) {
                if (!result.containsKey(retailerId)) {
                    result.put(retailerId,
                               new ArrayList<Offer>());
                }
                result.get(retailerId)
                      .add(offer);
            }
        }

        return result;
    }
}
