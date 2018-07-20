package com.wipro.shishir.demoapp.api;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.wipro.shishir.demoapp.model.MainData;

/**
 * This is a json parser class to get the final result from the
 * server response, as of now we need only to get "MainData"
 * So data type is explicitly defined
 */
class ResponseParser extends AsyncTask<Object, Object, Object> {

    private @NonNull
    RequestManager.ResponseListener<MainData> responseListener;

    private @NonNull
    String response;

    ResponseParser(@NonNull RequestManager.ResponseListener<MainData> responseListener,
                   @NonNull String response) {
        this.responseListener = responseListener;
        this.response = response;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        return new Gson().fromJson(response, MainData.class);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        responseListener.onResponse((MainData) o, true);
    }
}
