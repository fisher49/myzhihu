package org.qxh.myapp.myzhihu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by QXH on 2016/1/29.
 */
public class Utility {
    public static String formatYYYYMMDDDate(String date){
        StringBuilder builder = new StringBuilder(date);
        builder.insert(6, '-');
        builder.insert(4, '-');
        return builder.toString();
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
