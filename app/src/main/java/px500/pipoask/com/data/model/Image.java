
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Parcel
public class Image {

    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("https_url")
    @Expose
    public String httpsUrl;
    @SerializedName("format")
    @Expose
    public String format;

}
