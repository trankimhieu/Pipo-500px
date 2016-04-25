package px500.pipoask.com.module.login;

import android.app.Activity;
import android.content.Context;

import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.tasks.XAuth500pxTask;
import com.google.gson.Gson;

import javax.inject.Inject;

import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.GroovyApplication;
import px500.pipoask.com.NavigationManager;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import px500.pipoask.com.data.model.User;
import px500.pipoask.com.module.base.BasePresenter;
import px500.pipoask.com.module.main.MainActivity;
import px500.pipoask.com.utiity.LogUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<ILoginView> implements XAuth500pxTask.Delegate {

    private static final String TAG = "LoginPresenter";

    private static String email;

    @Inject
    PhotoApi photoApi;

    public LoginPresenter(Activity activity) {
        ((GroovyApplication) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void attachView(ILoginView iLoginView) {
        super.attachView(iLoginView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    public void login(String email, String password) {
        XAuth500pxTask loginTask = new XAuth500pxTask(this);
        loginTask.execute(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET, email, password);
        LoginPresenter.email = email;
    }

    @Override
    public void onSuccess(AccessToken accessToken) {
        getMvpView().hideLoadingData();
        LogUtils.debug(TAG, accessToken.getToken());
        SharedPreferenceHelper.setSharedPreferenceString(ConstKV.USER_500PX_TOKEN, accessToken.getToken());
        SharedPreferenceHelper.setSharedPreferenceString(ConstKV.USER_500PX_TOKEN_SECRET, accessToken.getTokenSecret());
        photoApi.getUser(email).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                new NavigationManager<MainActivity>().openActivity((Context) getMvpView(), MainActivity.class);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.error(TAG, e.getMessage());
            }

            @Override
            public void onNext(User user) {
                Gson gson = new Gson();
                String userJSON = gson.toJson(user);
                SharedPreferenceHelper.setSharedPreferenceString(ConstKV.USER_500PX_INFO, userJSON);
            }
        });

    }

    @Override
    public void onFail(FiveHundredException e) {
        getMvpView().hideLoadingData();
        getMvpView().showError(e.getMessage());
    }

    public void checkToken() {
        String token = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN, null);

        if (token != null) {
            String token_secret = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN_SECRET, null);
            LogUtils.debug(TAG + " Token", token);
            LogUtils.debug(TAG + " Token Secret", token_secret);
            new NavigationManager<MainActivity>().openActivity((Context) getMvpView(), MainActivity.class);
        }
    }
}
