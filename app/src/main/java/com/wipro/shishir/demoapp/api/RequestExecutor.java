package com.wipro.shishir.demoapp.api;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wipro.shishir.demoapp.listener.TaskListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is responsible for server communication
 */
class RequestExecutor extends AsyncTask<Void, Void, String> {

    private TaskListener<String> taskListener;
    private String requestUrl;
    private JSONObject params;

    RequestExecutor(@NonNull String requestUrl,
                    @NonNull TaskListener<String> taskListener,
                    @NonNull JSONObject params) {
        this.requestUrl = requestUrl;
        this.taskListener = taskListener;
        this.params = params;
    }

    protected String doInBackground(Void... urls) {

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            // For making request POST and GET
            urlConnection.setRequestMethod("GET");

            try {
                OutputStream os = urlConnection.getOutputStream();
                os.write(params.toString().getBytes("UTF-8"));
                os.close();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("doInBackground : ", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {

        if (response == null) {
            taskListener.onFailure(0);
        } else {
            Log.i("INFO", response);
            taskListener.onSuccess(response);
        }
    }
}
