package px500.pipoask.com.module.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.di.component.ActivityComponent;
import px500.pipoask.com.di.component.DaggerActivityComponent;
import px500.pipoask.com.di.module.ActivityModule;
import px500.pipoask.com.di.module.PresenterModule;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

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
