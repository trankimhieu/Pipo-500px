package px500.pipoask.com.module.upload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emmasuzuki.easyform.EasyTextInputLayout;
import com.koushikdutta.ion.Ion;
import com.race604.flyrefresh.FlyRefreshLayout;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import oauth.signpost.basic.DefaultOAuthConsumer;
import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.NavigationManager;
import px500.pipoask.com.R;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import px500.pipoask.com.data.model.Category;
import px500.pipoask.com.data.model.Upload;
import px500.pipoask.com.module.main.MainActivity;
import px500.pipoask.com.utiity.LogUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    File file;

    LayoutInflater inflater;

    @Inject
    PhotoApi photoApi;

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("URI", uri + "");
        String result = uri + "";
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length - 1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        ((GroovyApplication) this.getApplication()).getAppComponent().inject(this);
        ((GroovyApplication) this.getApplication()).getAppComponent().inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Display display = getWindowManager().getDefaultDisplay();
        int swidth = display.getWidth();
        ViewGroup.LayoutParams params = imageViewPhoto.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = swidth;
        imageViewPhoto.setLayoutParams(params);
        Bundle bundle = getIntent().getBundleExtra(ConstKV.BUNDLE_IMAGE_URI);
        if (bundle != null) {
            mUri = bundle.getParcelable(ConstKV.BUNDLE_IMAGE_URI);
            if (mUri != null) {
                imageViewPhoto.setImageURI(mUri);
                LogUtils.debug(TAG, mUri.toString());
                file = new File(getPath(UploadActivity.this, mUri));
            }
        }

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, 0, ConstKV.CATEGORY_LIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        flyLayout.setOnPullRefreshListener(new FlyRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh(FlyRefreshLayout view) {
                LogUtils.debug(TAG, "Refresh");
                String name = inputName.getEditText().getText().toString();
                String desc = inputDesc.getEditText().getText().toString();
                Category cat = (Category) spinnerCategory.getSelectedView().getTag();

                photoApi.postPhoto(name, desc, cat.id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Upload>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(UploadActivity.this,getString(R.string.uploading),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Upload upload) {
                        LogUtils.debug(TAG, upload.uploadKey);

                        String token = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN, null);
                        String token_secret = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN_SECRET, null);
                        DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET);
                        consumer.setTokenWithSecret(token, token_secret);

                        Ion.with(UploadActivity.this)
                                .load("http://upload.500px.com/v1/upload")
                                .addQuery("photo_id", String.valueOf(upload.photo.id))
                                .addQuery("upload_key", upload.uploadKey)
                                .addQuery("consumer_key", BuildConfig.CONSUMER_KEY)
                                .setMultipartFile("file", "image", file)
                                .asJsonObject()
                                .setCallback((e, result) -> {
                                    if (e != null)
                                        LogUtils.error(TAG, e.getMessage());
                                    if (result != null)
                                        LogUtils.debug(TAG, result.toString());
                                    flyLayout.onRefreshFinish();
                                    new Handler().postDelayed(() -> new NavigationManager<MainActivity>().openActivity(UploadActivity.this, MainActivity.class), 300);
                                });
                    }
                });
            }

            @Override
            public void onRefreshAnimationEnd(FlyRefreshLayout view) {

            }
        });

        View actionButton = flyLayout.getHeaderActionButton();
        if (actionButton != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flyLayout.startRefresh();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private class CategorySpinnerAdapter extends ArrayAdapter<Category> {

        List<Category> categoryList;

        public CategorySpinnerAdapter(Context context, int resource, List<Category> objects) {
            super(context, resource, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            categoryList = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Category category = categoryList.get(position);
            View row = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            TextView textView = (TextView) row.findViewById(android.R.id.text1);
            textView.setTag(category);
            textView.setText(category.name);
            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Category category = categoryList.get(position);
            View row = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            TextView textView = (TextView) row.findViewById(android.R.id.text1);
            textView.setTag(category);
            textView.setText(category.name);
            return row;
        }
    }
}
