
package px500.pipoask.com.data.model.avatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Parcel
public class Avatars {

    @SerializedName("default")
    @Expose
    public AvatarDefault _default;
    @SerializedName("large")
    @Expose
    public AvatarLarge large;
    @SerializedName("small")
    @Expose
    public AvatarSmall small;
    @SerializedName("tiny")
    @Expose
    public AvatarTiny tiny;

}
