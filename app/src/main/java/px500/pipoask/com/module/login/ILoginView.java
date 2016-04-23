package px500.pipoask.com.module.login;

import px500.pipoask.com.module.base.MvpView;

public interface ILoginView extends MvpView {


    void showError(String e);

    void login();

    void showLoadingData();

    void hideLoadingData();
}
