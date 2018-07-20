package com.wipro.shishir.demoapp;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.wipro.shishir.demoapp.api.RequestManager;
import com.wipro.shishir.demoapp.model.MainData;
import com.wipro.shishir.demoapp.utility.Utility;

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private RequestManager requestManager;


    MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        RequestManager.ResponseListener<MainData> responseListener = this;
        requestManager = new RequestManager(responseListener);
    }

    @Override
    public void startProcess() {
        mainView.showProgress();
        requestManager.callApi(Utility.DATA_URL);
    }

    @Override
    public void onOptionsItemClicked(MenuItem menuItem) {
        startProcess();
    }

    @Override
    public void onResponse(@NonNull MainData mainData, boolean success) {
        mainView.setAdapter(mainData);
        mainView.setTitle(mainData.getTitle());
        mainView.hideProgress();

        if (!success) {
            mainView.showMessage(R.string.download_failed);
        }
    }
}
