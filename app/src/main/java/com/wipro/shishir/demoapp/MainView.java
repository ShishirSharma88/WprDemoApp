package com.wipro.shishir.demoapp;

import com.wipro.shishir.demoapp.model.MainData;

public interface MainView {
    void showProgress();
    void hideProgress();
    void setAdapter(MainData mainData);
    void showMessage(int message);
    void setTitle(String title);
}
