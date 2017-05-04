package com.leafcastle.android.weatherservicedemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.leafcastle.android.weatherservicedemo.MainActivity;

import static com.leafcastle.android.weatherservicedemo.utils.Globals.CONNECT;

/**
 * Created by kasper on 01/05/17.
 */

public class NetworkChecker {

    public static String getNetworkStatus(Context c){
        ConnectivityManager connectMan = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            //somehow we connected
            Log.d(CONNECT, "Got connections" +
                    netInfo.toString()
            );

            return "Got connections" + netInfo.toString();
        } else {
            //oh no, no connection
            Log.d(CONNECT, "No connections");
            return "No connections";
        }
    }
}
