package px500.pipoask.com.helpers;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import px500.pipoask.com.ChatConfig;
import px500.pipoask.com.data.model.chat.ChatItem;

public class FirebaseHelper {


    private static FirebaseHelper instance;
    final String CHAT_COLLECTION = "chat";
    private Firebase firebaseClient;

    private FirebaseHelper() {
        firebaseClient = new Firebase(ChatConfig.FIREBASE_URL);
    }

    public static FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    public String saveChatItem(ChatItem chatItem) {
        Firebase chatCollectionRef = firebaseClient.child(CHAT_COLLECTION);
        Firebase newChatItem = chatCollectionRef.push();
        newChatItem.setValue(chatItem);
        return newChatItem.getKey();
    }

    public String saveChatItem(ChatItem chatItem, int photoId) {
        Firebase chatCollectionRef = firebaseClient.child(CHAT_COLLECTION).child(String.valueOf(photoId));
        Firebase newChatItem = chatCollectionRef.push();
        newChatItem.setValue(chatItem);
        return newChatItem.getKey();
    }

    public Firebase getChatFirebaseClient() {
        return firebaseClient.child(CHAT_COLLECTION);
    }

    public Firebase getChatFirebaseClient(int photoId) {
        return firebaseClient.child(CHAT_COLLECTION).child(String.valueOf(photoId));
    }

    public void updateChatItemStatus(ChatItem item) {
        Firebase firebaseItem = getChatFirebaseClient().child(item.getId());
        Map<String, Object> status = new HashMap<>();
        status.put("status", item.getStatus());
        firebaseItem.updateChildren(status);
    }
}
