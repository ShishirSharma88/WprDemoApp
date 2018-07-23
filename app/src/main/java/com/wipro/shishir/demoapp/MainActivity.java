package com.wipro.shishir.demoapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wipro.shishir.demoapp.model.MainData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements TourListContract.MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.progress_download_content)
    ProgressBar progressBar;

    @BindView(R.id.list_canada_tour)
    RecyclerView listCanadaTour;

    @BindView(R.id.swipe_layout_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    private MainPresenterImpl mainPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listCanadaTour.setLayoutManager(layoutManager);

        mainPresenterImpl = new MainPresenterImpl(this);
        mainPresenterImpl.initiate();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setAdapter(MainData mainData) {
        listCanadaTour.setAdapter(new TourListAdapter(mainData));
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this,
                getResources().getString(message),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        mainPresenterImpl.onRefresh();
    }
}
