package com.wipro.shishir.demoapp;

import android.support.annotation.NonNull;

import com.wipro.shishir.demoapp.api.RequestManager;
import com.wipro.shishir.demoapp.model.MainData;
import com.wipro.shishir.demoapp.utility.Utility;

public class MainPresenterImpl implements TourListContract.MainPresenter {

    private final TourListContract.MainView mainView;
    private final RequestManager requestManager;


    MainPresenterImpl(TourListContract.MainView mainView) {
        this.mainView = mainView;
        RequestManager.ResponseListener<MainData> responseListener = this;
        requestManager = new RequestManager(responseListener);
    }

    @Override
    public void startProcess() {
        requestManager.callApi();
    }

    @Override
    public void initiate() {
        mainView.showProgress();
        startProcess();
    }

    @Override
    public void onRefresh() {
        startProcess();
    }

    @Override
    public void onResponse(@NonNull MainData mainData, boolean success, int errorType) {
        mainView.setAdapter(mainData);
        mainView.setTitle(mainData.getTitle());
        mainView.hideProgress();

        if (!success) {
            mainView.showMessage(errorType == Utility.OTHER_ERROR_CODE
                    ? R.string.download_failed
                    : R.string.no_internet);
        }
    }
}
