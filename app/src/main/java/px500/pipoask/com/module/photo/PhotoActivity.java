package px500.pipoask.com.module.photo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.NavigationManager;
import px500.pipoask.com.R;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.BaseActivity;
import px500.pipoask.com.module.chat.ChatActivity;
import px500.pipoask.com.utiity.LogUtils;
import px500.pipoask.com.utiity.StringUtils;
import uk.co.senab.photoview.PhotoView;

public class PhotoActivity extends BaseActivity implements IPhotoView {

    public static final String TAG = "PhotoActivity";
    public static final String PHOTO_ID = "photo_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.my_image_view)
    PhotoView photoView;

    @Bind(R.id.copyright)
    Button copyright;

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
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        textViewSlideUpBar.setText(Html.fromHtml(getString(R.string.swipe_up_bar_text)));
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

        copyright.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(ConstKV.PHOTO_ID, photo.id);
                new NavigationManager<ChatActivity>().openActivity(this, ChatActivity.class, ConstKV.BUNDLE_PHOTO_ID, bundle);
            } catch (Exception exp) {
                LogUtils.error(TAG, exp.getMessage());
            }
        });
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
