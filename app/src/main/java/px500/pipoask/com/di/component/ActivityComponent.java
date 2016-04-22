package px500.pipoask.com.di.component;

import dagger.Component;
import px500.pipoask.com.di.module.ActivityModule;
import px500.pipoask.com.di.module.PresenterModule;
import px500.pipoask.com.di.scope.PerActivity;
import px500.pipoask.com.module.main.MainActivity;
import px500.pipoask.com.module.photo.PhotoActivity;
import px500.pipoask.com.module.search.SearchActivity;

@PerActivity
@Component(
        dependencies = {
          ApplicationComponent.class
        },
        modules = {
           ActivityModule.class,
           PresenterModule.class
        }
)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(PhotoActivity photoActivity);
    void inject(SearchActivity searchActivity);
}
