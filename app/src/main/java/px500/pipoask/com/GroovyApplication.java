package px500.pipoask.com;

import android.app.Application;

import px500.pipoask.com.di.component.ApplicationComponent;
import px500.pipoask.com.di.component.DaggerApplicationComponent;
import px500.pipoask.com.di.module.ApplicationModule;

/**
 * Created by Sandy on 12/26/15.
 */
public class GroovyApplication extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

}
