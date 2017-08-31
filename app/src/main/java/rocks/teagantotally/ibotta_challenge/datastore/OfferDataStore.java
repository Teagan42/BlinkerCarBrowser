package rocks.teagantotally.ibotta_challenge.datastore;

import android.util.SparseArray;

import java.util.List;
import java.util.Map;

import rocks.teagantotally.ibotta_challenge.datastore.models.Offer;

/**
 * Created by tglenn on 8/30/17.
 */

public interface OfferDataStore {
    /**
     * Get a list of offers
     *
     * @param offset The number of offers to skip
     * @param limit  The numer of offers to be returned
     * @return The list of offers returned by the query
     */
    List<Offer> getOffers(int offset,
                          int limit);

    /**
     * Get a list of offers
     *
     * @param retailerId The id retailer to query against
     * @return The list of offers returned by the query
     */
    List<Offer> getOffersByRetailerId(long retailerId);

    /**
     * Get a list of offers
     *
     * @param retailerIds
     * @return The map of retailer id to list of offers returned by the query
     */
    Map<Long, List<Offer>> getOffersByRetailerId(long... retailerIds);
}
