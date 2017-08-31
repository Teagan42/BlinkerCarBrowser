package rocks.teagantotally.ibotta_challenge.datastore;

import java.util.List;

import rocks.teagantotally.ibotta_challenge.datastore.models.Store;

/**
 * Created by tglenn on 8/30/17.
 */

public interface StoreDataStore {
    /**
     * Retrieve a list of stores
     *
     * @param offset The number of items to skip when querying
     * @param limit  The number of items to be returned
     * @return A list of stores returned by the query
     */
    List<Store> getStores(int offset,
                          int limit);

    /**
     * Retrieve a store
     *
     * @param storeId The id of the store to be retrieved
     * @return The store returned by the query
     */
    Store getStoreById(long storeId);

    /**
     * Retrieve stores associated to a specified retailer
     *
     * @param retailerId Retailer id to query against
     * @return List of stores for the query
     */
    List<Store> getStoresByRetailer(long retailerId);
}
