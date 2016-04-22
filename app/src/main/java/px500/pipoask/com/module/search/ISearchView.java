package px500.pipoask.com.module.search;

import java.util.List;

import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.MvpView;


/**
 * Created by Sandy on 12/26/15.
 */
public interface ISearchView extends MvpView {
    public void showLoadingData();
    public void hideLoadingData();
    public void showResult(List<Photo> photoList);
    public void emptyResult();
    public void showError(String e);
}
