package rocks.teagantotally.ibotta_challenge.datastore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tglenn on 8/30/17.
 */

public class Offer
          implements Serializable {

    public class Category
              implements Serializable {
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public long id;
        @SerializedName("sort_order")
        public float sortOrder;
    }

    public class Reward
              implements Serializable {

        @SerializedName("content")
        public String content;
        @SerializedName("linked_offer_ids")
        public long[] linkedOfferIds;
        @SerializedName("liverail")
        public Object liverail; // TODO : Type
        @SerializedName("options")
        public Object[] options; // TODO : Type
        @SerializedName("questions")
        public Object[] questions; // TODO : Type
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
        @SerializedName("weight")
        public int weight;
        @SerializedName("amount")
        public float amount;
        @SerializedName("answer")
        public int answer;
        @SerializedName("finished")
        public boolean finished;
        @SerializedName("id")
        public long id;
        @SerializedName("invite_count")
        public long inviteCount;
        @SerializedName("viewed")
        public boolean viewed;
        @SerializedName("type_enum")
        public String type_enum; // TODO : Enum
    }

    @SerializedName("bonus_ids")
    public Object[] bonusIds;
    @SerializedName("categories")
    public Category[] categories;
    @SerializedName("description")
    public String description;
    @SerializedName("digital_products")
    public Object[] digitalProducts;
    @SerializedName("end_time")
    public Date endTime; // "Aug 21, 2015 1:00:00 AM"
    @SerializedName("expiration")
    public Date expiration;
    @SerializedName("hotness")
    public float hotness;
    @SerializedName("large_url")
    public String largeUrl;
    @SerializedName("rewards")
    public Reward[] rewards;
    @SerializedName("launched_at")
    public Date launchedAt;
    @SerializedName("multiples")
    public String multiples;
    @SerializedName("name")
    public String name;
    @SerializedName("offer_tags")
    public Object[] offerTags; // TODO : Type
    @SerializedName("promos")
    public Object[] promos; // TODO : Type
    @SerializedName("purchase_type")
    public String purchaseType;
    @SerializedName("retailers")
    public long[] retailerIds;
    @SerializedName("share_url")
    public String shareUrl;
    @SerializedName("terms")
    public String terms;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("total_likes")
    public long totalLikes;
    @SerializedName("activated")
    public boolean activated;
    @SerializedName("active")
    public boolean active;
    @SerializedName("amount")
    public float amount;
    @SerializedName("combo")
    public boolean combo;
    @SerializedName("game_viewed")
    public boolean gameViewed;
    @SerializedName("hide_available_at")
    public boolean hideAvailableAt;
    @SerializedName("id")
    public long id;
    @SerializedName("ignore_barcode")
    public boolean ignoreBarcode;
    @SerializedName("liked")
    public boolean liked;
    @SerializedName("multiples_count")
    public int multiplesCount;
    @SerializedName("new_flag")
    public boolean newFlag;
    @SerializedName("non_item")
    public boolean nonItem;
    @SerializedName("non_item_total")
    public long nonItemTotal;
    @SerializedName("random_weight")
    public boolean randomWeight;
    @SerializedName("random_weight_total")
    public long randomWeightTotal;
    @SerializedName("receipt_total")
    public long receipt_total;
    @SerializedName("retailer_exclusive")
    public boolean retailerExclusive;
    @SerializedName("total_friend_likes")
    public long totalFriendLikes;
    @SerializedName("verified")
    public boolean verified;
    @SerializedName("vote")
    public long vote;
    @SerializedName("weight")
    public long weight;
    @SerializedName("earnings_potential")
    public float earningsPotential;
    @SerializedName("multiples_enum")
    public String multiplesEnum; // TODO : Enum
    @SerializedName("offer_type_enum")
    public String offerTypeENum; // TODO : Enum
    @SerializedName("product_groups")
    public Object[] productGroups; // TODO : Type
    @SerializedName("products")
    public Object[] products; // TODO : Type
    @SerializedName("purchase_type_enum")
    public String purchaseTypeEnum; // TODO : Enum
    @SerializedName("unlocked_total")
    public long unlockedTotal;
    @SerializedName("available_as_any_brand")
    public boolean availableAsAnyBrand;
    @SerializedName("expired")
    public boolean expired;
}
