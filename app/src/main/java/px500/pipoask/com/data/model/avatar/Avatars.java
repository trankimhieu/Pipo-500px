
package px500.pipoask.com.data.model.avatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Avatars {

    @SerializedName("default")
    @Expose
    private AvatarDefault _default;
    @SerializedName("large")
    @Expose
    private AvatarLarge large;
    @SerializedName("small")
    @Expose
    private AvatarSmall small;
    @SerializedName("tiny")
    @Expose
    private AvatarTiny tiny;

    /**
     * @return The _default
     */
    public AvatarDefault getDefault() {
        return _default;
    }

    /**
     * @param _default The default
     */
    public void setDefault(AvatarDefault _default) {
        this._default = _default;
    }

    /**
     * @return The large
     */
    public AvatarLarge getLarge() {
        return large;
    }

    /**
     * @param large The large
     */
    public void setLarge(AvatarLarge large) {
        this.large = large;
    }

    /**
     * @return The small
     */
    public AvatarSmall getSmall() {
        return small;
    }

    /**
     * @param small The small
     */
    public void setSmall(AvatarSmall small) {
        this.small = small;
    }

    /**
     * @return The tiny
     */
    public AvatarTiny getTiny() {
        return tiny;
    }

    /**
     * @param tiny The tiny
     */
    public void setTiny(AvatarTiny tiny) {
        this.tiny = tiny;
    }

}
