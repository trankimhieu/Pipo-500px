
package px500.pipoask.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Upload {

    @SerializedName("upload_key")
    @Expose
    public String uploadKey;
    @SerializedName("photo")
    @Expose
    public Photo photo;

}
