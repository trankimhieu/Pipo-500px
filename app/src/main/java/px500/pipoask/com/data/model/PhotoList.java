
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PhotoList {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_items")
    @Expose
    public Integer totalItems;
    @SerializedName("photos")
    @Expose
    public List<Photo> photos = new ArrayList<Photo>();
    @SerializedName("filters")
    @Expose
    public Filters filters;
    @SerializedName("feature")
    @Expose
    public String feature;

}
