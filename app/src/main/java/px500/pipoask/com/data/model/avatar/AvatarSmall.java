
package px500.pipoask.com.data.model.avatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AvatarSmall {

    @SerializedName("https")
    @Expose
    private String https;

    /**
     * @return The https
     */
    public String getHttps() {
        return https;
    }

    /**
     * @param https The https
     */
    public void setHttps(String https) {
        this.https = https;
    }

}
