package com.mindvalley.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * This is network utility class to check internet conditions.
 *
 * @author SandeepD
 */

public class NetworkUtil
{

    /**
     * This function is used to detect whether the phone is in Airplane Mode And WIFI is turned off
     *
     * @param context
     * @return
     */
    public static boolean isAirplaneModeWithNoWIFI(Context context)
    {
        return isAirplaneModeOn(context) && !isWifiEnabled(context);
    }

    static boolean isWifiEnabled(Context context)
    {
        WifiManager mng = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return mng.isWifiEnabled();
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context - The current app context
     * @return true if enabled.
     */
    private static boolean isAirplaneModeOn(Context context)
    {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

}
