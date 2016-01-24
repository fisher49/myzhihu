package org.qxh.myapp.myzhihu.model.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by QXH on 2016/1/24.
 */
public class PreferenceHelper {

    public static void saveStringPreference(Context context, String key, String value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    public static String readStringPreference(Context context, String key, String defaultString){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defaultString);
    }
}
