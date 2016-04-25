
package px500.pipoask.com.data.model.chat;

import com.firebase.client.DataSnapshot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ChatItem {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String id;

    public ChatItem(String username, String message) {
        this.setUsername(username);
        this.setMessage(message);
    }

    public ChatItem(DataSnapshot dataSnapshot) {
        ChatItem tempItem = dataSnapshot.getValue(ChatItem.class);
        this.setUsername(tempItem.getUsername());
        this.setMessage(tempItem.getMessage());
        this.setStatus(tempItem.getStatus());
        this.setId(dataSnapshot.getKey());
    }

    public ChatItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    ;

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
