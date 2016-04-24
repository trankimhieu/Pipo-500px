package px500.pipoask.com.module.upload;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.emmasuzuki.easyform.EasyTextInputLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.race604.flyrefresh.FlyRefreshLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import px500.pipoask.com.R;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.model.Category;
import px500.pipoask.com.utiity.LogUtils;

import static px500.pipoask.com.data.local.ConstKV.CATEGORY_LIST;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "Upload";

    @Bind(R.id.input_name)
    EasyTextInputLayout inputName;

    @Bind(R.id.input_desc)
    EasyTextInputLayout inputDesc;

    @Bind(R.id.fly_layout)
    FlyRefreshLayout flyLayout;

    @Bind(R.id.image_view_photo)
    ImageView imageViewPhoto;

    @Bind(R.id.spinner_category)
    Spinner spinnerCategory;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Display display = getWindowManager().getDefaultDisplay();
        int swidth = display.getWidth();
        ViewGroup.LayoutParams params = imageViewPhoto.getLayoutParams();
        params.width = ViewGroup.LayoutParams.FILL_PARENT;
        params.height = swidth;
        imageViewPhoto.setLayoutParams(params);
        Bundle bundle = getIntent().getBundleExtra(ConstKV.BUNDLE_IMAGE_URI);
        if (bundle != null) {
            mUri = bundle.getParcelable(ConstKV.BUNDLE_IMAGE_URI);
            if (mUri != null) {

                imageViewPhoto.setImageURI(mUri);
                LogUtils.debug(TAG, mUri.toString());
            }
        }

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, 0, ConstKV.CATEGORY_LIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private class CategorySpinnerAdapter extends ArrayAdapter<Category> {

        public CategorySpinnerAdapter(Context context, int resource, List<Category> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return super.getDropDownView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }
}
