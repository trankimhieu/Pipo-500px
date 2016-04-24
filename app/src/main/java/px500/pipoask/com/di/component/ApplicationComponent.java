package px500.pipoask.com.di.component;

import javax.inject.Singleton;

import dagger.Component;
import px500.pipoask.com.adapter.PhotoAdapter;
import px500.pipoask.com.di.module.ApplicationModule;
import px500.pipoask.com.di.module.PixelApiModule;
import px500.pipoask.com.module.login.LoginPresenter;
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
    void inject(LoginPresenter loginPresenter);

    void inject(PhotoAdapter photoAdapter);
}