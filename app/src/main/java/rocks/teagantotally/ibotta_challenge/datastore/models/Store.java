package rocks.teagantotally.ibotta_challenge.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tglenn on 8/30/17.
 */

public class Store
          implements Serializable {
    @SerializedName("events")
    public Object events; // TODO : Type
    @SerializedName("id")
    public long id;
    @SerializedName("retailer_id")
    public long retailerId;
    @SerializedName("lat")
    public float latitude;
    @SerializedName("long")
    public float longitude;
}
