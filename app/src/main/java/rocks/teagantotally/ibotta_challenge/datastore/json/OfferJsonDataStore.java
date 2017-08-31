package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.lang.reflect.Array;
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
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;

/**
 * Created by tglenn on 8/30/17.
 */

public class OfferJsonDataStore
          implements OfferDataStore {
    @Inject
    Context context;

    @Inject
    public OfferJsonDataStore(Context context) {
        this.context = context;
    }

    private List<Offer> loadJson() throws
                                   IOException {
        Offer[] offers = JsonHelper.loadJson(context,
                                             R.raw.stores,
                                             Offer[].class);
        return Arrays.asList(offers);
    }

    @Override
    public List<Offer> getOffers(int offset,
                                 int limit) {
        List<Offer> offers;

        try {
            offers = loadJson();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return offers.subList(offset,
                              offset + limit);
    }

    @Override
    public List<Offer> getOffersByRetailerId(long retailerId) {
        Map<Long, List<Offer>> offerMap = getOffersByRetailerId(new long[]{retailerId});

        if (!offerMap.containsKey(retailerId)) {
            return new ArrayList<>();
        }

        return offerMap.get(retailerId);
    }

    @Override
    public Map<Long, List<Offer>> getOffersByRetailerId(long... retailerIds) {
        Map<Long, List<Offer>> result = new HashMap<>();
        Set retailerIdSet = Sets.newHashSet(retailerIds);
        List<Offer> offers;

        try {
            offers = loadJson();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (Offer offer : offers) {
            Set offerRetailerIdSet = Sets.newHashSet(offer.retailerIds);
            offerRetailerIdSet.retainAll(retailerIdSet);

            Iterator iterator = offerRetailerIdSet.iterator();
            while (iterator.hasNext()) {
                Long retailerId = (Long) iterator.next();
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