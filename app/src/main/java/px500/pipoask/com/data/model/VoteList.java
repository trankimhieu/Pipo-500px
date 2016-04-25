
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Parcel
public class VoteList {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_items")
    @Expose
    public Integer totalItems;
    @SerializedName("users")
    @Expose
    public List<User> users = new ArrayList<User>();

}
