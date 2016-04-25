package px500.pipoask.com.helpers;

import java.util.List;

import px500.pipoask.com.data.model.chat.ChatItem;

public class ChatListHelper {

    public static ChatItem findItem(List<ChatItem> list, ChatItem item) {
        for (ChatItem i : list) {
            if (i.getId().equals(item.getId())) {
                return i;
            }
        }
        return null;
    }

}
