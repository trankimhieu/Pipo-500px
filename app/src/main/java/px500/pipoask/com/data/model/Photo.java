
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Parcel
public class Photo {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("camera")
    @Expose
    public String camera;
    @SerializedName("lens")
    @Expose
    public String lens;
    @SerializedName("focal_length")
    @Expose
    public String focalLength;
    @SerializedName("iso")
    @Expose
    public String iso;
    @SerializedName("shutter_speed")
    @Expose
    public String shutterSpeed;
    @SerializedName("aperture")
    @Expose
    public String aperture;
    @SerializedName("times_viewed")
    @Expose
    public Integer timesViewed;
    @SerializedName("rating")
    @Expose
    public Double rating;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("category")
    @Expose
    public Integer category;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("taken_at")
    @Expose
    public String takenAt;
    @SerializedName("hi_res_uploaded")
    @Expose
    public Integer hiResUploaded;
    @SerializedName("for_sale")
    @Expose
    public Boolean forSale;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("votes_count")
    @Expose
    public Integer votesCount;
    @SerializedName("favorites_count")
    @Expose
    public Integer favoritesCount;
    @SerializedName("comments_count")
    @Expose
    public Integer commentsCount;
    @SerializedName("nsfw")
    @Expose
    public Boolean nsfw;
    @SerializedName("sales_count")
    @Expose
    public Integer salesCount;
    @SerializedName("for_sale_date")
    @Expose
    public String forSaleDate;
    @SerializedName("highest_rating")
    @Expose
    public Double highestRating;
    @SerializedName("highest_rating_date")
    @Expose
    public String highestRatingDate;
    @SerializedName("license_type")
    @Expose
    public Integer licenseType;
    //    @SerializedName("converted")
//    @Expose
//    public Boolean converted;
    @SerializedName("collections_count")
    @Expose
    public Integer collectionsCount;
    @SerializedName("crop_version")
    @Expose
    public Integer cropVersion;
    @SerializedName("privacy")
    @Expose
    public Boolean privacy;
    @SerializedName("profile")
    @Expose
    public Boolean profile;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("images")
    @Expose
    public List<Image> images = new ArrayList<Image>();
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("positive_votes_count")
    @Expose
    public Integer positiveVotesCount;
    @SerializedName("converted_bits")
    @Expose
    public Integer convertedBits;
    @SerializedName("store_download")
    @Expose
    public Boolean storeDownload;
    @SerializedName("store_print")
    @Expose
    public Boolean storePrint;
    @SerializedName("store_license")
    @Expose
    public Boolean storeLicense;
    @SerializedName("request_to_buy_enabled")
    @Expose
    public Boolean requestToBuyEnabled;
    @SerializedName("license_requests_enabled")
    @Expose
    public Boolean licenseRequestsEnabled;
    @SerializedName("watermark")
    @Expose
    public Boolean watermark;
    @SerializedName("image_format")
    @Expose
    public String imageFormat;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("licensing_requested")
    @Expose
    public Boolean licensingRequested;
    @SerializedName("voted")
    @Expose
    public Boolean voted;


}
