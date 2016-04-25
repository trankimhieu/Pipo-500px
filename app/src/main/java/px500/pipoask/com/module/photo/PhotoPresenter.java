package px500.pipoask.com.module.photo;

import android.app.Activity;

import javax.inject.Inject;

import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.R;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.module.base.BasePresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoPresenter extends BasePresenter<IPhotoView> {

    private final Activity activity;
    @Inject
    PhotoApi photoApi;

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

    public void getPhotoById(Integer id) {
        getMvpView().loadingImage();
        photoApi.getPhotoById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoDetail -> {
                    if (photoDetail != null) {
                        getMvpView().imageLoaded(photoDetail.photo);
                    } else {
                        getMvpView().errorLoadingImage(activity.getString(R.string.empty_result));
                    }
                }, throwable -> {
                    getMvpView().errorLoadingImage(throwable.getMessage());
                });
    }

}
