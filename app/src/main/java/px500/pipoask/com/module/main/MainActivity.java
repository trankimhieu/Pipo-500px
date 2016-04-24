package px500.pipoask.com.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import px500.pipoask.com.R;
import px500.pipoask.com.adapter.PhotoAdapter;
import px500.pipoask.com.adapter.holder.MainHolder;
import px500.pipoask.com.data.api.Feature;
import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.data.model.PhotoList;
import px500.pipoask.com.module.base.BaseActivity;
import px500.pipoask.com.module.photo.PhotoActivity;
import px500.pipoask.com.module.search.SearchActivity;
import px500.pipoask.com.utiity.EndlessRecyclerOnScrollListener;
import px500.pipoask.com.utiity.LogUtils;

public class MainActivity extends BaseActivity implements IMainView,
        NavigationView.OnNavigationItemSelectedListener, MainHolder.ClickListener{

    private static final String TAG = "MainActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.status)
    TextView status;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.loading)
    LinearLayout llLoading;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.loadMoreView)
    TextView loadMoreView;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Inject
    MainPresenter mainPresenter;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private GridLayoutManager gridLayoutManager;
    private List<Photo> photoList;
    private PhotoAdapter photoAdapter;
    private int currentPage = 1;
    private String feature = Feature.Type.POPULAR;

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        LogUtils.debug(TAG, "Click FAB");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter.attachView(this);
        initUI();

        mainPresenter.getPhotos(currentPage, feature, false);
    }


    private void initUI() {
        setTitle("500px");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                currentPage++;
                mainPresenter.getPhotos(currentPage, feature, true);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(this, photoList, this);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, 0 );
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        resetAdapter();

        switch (item.getItemId()) {
            case R.id.popular:
                feature = Feature.Type.POPULAR;
                setTitle(getResources().getString(R.string.popular));
                break;

            case R.id.editors:
                feature = Feature.Type.EDITORS;
                setTitle(getResources().getString(R.string.editors));
                break;

            case R.id.upcoming:
                feature = Feature.Type.UPCOMING;
                setTitle(getResources().getString(R.string.upcoming));
                break;

            case R.id.fresh:
                feature = Feature.Type.FRESH;
                setTitle(getResources().getString(R.string.fresh));
                break;
        }

        if (endlessRecyclerOnScrollListener != null) {
            recyclerView.removeOnScrollListener(endlessRecyclerOnScrollListener);
            endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
                @Override
                public void onLoadMore() {
                    currentPage++;
                    mainPresenter.getPhotos(currentPage, feature, true);
                }
            };
            recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        }
        mainPresenter.getPhotos(currentPage, feature, false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void resetAdapter() {
        currentPage = 1;
        photoList.clear();
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingData() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        llLoading.setVisibility(View.GONE);
        loadMoreView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(final String e) {
        status.setText(e);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPhotoList(PhotoList data) {
        photoList.addAll(data.photos);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        loadMoreView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoList = null;
    }

    @Override
    public void onItemClicked(View v, int position) {
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        intent.putExtra(PhotoActivity.PHOTO_ID, Parcels.wrap(photoList.get(position)));
        startActivity(intent);
    }
}
