package org.qxh.myapp.myzhihu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by QXH on 2016/1/21.
 */
public class ZhihuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(getApplicationContext());
    }
}
