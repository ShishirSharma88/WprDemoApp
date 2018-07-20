package com.wipro.shishir.demoapp;

import android.view.MenuItem;

import com.wipro.shishir.demoapp.api.RequestManager;
import com.wipro.shishir.demoapp.model.MainData;

public interface MainPresenter extends RequestManager.ResponseListener<MainData> {
    void startProcess();
    void onOptionsItemClicked(MenuItem menuItem);
}

