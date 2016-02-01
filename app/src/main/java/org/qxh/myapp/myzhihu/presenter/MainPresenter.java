package org.qxh.myapp.myzhihu.presenter;

import android.content.Context;

/**
 * Created by QXH on 2016/2/1.
 */
public class MainPresenter {
    private Context context;
    private String tag;

    public MainPresenter(Context context, String tag) {
        this.context = context;
        this.tag = tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public String getTag(){
        return tag;
    }
}
