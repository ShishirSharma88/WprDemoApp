package com.wipro.shishir.demoapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wipro.shishir.demoapp.AppContext;

/**
 * This is the class to hold static data used in app mostly for urls, String etc
 */
public class Utility {

    public final static int NETWORK_ERROR_CODE = 1;
    public final static int OTHER_ERROR_CODE = 0;
    public final static int NO_ERROR_CODE = -1;

    public static String BASE_URL = "https://dl.dropboxusercontent.com/";

    public static boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm != null ? cm.getActiveNetworkInfo() : null;
        return info != null &&
                (info.getState() == NetworkInfo.State.CONNECTED
                        || info.getState() == NetworkInfo.State.CONNECTING);

    }
}
