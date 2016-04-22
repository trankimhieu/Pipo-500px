package px500.pipoask.com.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;

/**
 * Created by Sandy on 12/29/15.
 */
public class SearchHolder extends MainHolder {

    @Bind(R.id.photo)
    public ImageView photo;

    @Bind(R.id.title)
    public TextView title;

    public SearchHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
