package px500.pipoask.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.holder.MainHolder;
import px500.pipoask.com.data.model.Photo;

public class SearchAdapter extends RecyclerView.Adapter<MainHolder> {

    private final Activity mActivity;
    private final List<Photo> photoList;

    private final MainHolder.ClickListener clickListener;

    public SearchAdapter(Activity activity, List<Photo> photoList, MainHolder.ClickListener clickListener) {
        mActivity = activity;
        this.photoList = photoList;
        this.clickListener = clickListener;
    }

    public void setData(List<Photo> newPhotoList) {
        photoList.clear();
        photoList.addAll(newPhotoList);
        notifyDataSetChanged();
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup view = (ViewGroup) mInflater.inflate(R.layout.adapter_search, parent, false);
        SearchHolder searchHolder = new SearchHolder(view);
        searchHolder.setClickListener(clickListener);
        return searchHolder;
    }

    @Override
    public void onBindViewHolder(MainHolder viewHolder, int position) {
        SearchHolder searchHolder = (SearchHolder) viewHolder;
        Picasso.with(mActivity)
                .load(photoList.get(position).imageUrl)
                .into(searchHolder.photo);
        searchHolder.title.setText(photoList.get(position).name);
    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }

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
}
