package px500.pipoask.com.module.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.ChatViewAdapter;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.data.model.chat.ChatItem;
import px500.pipoask.com.helpers.ChatHelper;
import px500.pipoask.com.helpers.ChatListHelper;
import px500.pipoask.com.helpers.FirebaseHelper;
import px500.pipoask.com.module.base.BaseActivity;
import px500.pipoask.com.utiity.StringUtils;
import uk.co.senab.photoview.PhotoView;

public class PhotoActivity extends BaseActivity implements IPhotoView {

    public static final String TAG = "PhotoActivity";
    public static final String PHOTO_ID = "photo_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.my_image_view)
    PhotoView photoView;


    @Bind(R.id.status)
    TextView status;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.loading)
    LinearLayout llLoading;

    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout mLayout;

    @Bind(R.id.slide_up_bar)
    TextView textViewSlideUpBar;


    @Inject
    PhotoPresenter photoPresenter;
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
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextChatInput.getWindowToken(), 0);
            return true;
        }
    };
    private Photo photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);

        photoPresenter.attachView(this);

        initUI();


        Parcelable parcelable = getIntent().getParcelableExtra(PHOTO_ID);
        photo = Parcels.unwrap(parcelable);
        setTitle(photo.name);

        photoPresenter.getPhotoById(photo.id);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.equals(SlidingUpPanelLayout.PanelState.ANCHORED) || newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                    textViewSlideUpBar.setText(Html.fromHtml(getString(R.string.swipe_down_bar_text)));
                } else if (newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    textViewSlideUpBar.setText(Html.fromHtml(getString(R.string.swipe_up_bar_text)));
                }
            }
        });
        mLayout.setFadeOnClickListener(view -> mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED));

        textViewSlideUpBar.setText(Html.fromHtml(getString(R.string.swipe_up_bar_text)));


        chatItemList = new ArrayList<>();
        username = ChatHelper.getInstance(PhotoActivity.this).getChatUsername();
        adapter = new ChatViewAdapter(PhotoActivity.this, 0, chatItemList);
        listViewChatContent.setAdapter(adapter);

        photoId = photo.id;

        editTextChatInput.setImeActionLabel("Send", KeyEvent.KEYCODE_ENTER);
        editTextChatInput.setOnEditorActionListener(onEditorActionListener);

        //TODO: Will move it to presenter to have best design and remove logic code inside activity
        FirebaseHelper.getInstance().getChatFirebaseClient(photoId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatItem tempItem = new ChatItem(dataSnapshot);

                if (ChatHelper.getInstance(PhotoActivity.this).isFromThisUser(tempItem)) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                item.setTitle(R.string.action_show);
            } else {
                item.setTitle(R.string.action_hide);
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                        item.setTitle(R.string.action_show);
                    } else {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle(R.string.action_hide);
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.7f);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        item.setTitle(R.string.action_anchor_disable);
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle(R.string.action_anchor_enable);
                    }
                }
                return true;
            }
            case R.id.action_share: {
                if (photo != null && !StringUtils.isEmpty(photo.url)) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Sharing Photo");
                    i.putExtra(Intent.EXTRA_TEXT, "http://www.500px.com" + photo.url);
                    startActivity(Intent.createChooser(i, "Share Photo"));
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void loadingImage() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void imageLoaded(Photo photo) {
        llLoading.setVisibility(View.GONE);
        Glide.with(this)
                .load(photo.imageUrl)
                .into(photoView);
    }

    @Override
    public void errorLoadingImage(String e) {
        status.setText(e);
        progressBar.setVisibility(View.GONE);
    }
}
