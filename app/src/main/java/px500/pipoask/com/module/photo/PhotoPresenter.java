package px500.pipoask.com.module.photo;

import android.app.Activity;

import javax.inject.Inject;

import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.R;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.model.PhotoDetail;
import px500.pipoask.com.module.base.BasePresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sandy on 12/26/15.
 */
public class PhotoPresenter extends BasePresenter<IPhotoView> {

    @Inject
    PhotoApi photoApi;

    private Activity activity;

    public PhotoPresenter(Activity activity) {
        this.activity = activity;
        ((GroovyApplication) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void attachView(IPhotoView iPhotoView) {
        super.attachView(iPhotoView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void getPhotoById(String id) {
        getMvpView().loadingImage();
        photoApi.getPhotoById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoDetail>() {
                    @Override
                    public void call(PhotoDetail photoDetail) {
                        if (photoDetail != null) {
                            getMvpView().imageLoaded(photoDetail.photo);
                        } else {
                            getMvpView().errorLoadingImage(activity.getString(R.string.empty_result));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getMvpView().errorLoadingImage(throwable.getMessage());
                    }
                });
    }

}
