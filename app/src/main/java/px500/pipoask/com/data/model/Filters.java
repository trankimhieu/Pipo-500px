
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Parcel
public class Filters {

    @SerializedName("category")
    @Expose
    public Boolean category;
    @SerializedName("exclude")
    @Expose
    public Boolean exclude;

}
