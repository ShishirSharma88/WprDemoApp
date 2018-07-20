package com.wipro.shishir.demoapp.api;

import android.support.annotation.NonNull;

import com.wipro.shishir.demoapp.listener.TaskListener;
import com.wipro.shishir.demoapp.model.MainData;

import org.json.JSONObject;

/**
 * This class has responsibility to make request and return response
 * after parsing to presenter
 */
public class RequestManager implements TaskListener {

    private TaskListener<String> taskListener;
    private ResponseListener<MainData> responseListener;

    public RequestManager(ResponseListener<MainData> responseListener) {
        taskListener = this;
        this.responseListener = responseListener;
    }

    public void callApi(String url) {
        RequestExecutor requestExecutor = new RequestExecutor(url, taskListener, new JSONObject());
        requestExecutor.execute();
    }

    @Override
    public void onSuccess(@NonNull Object result) {
        new ResponseParser(responseListener, (String) result).execute();
    }

    @Override
    public void onFailure(int errorMessageResource) {
        MainData mainData = new MainData();
        responseListener.onResponse(mainData, false);
    }

    // Data type is fixed here as of now, we do not have any other server request
    // in the application
    public interface ResponseListener<MainData> {
        void onResponse(@NonNull MainData mainData, boolean success);
    }
}
