package px500.pipoask.com.module.photo;

import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.MvpView;

/**
 * Created by Sandy on 12/26/15.
 */
public interface IPhotoView extends MvpView {
    public void loadingImage();
    public void imageLoaded(Photo photo);
    public void errorLoadingImage(String e);
}
