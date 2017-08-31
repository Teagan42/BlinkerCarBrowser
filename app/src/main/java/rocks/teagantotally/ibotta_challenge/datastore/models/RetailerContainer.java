package rocks.teagantotally.ibotta_challenge.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tglenn on 8/31/17.
 */

public class RetailerContainer
          implements Serializable {
    @SerializedName("retailers")
    public Retailer[] retailers;
}
