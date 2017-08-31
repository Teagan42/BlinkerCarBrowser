package rocks.teagantotally.ibotta_challenge.datastore;

import java.util.List;

import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;

/**
 * Created by tglenn on 8/30/17.
 */

public interface RetailerDataStore {
    /**
     * Retrieve a list of retailers
     *
     * @param offset The number of retailers to skip
     * @param limit  The number of retailers to be returned
     * @return The list of retailers returned by the query
     */
    List<Retailer> getRetailers(int offset,
                                int limit);

    /**
     * Retrieve a specific retailer
     *
     * @param id Id of the retailer to be returned
     * @return The retailer returned by the query
     */
    Retailer getRetailerById(long id);

    /**
     * Retrieve a specific retailer
     *
     * @param name Name of the retailer to be returned
     * @return The retailer returned by the query
     */
    Retailer getRetailerByName(String name);
}
