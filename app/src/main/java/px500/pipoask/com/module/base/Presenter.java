package px500.pipoask.com.module.base;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}