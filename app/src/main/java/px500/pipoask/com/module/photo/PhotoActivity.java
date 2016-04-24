package px500.pipoask.com.module.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.BaseActivity;
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
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(v -> finish());

        copyright.setOnClickListener(v -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://500px.com" + photo.url));
                startActivity(browserIntent);
            } catch (Exception exp) {
                LogUtils.error(TAG, exp.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            if (photo != null && !StringUtils.isEmpty(photo.url)) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing Photo");
                i.putExtra(Intent.EXTRA_TEXT, "http://www.500px.com" + photo.url);
                startActivity(Intent.createChooser(i, "Share Photo"));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadingImage() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void imageLoaded(Photo photo) {
        llLoading.setVisibility(View.GONE);
        Picasso.with(this)
                .load(photo.imageUrl)
                .into(photoView);

    }

    @Override
    public void errorLoadingImage(String e) {
        status.setText(e);
        progressBar.setVisibility(View.GONE);
    }
}
