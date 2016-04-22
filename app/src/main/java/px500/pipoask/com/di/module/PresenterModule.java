package px500.pipoask.com.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import px500.pipoask.com.di.scope.PerActivity;
import px500.pipoask.com.module.main.MainPresenter;
import px500.pipoask.com.module.photo.PhotoPresenter;
import px500.pipoask.com.module.search.SearchPresenter;

@Module
public class PresenterModule {

    @Provides
    @PerActivity
    MainPresenter providesMainPresenter(Activity activity) {
        return new MainPresenter(activity);
    }

    @Provides
    @PerActivity
    PhotoPresenter providesPhotoPresenter(Activity activity) {
        return new PhotoPresenter(activity);
    }

    @Provides
    @PerActivity
    SearchPresenter providesSearchPresenter(Activity activity) {
        return new SearchPresenter(activity);
    }

}
