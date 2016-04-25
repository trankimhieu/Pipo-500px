package px500.pipoask.com.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.google.gson.Gson;

import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import px500.pipoask.com.data.model.User;
import px500.pipoask.com.data.model.chat.ChatItem;

public class ChatHelper {

    public static String CHAT_STATUS_SENDING = "sending";
    public static String CHAT_STATUS_DELIVERED = "delivered";
    public static String CHAT_STATUS_RECEIVED = "received";
    private static ChatHelper instance;
    final String KEY_CHAT_USERNAME = "px500.pipoask.com.ChatUsername";
    final String KEY_CHAT_SHAREREF = "px500.pipoask.com.chat";
    String username;
    private Context context;

    private ChatHelper(Context context) {
        this.context = context;
        username = getChatUsername();
    }

    public static ChatHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ChatHelper(context);
        }
        return instance;
    }

    private static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getChatUsername() {
        String userJson = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_INFO, "");

        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);
            username = user.username;
            saveCreatedUsername(username);
            return user.username;
        } else {
            String username = getCreatedUsername();
            if (username.equals("")) {
                String deviceId = ChatHelper.getDeviceId(context);
                String substr = deviceId.substring(deviceId.length() - 4);
                username = "USER_" + substr;
                saveCreatedUsername(username);
            }
            return username;
        }
    }

    public boolean isFromThisUser(ChatItem item) {
        return item.getUsername().equals(username);
    }

    private String getCreatedUsername() {
        Context context = this.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                KEY_CHAT_SHAREREF, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_CHAT_USERNAME, "");
    }

    private void saveCreatedUsername(String username) {
        Context context = this.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                KEY_CHAT_SHAREREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_CHAT_USERNAME, username);
        editor.apply();
    }


}
