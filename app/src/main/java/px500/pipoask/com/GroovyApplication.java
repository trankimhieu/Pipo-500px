package px500.pipoask.com;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import px500.pipoask.com.data.model.Category;
import px500.pipoask.com.di.component.ApplicationComponent;
import px500.pipoask.com.di.component.DaggerApplicationComponent;
import px500.pipoask.com.di.module.ApplicationModule;

public class GroovyApplication extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        SharedPreferenceHelper.getInstance(getApplicationContext());
        Fresco.initialize(getApplicationContext());

        ConstKV.CATEGORY_LIST.add(new Category(0, "Uncategorized"));
        ConstKV.CATEGORY_LIST.add(new Category(10, "Abstract"));
        ConstKV.CATEGORY_LIST.add(new Category(11, "Animals"));
        ConstKV.CATEGORY_LIST.add(new Category(5, "Black and White"));
        ConstKV.CATEGORY_LIST.add(new Category(1, "Celebrities"));
        ConstKV.CATEGORY_LIST.add(new Category(9, "City and Architecture"));
        ConstKV.CATEGORY_LIST.add(new Category(15, "Commercial"));
        ConstKV.CATEGORY_LIST.add(new Category(16, "Concert"));
        ConstKV.CATEGORY_LIST.add(new Category(20, "Family"));
        ConstKV.CATEGORY_LIST.add(new Category(14, "Fashion"));
        ConstKV.CATEGORY_LIST.add(new Category(2, "Film"));
        ConstKV.CATEGORY_LIST.add(new Category(24, "Fine Art"));
        ConstKV.CATEGORY_LIST.add(new Category(23, "Food"));
        ConstKV.CATEGORY_LIST.add(new Category(3, "Journalism"));
        ConstKV.CATEGORY_LIST.add(new Category(8, "Landscapes"));
        ConstKV.CATEGORY_LIST.add(new Category(12, "Macro"));
        ConstKV.CATEGORY_LIST.add(new Category(18, "Nature"));
        ConstKV.CATEGORY_LIST.add(new Category(4, "Nude"));
        ConstKV.CATEGORY_LIST.add(new Category(7, "People"));
        ConstKV.CATEGORY_LIST.add(new Category(19, "Performing Arts"));
        ConstKV.CATEGORY_LIST.add(new Category(17, "Sport"));
        ConstKV.CATEGORY_LIST.add(new Category(6, "Still Life"));
        ConstKV.CATEGORY_LIST.add(new Category(21, "Street"));
        ConstKV.CATEGORY_LIST.add(new Category(26, "Transportation"));
        ConstKV.CATEGORY_LIST.add(new Category(13, "Travel"));
        ConstKV.CATEGORY_LIST.add(new Category(22, "Underwater"));
        ConstKV.CATEGORY_LIST.add(new Category(27, "Urban Exploration"));
        ConstKV.CATEGORY_LIST.add(new Category(25, "Wedding"));

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
