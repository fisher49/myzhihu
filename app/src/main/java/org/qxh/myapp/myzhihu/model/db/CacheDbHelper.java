package org.qxh.myapp.myzhihu.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by QXH on 2016/1/24.
 */
public class CacheDbHelper extends SQLiteOpenHelper{

    public CacheDbHelper(Context context, int version) {
        super(context, "myzhihu.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("if not exists CacheList(id INTEGER primary key autoincrement,date INTEGER unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
