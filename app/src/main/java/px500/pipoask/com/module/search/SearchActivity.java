package px500.pipoask.com.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.SearchAdapter;
import px500.pipoask.com.adapter.holder.MainHolder;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.module.base.BaseActivity;
import px500.pipoask.com.module.photo.PhotoActivity;
import px500.pipoask.com.utiity.CommonUtils;
import px500.pipoask.com.utiity.LogUtils;
import px500.pipoask.com.utiity.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SearchActivity extends BaseActivity implements ISearchView, MainHolder.ClickListener{

    public static final String TAG = "SearchActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.loading)
    ProgressBar loading;

    @Bind(R.id.searchBox)
    EditText searchbox;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    SearchPresenter searchPresenter;
    private List<Photo> photoList;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchPresenter.attachView(this);

        initUI();
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_search_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photoList = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, photoList, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(searchAdapter);

        RxTextView.textChangeEvents(searchbox)
                .debounce(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());

        searchbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    CommonUtils.hideKeyboard(SearchActivity.this);
                    return true;
                }
                return false;
            }
        });


    }

    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                if (isCurrentlyOnMainThread()) {
                    String keyword = onTextChangeEvent.text().toString();
                    LogUtils.debug(TAG, keyword);
                    if (!StringUtils.isEmpty(keyword.trim())) {
                        searchPresenter.search(onTextChangeEvent.text().toString());
                    }
                }
            }
        };
    }

    private boolean isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @Override
    public void showLoadingData() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showResult(List<Photo> photos) {
        searchAdapter.setData(photos);
    }

    @Override
    public void emptyResult() {

    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void onItemClicked(View v, int position) {
        Intent intent = new Intent(SearchActivity.this, PhotoActivity.class);
        intent.putExtra(PhotoActivity.PHOTO_ID, photoList.get(position));
        startActivity(intent);
    }
}
