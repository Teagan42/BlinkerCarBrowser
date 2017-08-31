package rocks.teagantotally.ibotta_challenge.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tglenn on 8/30/17.
 */

public class Retailer
          implements Serializable {

    public class RedemptionMeta
              implements Serializable {
        @SerializedName("additionalRedemptionInstructions")
        public String[] additionalRedemptionInstructions;
        @SerializedName("receiptName")
        public String receiptName;
        @SerializedName("redeem_string")
        public String redeemString;
        @SerializedName("max_receipt_age_days")
        public int maxReceiptAgeInDays;
    }

    @SerializedName("allowed_viewports")
    public Object[] allowedViewPorts; // TODO : Type
    @SerializedName("any_brand_full_url")
    public String anyBrandFullUrl;
    @SerializedName("any_brand_icon_url")
    public String anyBrandIconUrl;
    @SerializedName("card_signup_url")
    public String cardSignupUrl;
    @SerializedName("category_ids")
    public long[] categoryIds;
    @SerializedName("distance")
    public long distance;
    @SerializedName("exclusive_image_url")
    public String exclusiveImageUrl;
    @SerializedName("gallery_features")
    public Object[] galleryFeatures; // TODO : Type
    @SerializedName("icon_url")
    public String iconUrl;
    @SerializedName("name")
    public String name;
    @SerializedName("redemption_meta")
    public RedemptionMeta redemptionMeta;
    @SerializedName("stores")
    public Object[] stores; // TODO : Type
    @SerializedName("verification_type")
    public String verificationType;
    @SerializedName("active") //: true,
    public boolean active;
    @SerializedName("any_brand") //: true,
    public boolean anyBrand;
    @SerializedName("barcode") //: true,
    public boolean barcode;
    @SerializedName("featured") //: false,
    public boolean featured;
    @SerializedName("id") //: 48,
    public long id;
    @SerializedName("sort_order") //: 1501,
    public long sortOrder;
    @SerializedName("allowed_viewports_enum_set")  //[],
    public Object[] allowedViewPortsEnumSet;
    @SerializedName("verification_type_enum") //"RECEIPT",
    public String verificationTypeEnum;
    @SerializedName("linked") //: false
    public boolean linked;
}
