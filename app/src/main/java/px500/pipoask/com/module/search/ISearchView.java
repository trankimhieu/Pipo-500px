package px500.pipoask.com.module.search;

import java.util.List;

import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.MvpView;

public interface ISearchView extends MvpView {
    void showLoadingData();

    void hideLoadingData();

    void showResult(List<Photo> photoList);

    void emptyResult();

    void showError(String e);
}
