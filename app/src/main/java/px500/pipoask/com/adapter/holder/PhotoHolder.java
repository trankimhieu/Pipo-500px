package px500.pipoask.com.adapter.holder;

import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.widget.PhotoView;

/**
 * Created by Sandy on 12/27/15.
 */
public class PhotoHolder extends MainHolder {

    @Bind(R.id.photo)
    public PhotoView photo;

    public PhotoHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
