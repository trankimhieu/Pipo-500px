package px500.pipoask.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import px500.pipoask.com.R;
import px500.pipoask.com.data.model.chat.ChatItem;
import px500.pipoask.com.helpers.ChatHelper;

public class ChatViewAdapter extends ArrayAdapter<ChatItem> {

    List<ChatItem> chatItemList;
    LayoutInflater inflater;
    private Context context;

    public ChatViewAdapter(Context context, int resource, List<ChatItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.chatItemList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ChatItem item = chatItemList.get(position);
        View messageView;
        TextView textViewMessage;
        TextView textViewUsername;
        TextView textViewStatus;
        if (ChatHelper.getInstance(context).isFromThisUser(item)) {
            messageView = inflater.inflate(R.layout.cell_chat_item_mine, parent, false);
            textViewStatus = (TextView) messageView.findViewById(R.id.text_view_status);
            textViewStatus.setText(item.getStatus());
        } else {
            messageView = inflater.inflate(R.layout.cell_chat_item_other, parent, false);
            textViewUsername = (TextView) messageView.findViewById(R.id.text_view_username);
            textViewUsername.setText(item.getUsername());
        }
        textViewMessage = (TextView) messageView.findViewById(R.id.text_view_message);
        textViewMessage.setText(item.getMessage());

        return messageView;
    }
}
