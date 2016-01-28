package org.qxh.myapp.myzhihu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.qxh.myapp.myzhihu.model.db.CacheDbHelper;
import org.qxh.myapp.myzhihu.model.db.CacheDbManager;

/**
 * Created by QXH on 2016/1/21.
 */
public class ZhihuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(getApplicationContext());
        CacheDbManager.initialize(new CacheDbHelper(this));
    }
}
