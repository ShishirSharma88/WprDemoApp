package com.wipro.shishir.demoapp.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wipro.shishir.demoapp.listener.TaskListener;
import com.wipro.shishir.demoapp.model.MainData;
import com.wipro.shishir.demoapp.utility.Utility;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This class is responsible for server communication
 */
class RequestExecutor {

    private final String TAG = getClass().getName();
    private final TaskListener<MainData> taskListener;
    private final Retrofit retrofit;

    RequestExecutor(@NonNull TaskListener<MainData> taskListener,
                    @NonNull Retrofit retrofit) {
        this.taskListener = taskListener;
        this.retrofit = retrofit;
    }

    void execute() {
        RetroInterface retroInterface = retrofit.create(RetroInterface.class);

        Observable<MainData> observable = retroInterface.getTourData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<MainData>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError : " + e.toString());
            }

            @Override
            public void onNext(MainData mainData) {
                if (mainData == null) {
                    taskListener.onFailure(Utility.OTHER_ERROR_CODE);
                } else {
                    Log.i("INFO", mainData.toString());
                    taskListener.onSuccess(mainData);
                }
            }
        });
    }
}
