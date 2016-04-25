package px500.pipoask.com.module.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.ChatViewAdapter;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.model.chat.ChatItem;
import px500.pipoask.com.helpers.ChatHelper;
import px500.pipoask.com.helpers.ChatListHelper;
import px500.pipoask.com.helpers.FirebaseHelper;


public class ChatActivity extends AppCompatActivity {

    @Bind(R.id.editTextChatInput)
    EditText editTextChatInput;
    @Bind(R.id.listViewChatContent)
    ListView listViewChatContent;

    int photoId = 0;

    List<ChatItem> chatItemList;
    ChatViewAdapter adapter;
    String username;
    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            String content = v.getText().toString();

            if (content.equals("")) {
                return false;
            }
            ChatItem item = new ChatItem(username, content);
            item.setStatus(ChatHelper.CHAT_STATUS_SENDING);
            chatItemList.add(item);
            adapter.notifyDataSetChanged();
            listViewChatContent.smoothScrollToPosition(adapter.getCount() - 1);
            String id = FirebaseHelper.getInstance().saveChatItem(item, photoId);
            item.setId(id);
            editTextChatInput.setText("");
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        chatItemList = new ArrayList<>();
        username = ChatHelper.getInstance(ChatActivity.this).getChatUsername();
        adapter = new ChatViewAdapter(ChatActivity.this, 0, chatItemList);
        listViewChatContent.setAdapter(adapter);

        photoId = getIntent().getBundleExtra(ConstKV.BUNDLE_PHOTO_ID).getInt(ConstKV.PHOTO_ID, 0);

        editTextChatInput.setImeActionLabel("Send", KeyEvent.KEYCODE_ENTER);
        editTextChatInput.setOnEditorActionListener(onEditorActionListener);

        //TODO: Will move it to presenter to have best design and remove logic code inside activity
        FirebaseHelper.getInstance().getChatFirebaseClient(photoId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatItem tempItem = new ChatItem(dataSnapshot);

                if (ChatHelper.getInstance(ChatActivity.this).isFromThisUser(tempItem)) {
                    if (tempItem.getStatus().equals(ChatHelper.CHAT_STATUS_SENDING)) {
                        tempItem.setStatus(ChatHelper.CHAT_STATUS_DELIVERED);
                        FirebaseHelper.getInstance().updateChatItemStatus(tempItem, photoId);
                    }
                } else {
                    tempItem.setStatus(ChatHelper.CHAT_STATUS_RECEIVED);
                    FirebaseHelper.getInstance().updateChatItemStatus(tempItem, photoId);
                }

                ChatItem item = ChatListHelper.findItem(chatItemList, tempItem);
                if (item == null) {
                    chatItemList.add(tempItem);
                }
                adapter.notifyDataSetChanged();
                listViewChatContent.smoothScrollToPosition(adapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChatItem tempItem = new ChatItem(dataSnapshot);
                ChatItem item = ChatListHelper.findItem(chatItemList, tempItem);
                if (item == null) {
                    chatItemList.add(tempItem);

                } else {
                    item.setStatus(tempItem.getStatus());
                    item.setUsername(tempItem.getUsername());
                    item.setMessage(tempItem.getMessage());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
