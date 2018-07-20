package com.wipro.shishir.demoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wipro.shishir.demoapp.model.MainData;

public class MainActivity extends AppCompatActivity implements MainView {

    private ProgressBar progressBar;
    private RecyclerView listCanadaTour;
    private MainPresenterImpl mainPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCanadaTour = (RecyclerView) findViewById(R.id.list_canada_tour);
        progressBar = (ProgressBar) findViewById(R.id.progress_download_content);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listCanadaTour.setLayoutManager(layoutManager);

        mainPresenterImpl = new MainPresenterImpl(this);
        mainPresenterImpl.startProcess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mainPresenterImpl.onOptionsItemClicked(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
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
}
