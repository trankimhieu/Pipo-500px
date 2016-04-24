
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import px500.pipoask.com.data.model.avatar.Avatars;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("usertype")
    @Expose
    public Integer usertype;
    @SerializedName("fullname")
    @Expose
    public String fullname;
    @SerializedName("userpic_url")
    @Expose
    public String userpicUrl;
    @SerializedName("userpic_https_url")
    @Expose
    public String userpicHttpsUrl;
    @SerializedName("cover_url")
    @Expose
    public Object coverUrl;
    @SerializedName("upgrade_status")
    @Expose
    public Integer upgradeStatus;
    @SerializedName("store_on")
    @Expose
    public Boolean storeOn;
    @SerializedName("affection")
    @Expose
    public Integer affection;
    @SerializedName("avatars")
    @Expose
    public Avatars avatars;
    @SerializedName("followers_count")
    @Expose
    public Integer followersCount;

}
