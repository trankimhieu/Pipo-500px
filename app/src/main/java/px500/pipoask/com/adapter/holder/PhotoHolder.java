package px500.pipoask.com.adapter.holder;

import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.widget.PhotoView;

public class PhotoHolder extends MainHolder {

    @Bind(R.id.photo)
    public PhotoView photo;

    public PhotoHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
