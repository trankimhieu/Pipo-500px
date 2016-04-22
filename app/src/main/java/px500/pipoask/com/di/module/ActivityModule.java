package px500.pipoask.com.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import px500.pipoask.com.di.scope.PerActivity;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return activity;
    }
}