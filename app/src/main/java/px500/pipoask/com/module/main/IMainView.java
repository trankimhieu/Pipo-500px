package px500.pipoask.com.module.main;

import px500.pipoask.com.data.model.PhotoList;
import px500.pipoask.com.module.base.MvpView;

public interface IMainView extends MvpView {
    void showLoadingData();

    void hideLoadingData();

    void showError(String e);

    void showPhotoList(PhotoList photo);

    void loadMore();
}
