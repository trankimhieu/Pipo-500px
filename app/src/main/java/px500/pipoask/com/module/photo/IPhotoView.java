package px500.pipoask.com.module.photo;

import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.MvpView;

public interface IPhotoView extends MvpView {
    void loadingImage();

    void imageLoaded(Photo photo);

    void errorLoadingImage(String e);
}
