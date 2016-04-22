package px500.pipoask.com.module.main;

import px500.pipoask.com.data.model.PhotoList;
import px500.pipoask.com.module.base.MvpView;

/**
 * Created by Sandy on 12/26/15.
 */
public interface IMainView extends MvpView {
    public void showLoadingData();
    public void hideLoadingData();
    public void showError(String e);
    public void showPhotoList(PhotoList photo);
    public void loadMore();
}
