package com.onmicrosoft.weatherapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnection {
    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
