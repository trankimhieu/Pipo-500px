package px500.pipoask.com.di.component;

import javax.inject.Singleton;

import dagger.Component;
import px500.pipoask.com.di.module.ApplicationModule;
import px500.pipoask.com.di.module.PixelApiModule;
import px500.pipoask.com.module.main.MainPresenter;
import px500.pipoask.com.module.photo.PhotoPresenter;
import px500.pipoask.com.module.search.SearchPresenter;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                PixelApiModule.class
        }
)
public interface ApplicationComponent  {
    void inject(MainPresenter mainPresenter);
    void inject(PhotoPresenter photoPresenter);
    void inject(SearchPresenter searchPresenter);
}