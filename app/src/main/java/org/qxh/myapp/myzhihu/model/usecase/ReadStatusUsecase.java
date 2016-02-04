package org.qxh.myapp.myzhihu.model.usecase;

import android.content.Context;

import org.qxh.myapp.myzhihu.model.db.PreferenceHelper;

/**
 * Created by QXH on 2016/2/4.
 */
public class ReadStatusUsecase {
    private static final String READ_TAG = "read";
    private Context context;

    public ReadStatusUsecase(Context context){
        this.context = context;
    }

    public void insertReadId(String id){
        if(id.length() > 0){
            String status = PreferenceHelper.readStringPreference(context, READ_TAG, "");
            String update = status + "," + id;
            PreferenceHelper.saveStringPreference(context, READ_TAG, update);
        }

    }

    public boolean isReadStatusById(String id){
        if(id.length() > 0){
            String status = PreferenceHelper.readStringPreference(context, READ_TAG, "");
            if(status.contains(id)){
                return true;
            }
        }
        return false;
    }
}
