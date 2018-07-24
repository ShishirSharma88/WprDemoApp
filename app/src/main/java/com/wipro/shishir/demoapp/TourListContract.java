package com.wipro.shishir.demoapp;

import com.wipro.shishir.demoapp.api.RequestManager;
import com.wipro.shishir.demoapp.model.MainData;

public interface TourListContract {
     interface MainView {
        void showProgress();
        void hideProgress();
        void setAdapter(MainData mainData);
        void showMessage(int message);
        void setTitle(String title);
    }

     interface MainPresenter extends RequestManager.ResponseListener<MainData> {
        void startProcess();
        void initiate();
        void onRefresh();
    }
}
