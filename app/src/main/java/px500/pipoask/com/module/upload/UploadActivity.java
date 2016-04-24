package px500.pipoask.com.module.upload;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import px500.pipoask.com.R;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.utiity.LogUtils;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "Upload";
    Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Bundle bundle = getIntent().getBundleExtra(ConstKV.BUNDLE_IMAGE_URI);
        if (bundle != null) {
            mUri = bundle.getParcelable(ConstKV.BUNDLE_IMAGE_URI);
            if (mUri != null) {
                LogUtils.debug(TAG, mUri.toString());
            }

        }
    }
}
