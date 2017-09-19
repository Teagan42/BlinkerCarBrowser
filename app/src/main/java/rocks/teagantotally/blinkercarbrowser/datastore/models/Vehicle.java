package rocks.teagantotally.blinkercarbrowser.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tglenn on 9/14/17.
 */

public class Vehicle
          implements Serializable {
    private static int vehicleIndex = 0;

    public Vehicle() {
        // Records should have indices! Adding fake index for completion sake
        index = vehicleIndex++;
    }

    public int index;

    @SerializedName("year")
    public String year;

    @SerializedName("make")
    public String make;

    @SerializedName("model")
    public String model;

    @SerializedName("mileage")
    public int mileage;

    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("created_at")
    public Date createdAt;
}
