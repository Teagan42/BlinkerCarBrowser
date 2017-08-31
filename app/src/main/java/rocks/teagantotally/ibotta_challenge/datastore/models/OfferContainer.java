package rocks.teagantotally.ibotta_challenge.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tglenn on 8/31/17.
 */

public class OfferContainer
          implements Serializable {
    @SerializedName("offers")
    public Offer[] offers;
}
