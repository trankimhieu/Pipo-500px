package px500.pipoask.com.module.search;

import android.app.Activity;

import javax.inject.Inject;

import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.model.PhotoList;
import px500.pipoask.com.module.base.BasePresenter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<ISearchView> {

    @Inject
    PhotoApi photoApi;

    public SearchPresenter(Activity activity) {
        ((GroovyApplication) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void attachView(ISearchView iSearchView) {
        super.attachView(iSearchView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void search(String keyword) {

        getMvpView().showLoadingData();

        photoApi.search(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhotoList>() {
                               @Override
                               public void onCompleted() {
                                   getMvpView().hideLoadingData();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   getMvpView().showError(e.getMessage());
                               }

                               @Override
                               public void onNext(PhotoList photoList) {
                                   if (photoList != null && photoList.photos.size() > 0) {
                                       getMvpView().showResult(photoList.photos);
                                   } else {
                                       getMvpView().emptyResult();
                                   }
                               }
                           }

                );
    }
}
