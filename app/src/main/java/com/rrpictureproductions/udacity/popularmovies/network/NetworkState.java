package com.rrpictureproductions.udacity.popularmovies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by robin on 06.10.2016.
 */

public class NetworkState {

    private Context ctx;

    public NetworkState(Context context) {
        ctx = context.getApplicationContext();
    }

    /**
     * Returns true if the device has internet connectivity.
     * Copied from: http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
