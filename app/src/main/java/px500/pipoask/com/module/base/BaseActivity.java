package px500.pipoask.com.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.di.component.ActivityComponent;
import px500.pipoask.com.di.component.DaggerActivityComponent;
import px500.pipoask.com.di.module.ActivityModule;
import px500.pipoask.com.di.module.PresenterModule;

/**
 * Created by Sandy on 12/25/15.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(((GroovyApplication) getApplication()).getAppComponent())
                    .activityModule(new ActivityModule(this))
                    .presenterModule(new PresenterModule())
                    .build();
        }
        return mActivityComponent;
    }

}
