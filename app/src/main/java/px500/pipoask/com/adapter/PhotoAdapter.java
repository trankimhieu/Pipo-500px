package px500.pipoask.com.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.holder.MainHolder;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.widget.PhotoView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoAdapter extends RecyclerView.Adapter<MainHolder> {

    private static final String TAG = "PhotoAdapter";
    private final Activity mActivity;
    private final List<Photo> photoList;
    private final MainHolder.ClickListener clickListener;
    @Inject
    PhotoApi photoApi;

    public PhotoAdapter(Activity activity) {
        ((GroovyApplication) activity.getApplication()).getAppComponent().inject(this);
        mActivity = null;
        photoList = null;
        clickListener = null;
    }

    public PhotoAdapter(Activity activity, List<Photo> photoList, MainHolder.ClickListener clickListener) {
        mActivity = activity;
        this.photoList = photoList;
        this.clickListener = clickListener;
    }

    public void addPhoto(Photo photo) {
        photoList.add(photo);
        notifyDataSetChanged();
    }

    public void setData(List<Photo> newPhotoList) {
        photoList.addAll(newPhotoList);
        notifyDataSetChanged();
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup view = (ViewGroup) mInflater.inflate(R.layout.adapter_photo, parent, false);
        PhotoHolder photoHolder = new PhotoHolder(view);
        photoHolder.setClickListener(clickListener);
        return photoHolder;
    }

    @Override
    public void onBindViewHolder(MainHolder viewHolder, int position) {
        PhotoHolder photoHolder = (PhotoHolder) viewHolder;
        Photo photo = photoList.get(position);
        if (photo.voted) {
            photoHolder.imageButtonVote.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.icon_vote1));

        } else {
            photoHolder.imageButtonVote.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.icon_vote0));

        }
        photoHolder.imageButtonVote.setTag(photo);
        Picasso.with(mActivity)
                .load(photo.imageUrl)
                .into(photoHolder.photo);
    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }

    public class PhotoHolder extends MainHolder {

        @Bind(R.id.photo)
        public PhotoView photo;

        @Bind(R.id.image_button_vote)
        public ImageButton imageButtonVote;

        public PhotoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.image_button_vote)
        void onClickImageButtonVote(View view) {
            Photo photo = (Photo) view.getTag();
            if (photo.voted) {
                photoApi.postVote(photo.id, 0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Photo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Photo photo) {
                        imageButtonVote.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.icon_vote0));
                    }
                });
            } else {
                photoApi.postVote(photo.id, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Photo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Photo photo) {
                        imageButtonVote.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.icon_vote1));
                    }
                });
            }
        }

    }
}