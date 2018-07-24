package com.wipro.shishir.demoapp.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wipro.shishir.demoapp.listener.TaskListener;
import com.wipro.shishir.demoapp.model.MainData;
import com.wipro.shishir.demoapp.utility.Utility;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class has responsibility to make request and return response
 * after parsing to presenter
 */
public class RequestManager implements TaskListener<MainData> {

    private final TaskListener<MainData> taskListener;
    private final ResponseListener<MainData> responseListener;

    public RequestManager(ResponseListener<MainData> responseListener) {
        taskListener = this;
        this.responseListener = responseListener;
    }

    public void callApi() {
        if (!Utility.isNetworkConnectionAvailable()) {
            onFailure(Utility.NETWORK_ERROR_CODE);
            return;
        }

        try {
            RequestExecutor requestExecutor = new RequestExecutor(taskListener, getRetroClient());
            requestExecutor.execute();
        } catch (Exception e) {
            Log.i(getClass().getName(), e.toString());
            onFailure(Utility.OTHER_ERROR_CODE);
        }
    }

    private Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Override
    public void onSuccess(@NonNull MainData result) {
        responseListener.onResponse(result, true, Utility.NO_ERROR_CODE);
    }

    @Override
    public void onFailure(int errorType) {
        MainData mainData = new MainData();
        responseListener.onResponse(mainData, false, errorType);
    }

    // Data type is fixed here as of now, we do not have any other server request
    // in the application
    // here reason is used for error occurred due to network or any other
    // since api doesn't support any error message on failure we are using error code '0'
    // and failure due to network is '1'
    public interface ResponseListener<MainData> {
        void onResponse(@NonNull MainData mainData, boolean success, int reason);
    }
}
